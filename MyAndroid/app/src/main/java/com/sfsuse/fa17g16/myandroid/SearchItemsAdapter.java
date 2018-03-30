package com.sfsuse.fa17g16.myandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Gabin on 13.03.2018.
 */

public class SearchItemsAdapter extends BaseAdapter {
    // Une liste de Maison
    private List<SearchItem> ergebnisListe;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    public SearchItemsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        ergebnisListe = new ArrayList<SearchItem>();
    }
    @Override
    public int getCount() {
        return ergebnisListe.size();
    }

    @Override
    public Object getItem(int i) {
        return ergebnisListe.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "search_item.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.search_item, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView header = (TextView)layoutItem.findViewById(R.id.header);
        TextView cost = (TextView)layoutItem.findViewById(R.id.cost);
        TextView size = (TextView)layoutItem.findViewById(R.id.size);
        TextView rooms = (TextView)layoutItem.findViewById(R.id.rooms);
        TextView street = (TextView)layoutItem.findViewById(R.id.street);
        TextView zipcode = (TextView)layoutItem.findViewById(R.id.zipcode);
        TextView location = (TextView)layoutItem.findViewById(R.id.location);

        ImageView imageView = (ImageView)layoutItem.findViewById(R.id.path);

        //(3) : Renseignement des valeurs
        header.setText(ergebnisListe.get(position).getHeader());
        cost.setText(ergebnisListe.get(position).getCost());
        size.setText(ergebnisListe.get(position).getSize());
        street.setText(ergebnisListe.get(position).getStreet());
        rooms.setText(ergebnisListe.get(position).getRooms());
        zipcode.setText(ergebnisListe.get(position).getZipcode());
        location.setText(ergebnisListe.get(position).getLocation());

        String imagePath = ergebnisListe.get(position).getFirtImage();
        if(imagePath.length() == 0){
            imageView.setImageResource(R.drawable.homepage_logo);
        }else{
            Glide.with(mContext).load(imagePath).into(imageView);
        }

        //On retourne l'item créé.
        return layoutItem;
    }

    public void setResult(JSONArray result) {
        try {
            for (int i = 0; i < result.length(); i++) {
                //get infos
                JSONObject obj = result.getJSONObject(i);
                String header = obj.getString("header");
                String size = obj.getString("size");
                String cost = obj.getString("cost");
                String rooms = obj.getString("rooms");
                String description = obj.getString("description");
                String build_at = obj.getString("build_at");

                //*Adresse --> Object
                JSONObject adresse = obj.getJSONObject("adress");
                String street = adresse.getString("street");

                //getzipcode --> Object
                JSONObject zipcodeObject = adresse.getJSONObject("zipcode");
                String zipcode = zipcodeObject.getString("zipcode");
                String location = zipcodeObject.getString("location");

                //get seller
                JSONObject sellerObject = obj.getJSONObject("seller");
                String first_name = sellerObject.getString("first_name");
                String last_name = sellerObject.getString("last_name");
                String email = sellerObject.getString("email");
                String sellerid=sellerObject.getString("id");
                String realestate_id=" ";

                //get equipment
                JSONArray equipmentItem = obj.getJSONArray("equipment");

                //get media --> array
                JSONArray medias = obj.getJSONArray("medias");
                SearchItem item = new SearchItem(header,cost,size,street,rooms,zipcode,location,description,build_at,first_name,last_name,email,sellerid,realestate_id);

                for (int j = 0; j < medias.length(); j++) {
                    JSONObject mediasObject = medias.getJSONObject(j);
                    String path = mediasObject.getString("path");
                     item.setRealestate_id(mediasObject.getString("estate_id"));
                    item.addMedia(path);
                }

                //equipmemt
                for (int k = 0; k < equipmentItem.length(); k++){
                    JSONObject equipmentObject = equipmentItem.getJSONObject(k);
                    String equipment_type = equipmentObject.getString("equipment_type");
                    String equipment_name = equipmentObject.getString("equipment_name");
                    item.addEquipment(equipment_type, equipment_name);
                }
                /*ResourceBundle mediasObject = null;
                assert mediasObject != null;
                String path = mediasObject.getString("path");*/

                ergebnisListe.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<SearchItem> getErgebnisListe(){
        return ergebnisListe;
    }
}
