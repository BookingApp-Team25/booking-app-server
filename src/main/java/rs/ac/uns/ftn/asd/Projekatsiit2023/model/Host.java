package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Host extends User{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "host", orphanRemoval = true)
    private List<Accommodation> accommodations;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<HostReview> reviews;

//    @Column(name = "rating", nullable = true)
//    private double rating;

    public Host() {
    }

    public Host(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
        this.reviews=new ArrayList<HostReview>();
//        this.rating=0;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void addReview(HostReview review){
        this.reviews.add(review);
    }

    public void removeReview(HostReview review){
        this.reviews.remove(review);
    }

    public double getRating(){
        if(reviews.isEmpty()){
            return 0;
        } else {
            int n=0;
            double rating=0;
            for(HostReview r: reviews){
                rating=rating+r.getRating();
                n++;
            }
            return rating/n;
        }
    }

//    public double getRating() {
//        return rating;
//    }
//
//    public void updateRating(){
//        int n=0;
//        double rating=0;
//        if(reviews.isEmpty()){
//            this.rating=0;
//        } else {
//            for (HostReview review : reviews) {
//                rating = rating + review.getRating();
//                n++;
//            }
//            this.rating = rating / n;
//        }
//    }
}
