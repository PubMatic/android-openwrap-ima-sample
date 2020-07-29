/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap.models;

/**
 * Provides setters to pass user information
 */
public class POWUserInfo {

    /**
     * The year of birth in YYYY format.
     * Example :
     * adRequest.setBirthYear(1988);
     */
    private int birthYear;
    /**
     * Set the user gender,
     * Possible options are:
     * OTHER
     * MALE,
     * FEMALE
     */
    private Gender gender;
    /**
     * Google metro code, You can set Designated market area (DMA) code of the user in this
     * field. This field is applicable for US users only
     */
    private String metro;
    /**
     * The user's zip code may be useful in delivering geographically relevant ads
     */
    private String zip;
    /**
     * City of user
     */
    private String city;
    /**
     * Country code using ISO-3166-1-alpha-3.
     */
    private String country;
    /**
     * Comma separated list of keywords, interests, or intent.
     */
    private String userKeywords;
    /**
     * User's location. It is useful in delivering geographically relevant ads.
     * If your application is already accessing the device location, it is highly recommended to
     * set the location coordinates inferred from the device GPS.
     */
    private POWLocation location;

    /**
     * Constructor
     */
    public POWUserInfo() {

    }

    /**
     * Returns user's Birth year
     *
     * @return Birth Year
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * The year of birth in YYYY format.
     * Example :
     * setBirthYear(1988);
     *
     * @param birthYear birth year of user
     */
    public void setBirthYear(int birthYear) {
        if (birthYear > 0) {
            this.birthYear = birthYear;
        }
    }

    /**
     * Returns user's Gender
     *
     * @return User's gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set the user gender,
     * Possible options are:
     * OTHER
     * MALE,
     * FEMALE
     *
     * @param gender User's gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns user's country
     *
     * @return User's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the user country. Country code using ISO-3166-1-alpha-3.
     * For example: for United State Of America, you can use setCountry("USA");
     *
     * @param country User's country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns user's city
     *
     * @return User's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city of user
     * For example: setCity("Los Angeles");
     *
     * @param city City of user
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns User's Metro
     *
     * @return User's metro
     */
    public String getMetro() {
        return metro;
    }

    /**
     * Sets metro / DMA.
     * For example, New York, NY is also known as 501. Los Angeles, CA, on the other hand has been
     * assigned the number 803.
     *
     * @param metro Metro / DMA code of the user.
     */
    public void setMetro(String metro) {
        this.metro = metro;
    }

    /**
     * Returns user's zip
     *
     * @return User's zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the user's zip or postal code. This may be useful in delivering geographically relevant
     * ads
     * For example: for Redwood city, CA use setZip("94063");
     *
     * @param zip user's zip code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns user's Location
     *
     * @return user's Location
     */
    public POWLocation getLocation() {
        return location;
    }

    /**
     * Sets the user's Location.
     *
     * @param location user's Location.
     */
    public void setLocation(POWLocation location) {
        this.location = location;
    }

    /**
     * Defines gender of user
     */
    public enum Gender {
        MALE("M"), FEMALE("F"), OTHER("O");

        private final String value;

        Gender(String val) {
            this.value = val;
        }

        /**
         * Returns the string identifier of gender. Possible values are:
         * "M" - MALE
         * "F" - FEMALE
         * "O" - OTHER
         *
         * @return String identifier of gender
         */
        public String getValue() {
            return value;
        }
    }
}
