package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class AccommodationUpdateServiceImplementation implements AccommodationUpdateService{
    @Autowired
    AccommodationUpdateRepository accommodationUpdateRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    DateManagementService dms;
    @Override
    public Collection<AccommodationUpdateSummaryResponse> getAllAccommodationUpdates() throws IOException {
        ArrayList<AccommodationUpdate> requests = new ArrayList<AccommodationUpdate>(accommodationUpdateRepository.findAll());
        ArrayList<AccommodationUpdateSummaryResponse> summary = new ArrayList<>();
        for (AccommodationUpdate request : requests){
            if(request.getUpdateStatus() == AccommodationUpdateStatus.Created){
                summary.add(new AccommodationUpdateSummaryResponse(request.getId(),request.getAccommodation().getName(),
                        request.getAccommodation().getDescription(),
                        request.getAccommodation().getPhotos().get(0).getEncodedImage(),
                        request.getUpdateType()));
            }
        }
        return summary;
    }
    @Override
    public AccommodationUpdateResponse getAccommodationUpdate(UUID updateId){
        AccommodationUpdate requestedUpdate = accommodationUpdateRepository.getReferenceById(updateId);
        return new AccommodationUpdateResponse(requestedUpdate.getId(),requestedUpdate.getAccommodation(),requestedUpdate.getUpdateStatus());
    }
    @Override
    public MessageResponse resolveAccommodationUpdate(UUID updateId, int flag) { // 0 - Rejected
        AccommodationUpdate accommodationUpdate = accommodationUpdateRepository.getReferenceById(updateId);
        if(accommodationUpdate.getUpdateType() == AccommodationUpdateType.CREATE_REQUEST) { // REQUEST REJECTED
            if(flag == 0){
                Accommodation accommodation = accommodationUpdate.getAccommodation();
                accommodation.setOnHoldStatus(AccommodationOnHoldStatus.CREATE_APPROVAL_REJECTED);
                accommodationRepository.save(accommodation);

                accommodationUpdate.setUpdateStatus(AccommodationUpdateStatus.Rejected);
                accommodationUpdateRepository.save(accommodationUpdate);
                return new MessageResponse(true,"Rejected");
            }
            else{
                Accommodation accommodation = accommodationUpdate.getAccommodation();
                accommodation.setOnHoldStatus(AccommodationOnHoldStatus.APPROVED);
                accommodationRepository.save(accommodation);

                accommodationUpdate.setUpdateStatus(AccommodationUpdateStatus.Accepted);
                accommodationUpdateRepository.save(accommodationUpdate);
                return new MessageResponse(true,"Accepted");
            }
        }
        else{// EDIT_REQUEST
            if(flag == 0){
                Accommodation accommodation = accommodationUpdate.getAccommodation();
                accommodation.setOnHoldStatus(AccommodationOnHoldStatus.EDIT_APPROVAL_REJECTED);
                accommodationRepository.save(accommodation);

                accommodationUpdate.setUpdateStatus(AccommodationUpdateStatus.Rejected);
                accommodationUpdateRepository.save(accommodationUpdate);
                return new MessageResponse(true,"Rejected");
            }
            else{
                Accommodation accommodation = accommodationUpdate.getAccommodation();
                accommodation.setOnHoldStatus(AccommodationOnHoldStatus.APPROVED);
                accommodationRepository.save(accommodation);

                accommodationUpdate.setUpdateStatus(AccommodationUpdateStatus.Accepted);
                accommodationUpdateRepository.save(accommodationUpdate);
                return new MessageResponse(true,"Accepted");
            }
        }
    }

    @Override
    public void insertPhotos(Accommodation accommodation, List<String> photos) throws IOException {
        String folderPath = "D:\\projekatBek\\src\\main\\resources\\images";
        for(String image64 : photos){
            String[] parts = image64.split(",");
            String base64Data = parts[1];
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            String randomImageName = UUID.randomUUID().toString();
            String filePath = folderPath + "/" + randomImageName + ".png";
            Files.write(Path.of(filePath), decodedBytes, StandardOpenOption.CREATE);
            System.out.println("Image stored successfully at: " + filePath);
            accommodation.photos.add(new Image(filePath));
        }
    }

    @Override
    public MessageResponse createAccommodationUpdate(AccommodationRequest accommodationRequest) throws IOException {
        Accommodation newAccommodation = new Accommodation();
        newAccommodation.processAccommodationRequest(accommodationRequest);
        newAccommodation.photos = new ArrayList<Image>();
        insertPhotos(newAccommodation,accommodationRequest.getPhotos());

        newAccommodation.setHost(hostRepository.getReferenceById(accommodationRequest.getHostId()));
        newAccommodation.setOnHoldStatus(AccommodationOnHoldStatus.WAITING_FOR_CREATE_APPROVAL);
        accommodationRepository.save(newAccommodation);
        AccommodationUpdate createRequest = new AccommodationUpdate(newAccommodation,AccommodationUpdateType.CREATE_REQUEST, AccommodationUpdateStatus.Created);
        accommodationUpdateRepository.save(createRequest);
        return new MessageResponse(true,"Successfuly requested accommodation creation");
    }

    @Override
    public MessageResponse createEditRequest(UUID accommodationId, AccommodationRequest accommodationRequest) throws IOException {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.processAccommodationRequest(accommodationRequest);
        List<DatePeriod> datePeriods=accommodation.getAvailabilityDatePeriods();
        accommodation.photos = new ArrayList<Image>();
        Host host=accommodation.getHost();
        List<Reservation> reservations=reservationRepository.findAllByHostAndAccommodationId(host.getId(),accommodationId);
        for(Reservation r : reservations){
            for(DatePeriod datePeriod: datePeriods){
                if(dms.isPeriodInside(datePeriod,r.getReservedDate()) || dms.doPeriodsOverlap(datePeriod,r.getReservedDate())){
                    return new MessageResponse(false,"Error editing accommodation");
                }
            }
        }
        insertPhotos(accommodation,accommodationRequest.getPhotos());

        accommodation.setOnHoldStatus(AccommodationOnHoldStatus.WAITING_FOR_EDIT_APPROVAL);
        accommodationRepository.save(accommodation);
        AccommodationUpdate accommodationUpdateRequest = new AccommodationUpdate(accommodation,AccommodationUpdateType.EDIT_REQUEST,AccommodationUpdateStatus.Created);
        accommodationUpdateRepository.save(accommodationUpdateRequest);
        return new MessageResponse(true,"succesfuly added new request");
    }
}
