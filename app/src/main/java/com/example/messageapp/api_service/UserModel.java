package com.example.messageapp.api_service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import lombok.Data;

public class UserModel {

    @Data
    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        // Deserialize JSON to Address object
        public static Address fromJson(String json) {
            Gson gson = new Gson();
            return gson.fromJson(json, Address.class);
        }

        // Serialize Address object to JSON
        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    @Data
    public static class Geo {
        private String lat;
        private String lng;

        // Deserialize JSON to Geo object
        public static Geo fromJson(String json) {
            Gson gson = new Gson();
            return gson.fromJson(json, Geo.class);
        }

        // Serialize Geo object to JSON
        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    @Data
    public static class Company {
        private String name;
        private String catchPhrase;
        private String bs;

        // Deserialize JSON to Company object
        public static Company fromJson(String json) {
            Gson gson = new Gson();
            return gson.fromJson(json, Company.class);
        }

        // Serialize Company object to JSON
        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    @Data
    public static class User {
        private int id;
        private String name;
        private String username;
        private String email;
        private Address address;
        private String phone;
        private String website;
        private Company company;

        // Deserialize JSON to User object
        public static User fromJson(String json) {
            Gson gson = new Gson();
            return gson.fromJson(json, User.class);
        }

        // Serialize User object to JSON
        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    // Method to convert List<User> to JSON
    public static String userListToJson(List<User> userList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(userList);
    }
}
