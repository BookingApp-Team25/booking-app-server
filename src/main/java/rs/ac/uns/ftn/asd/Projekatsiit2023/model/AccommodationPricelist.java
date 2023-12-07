package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class AccommodationPricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "dailyPrice", updatable = false)
    private double dailyPrice;
    @Column(name = "weekendPrice", updatable = false)
    private double weekendPrice;
    @Column(name = "summerPrice", updatable = false)
    private double summerPrice;
    @Column(name = "winterPrice", updatable = false)
    private double winterPrice;

    
    public AccommodationPricelist(double dailyPrice, double weekendPrice, double summerPrice, double winterPrice) {
        this.dailyPrice = dailyPrice;
        this.weekendPrice = weekendPrice;
        this.summerPrice = summerPrice;
        this.winterPrice = winterPrice;
    }

    public AccommodationPricelist() {
        this.dailyPrice = 0.0;
        this.weekendPrice = 0.0;
        this.summerPrice = 0.0;
        this.winterPrice = 0.0;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public double getWeekendPrice() {
        return weekendPrice;
    }

    public double getSummerPrice() {
        return summerPrice;
    }

    public double getWinterPrice() {
        return winterPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public void setWeekendPrice(double weekendPrice) {
        this.weekendPrice = weekendPrice;
    }

    public void setSummerPrice(double summerPrice) {
        this.summerPrice = summerPrice;
    }

    public void setWinterPrice(double winterPrice) {
        this.winterPrice = winterPrice;
    }
}
