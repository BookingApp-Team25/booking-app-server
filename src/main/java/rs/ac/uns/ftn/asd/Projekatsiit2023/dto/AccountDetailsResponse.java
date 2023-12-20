package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

public class AccountDetailsResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public AccountDetailsResponse() {
    }

    public AccountDetailsResponse(String username, String firstName, String lastName, String address, String phoneNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
