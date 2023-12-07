package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.AccommodationUpdateRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class AccommodationUpdateServiceImplementation implements AccommodationUpdateService{
    @Autowired
    AccommodationUpdateRepository accommodationUpdateRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Override
    public Collection<AccommodationUpdateSummaryResponse> getAllAccommodationUpdates(){
        ArrayList<AccommodationUpdate> requests = new ArrayList<AccommodationUpdate>(accommodationUpdateRepository.findAll());
        ArrayList<AccommodationUpdateSummaryResponse> summary = new ArrayList<>();
        for (AccommodationUpdate request : requests){
            summary.add(new AccommodationUpdateSummaryResponse(request.getId(),request.getAccommodation().getName(),
                    request.getAccommodation().getDescription(),
                    request.getAccommodation().getPhotos().get(0),
                    request.getUpdateType()));
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
    public MessageResponse createAccommodationUpdate(AccommodationRequest accommodationRequest) {
        Accommodation newAccommodation = new Accommodation();
        newAccommodation.processAccommodationRequest(accommodationRequest);
        newAccommodation.setOnHoldStatus(AccommodationOnHoldStatus.WAITING_FOR_CREATE_APPROVAL);
        accommodationRepository.save(newAccommodation);
        AccommodationUpdate createRequest = new AccommodationUpdate(newAccommodation,AccommodationUpdateType.CREATE_REQUEST, AccommodationUpdateStatus.Created);
        accommodationUpdateRepository.save(createRequest);
        return new MessageResponse(true,"Successfuly requested accommodation creation");
    }

    @Override
    public MessageResponse createEditRequest(UUID accommodationId, AccommodationRequest accommodationRequest) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.processAccommodationRequest(accommodationRequest);
        accommodation.setOnHoldStatus(AccommodationOnHoldStatus.WAITING_FOR_EDIT_APPROVAL);
        accommodationRepository.save(accommodation);
        AccommodationUpdate accommodationUpdateRequest = new AccommodationUpdate(accommodation,AccommodationUpdateType.EDIT_REQUEST,AccommodationUpdateStatus.Created);
        accommodationUpdateRepository.save(accommodationUpdateRequest);
        return new MessageResponse(true,"succesfuly added new request");
    }
}
