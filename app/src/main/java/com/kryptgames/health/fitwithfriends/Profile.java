package com.kryptgames.health.fitwithfriends;

public class Profile {

     private String name;
     private String lastName;
     private String genre;
     private String dob;
     private String height;
     private String weight;

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

    public Profile(String name, String lastName, String genre, String dob, String height, String weight) {
        this.name = name;
        this.lastName = lastName;
        this.genre = genre;
        this.dob = dob;
        this.height = height;
        this.weight = weight;


    }
}
