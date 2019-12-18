package com.kryptgames.health.fitwithfriends.models;

import java.util.HashMap;

public class Profile {

     private String name;
     private String lastName;
     private String genre;
     private String dob;
     private String height;
     private String weight;
     private String imageRef;
     private String number;
     private String email;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public Profile(){

    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGenre() {
        return genre;
    }

    public String getDob() {
        return dob;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }


    public Profile(String name, String lastName, String genre, String dob, String height, String weight, String imageRef, String number,String email) {
        this.name = name;
        this.lastName = lastName;
        this.genre = genre;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.imageRef = imageRef;
        this.number = number;
        this.email=email;
    }
}
