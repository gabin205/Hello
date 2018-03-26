package com.sfsuse.fa17g16.myandroid;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Gabin on 13.03.2018.
 */

public class SearchItem {
    private String header;
    private String cost;
    private String size;
    private String street;
    private String rooms;
    private String zipcode;
    private String location;
    private ArrayList<String> mediaListe;
    private String seller_id;



    private String realestate_id;
    //add other here
    private String first_name;
    private String last_name;
    private String email;
    private String description;
    private String build_at;
    private ArrayList<String> equitmentListe;



    public SearchItem(String header, String cost, String size, String street, String rooms, String zipcode, String location, String description, String build_at, String first_name, String last_name, String email,String seller_id,String realestate_id) {
        this.header = header;
        this.cost = cost;
        this.size = size;
        this.street = street;
        this.rooms = rooms;
        this.zipcode = zipcode;
        this.location = location;
        this.description = description;
        this.build_at = build_at;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email =email;
        this.seller_id=seller_id;
        this.realestate_id=realestate_id;
        this.equitmentListe = new ArrayList<String>();
        this.mediaListe = new ArrayList<String>();
    }


    public String getHeader() {
        return header;
    }

    public String getSize() {
        return size;
    }

    public String getCost() {
        return cost;
    }

    public String getStreet() {
        return street;
    }

    public String getRooms() {
        return rooms;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getLocation() {
        return location;
    }

    //add other here
    public String getDescription() {return description; }

    public String getBuild_at() {return build_at; }

    public String getFirst_name() {return first_name; }

    public String getLast_name() {return last_name; }

    public String getEmail() {return email;}

    public String getSeller_id() {
        return seller_id;
    }

    public String getRealestate_id() {
        return realestate_id;
    }

    public ArrayList<String> getequipmentItem(){return equitmentListe; }

    public void setRealestate_id(String realestate_id) {
        this.realestate_id = realestate_id;
    }

    public void addMedia(String path) {
        //to remove this line in live system "localhost"
        String image = path.replace("localhost", Utils.HOST);
        mediaListe.add(image);
    }

    public ArrayList<String> getMedias(){
        return mediaListe;
    }

    public String getFirtImage(){
        //return mediaListe.size() == 0 ? "":mediaListe.get(0);
        if(mediaListe.size() == 0){
            return "";
        }else{
            return mediaListe.get(0);
        }
    }

    public void addEquipment(String equipment_type, String equipment_name) {
        equitmentListe.add(equipment_name+" "+equipment_type);
    }
}
