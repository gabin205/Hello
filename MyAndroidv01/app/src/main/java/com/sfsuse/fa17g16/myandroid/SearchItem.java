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
    //private Uri path;
    //private  String id;
    private ArrayList<String> mediaListe;


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


    /*public Uri getPath() {
        return path;
    }*/

    public SearchItem(String header, String cost, String size, String street, String rooms, String zipcode, String location) {
        this.header = header;
        this.cost = cost;
        this.size = size;
        this.street = street;
        this.rooms = rooms;
        this.zipcode = zipcode;
        this.location = location;
        this.mediaListe = new ArrayList<String>();
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
}
