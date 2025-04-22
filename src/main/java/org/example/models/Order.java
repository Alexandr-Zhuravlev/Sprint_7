package org.example.models;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Number rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order setColor(String[] color) {
        this.color = color;
        return this;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Order setRentTime(Number rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Order setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }




}
