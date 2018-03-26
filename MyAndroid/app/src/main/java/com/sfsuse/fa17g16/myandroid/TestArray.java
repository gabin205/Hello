package com.sfsuse.fa17g16.myandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Gabin on 10.03.2018.
 */

public class TestArray {


    public static void main(String[]args){

        //Array
        String[] carsArray =new String[5];
        carsArray[0]="VW";
        carsArray[1]="BMW";
        carsArray[2]="Audi";
        carsArray[3]="Volvo";
        carsArray[4]="Mercedes";

        Integer[] integerArray =new Integer[5];
        integerArray[0]=5;


        //Collection -> List --> Arraylist

        ArrayList<String> listCar = new ArrayList<>();
        listCar.add("VW");
        listCar.add("BMW");
        listCar.add("Volvo");

        listCar.get(0);

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("Renault");

        //Map
        Map<Integer,String> bundesliga = new HashMap<>();
        bundesliga.put(1,"FC Bayern");

        bundesliga.get(1);

    }

}
