package com.duy.carshowroomdemo.util;

import java.util.Random;

public class Util {
    static Random random = new Random();
    public static String getRandPhone(){
        String phone = "09";


        for (int i = 0; i < 8; i++) {
            phone += random.nextInt(10);
        }

        return phone;
    }

    public static String getRandEmail() {
        String email = "";

        for (int i = 0; i < 10; i++) {
            int a = random.nextInt(26) + 97;
            email += (char) a;
        }

        return email + "@gmail.com";
    }

    public static String getRandGender(){
        return (random.nextInt(2) == 0) ? "male":"female";
    }

    public static int getRandInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    public static String getRandName() {
        String[] lastNames = {"Nguyen", "Cao", "Doan", "Dang", "Le", "Tran", "Pham", "Duong", "Dinh", "Ha"};
        String[] middleNames = {"Quoc", "Huynh", "Hoang", "Thanh", "Binh", "Huu", "Hong", "Thi", "Ngoc", "Minh"};
        String[] firstNames = {"Thai", "Kiet", "Khoi", "Huan", "Long", "Nhat", "Dat", "Tri", "Quan", "Khang"};
        return lastNames[getRandInt(lastNames.length)] + " " + middleNames[getRandInt(middleNames.length)] + " " + firstNames[getRandInt(firstNames.length)];
    }

    public static String getRandColor() {
        String[] colors = {"red", "blue", "yellow", "white", "black", "brown", "violet", "emerald", "green", "pink"};
        return colors[getRandInt(colors.length)];
    }

    public static String getRandFuelType() {
        String[] fuelTypes = {"Diesel", "Gasoline", "Bio-diesel", "Ethanol"};
        return fuelTypes[getRandInt(fuelTypes.length)];
    }

    public static String getRandCarName() {
        String[] carNames = {"BOLT EV", "TRAX", "TRAILBLAZER", "EQUINOX", "BLAZER", "TRAVERSE", "TAHOE", "2024 Mustang", "Escape", "Bronco Sport", "Explorer", "Expedition"};
        return carNames[getRandInt(carNames.length)];
    }

    public static String getRandBrand() {
        String[] brands = {"Chevy", "Ford", " Volkswagen Beetel", "Cadilac", "Corolla", "Herbie", "", "Mitsubishi", "Camry", "Alfa Romeo", "Toyota", "Ferrari", "Kia"};
        return brands[Util.getRandInt(brands.length)];
    }

    public static Long getRandPrice() {
        return (getRandInt(4500) + 500) * 1000000L;
    }

    public static int getRandInt(int lowerBound, int upperBound) {
        return getRandInt(lowerBound) + (upperBound - lowerBound);
    }
}