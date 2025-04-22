package org.example.generators;

import org.example.models.Order;

public class OrderGenerator {

    public static Order generateRandomOrder(String[] color){
        return new Order()
                .setFirstName("Naruto")
                .setLastName("Uchiha")
                .setAddress("Konoha, 142 apt.")
                .setMetroStation("4")
                .setPhone("+7 800 355 35 35")
                .setRentTime(5)
                .setDeliveryDate("2020-06-06")
                .setComment("Saske, come back to Konoha")
                .setColor(color);
    }
}
