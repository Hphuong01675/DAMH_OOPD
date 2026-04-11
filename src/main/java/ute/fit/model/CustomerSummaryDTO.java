package ute.fit.model;

public class CustomerSummaryDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private int loyaltyPoints;

    public CustomerSummaryDTO() {
    }

    public CustomerSummaryDTO(Long id, String name, String phoneNumber, int loyaltyPoints) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
