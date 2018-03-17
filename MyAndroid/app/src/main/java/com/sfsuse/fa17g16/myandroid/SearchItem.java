package com.sfsuse.fa17g16.myandroid;

import android.net.Uri;

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
    private  String path;


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


    public String getPath() {
        return path;
    }

    /*public Uri getPath() {
        return path;
    }*/

    public SearchItem(String header, String cost, String size, String street, String rooms, String zipcode, String location, String path) {
        this.header = header;
        this.cost = cost;
        this.size = size;
        this.street = street;
        this.rooms = rooms;
        this.zipcode = zipcode;
        this.location = location;
        this.path = path;

    }

}
