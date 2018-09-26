package com.google.sample.cloudvision;

/**
 * Created by Paras Rawat on 31-03-2018.
 */

public class User {


    private String Date,Location,Prob,Status,Images;
    public User(){}

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getProb() {
        return Prob;
    }

    public void setProb(String prob) {
        Prob = prob;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) { Images = images; }

}
