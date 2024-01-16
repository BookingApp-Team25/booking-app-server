package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

public class AccountDetailsResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private double rating;
    public AccountDetailsResponse() {
    }

    public AccountDetailsResponse(String id,String username, String firstName, String lastName, String address, String phoneNumber) {
        this.id=id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating=0;
    }

    public AccountDetailsResponse(String id,String username, String firstName, String lastName, String address, String phoneNumber,double rating) {
        this.id=id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating=rating;
    }

    public double getRating() {
        return rating;
    }

    public String getId(){return id;}
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
