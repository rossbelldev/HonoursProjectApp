package com.honoursapp.calculations;

import com.honoursapp.classes.Item;
import com.honoursapp.classes.ItemDB;

import java.util.ArrayList;
import java.util.Arrays;

public class SwapIDBtoItem {

    //Take in the itemDB and change it to a normal Item, return that
    public Item swap(ItemDB itemDB){
        Item i = new Item();

        //Only name, allergens and description don't need changed
        i.setName(itemDB.getName());
        i.setAllergens(itemDB.getAllergens());
        i.setDescription(itemDB.getDescription());

        //Make an array to store the split values (if there are any)
        //Add that array to the array list
        if(itemDB.getProteins().contains(",")){
            String[] proteins = itemDB.getProteins().split(",");
            ArrayList<String> proteinsList = new ArrayList<>(Arrays.asList(proteins));
            i.setProteins(proteinsList);
        }

        //Split the prices into an array
        String[] pricesString = itemDB.getPrice().split(",");
        //Make the array list of type double to be filled
        ArrayList<Double> pricesList = new ArrayList<>();

        if(pricesString.length == 2){

            double p = Double.parseDouble(pricesString[0]);
            pricesList.add(p);

        }else{

            for(int ii = 0; ii < pricesString.length; ii++){
                //Convert the array and add the elements to the double array list
                double p = Double.parseDouble(pricesString[ii]);
                pricesList.add(p);
            }
        }

        //Return the swapped class
        return i;
    }


}
