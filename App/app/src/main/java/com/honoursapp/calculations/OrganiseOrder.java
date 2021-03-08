package com.honoursapp.calculations;

import android.widget.ArrayAdapter;

import com.honoursapp.classes.Order;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class OrganiseOrder {

    public Order Organise(Order order){
        // Priority list for order structure
        String[] structure = {"Drinks", "Starters", "Mains", "Sides","Deserts"};
        // Create a new order which is the final order of the order
        Order finalisedOrder = new Order();
        // The standard 'User' 'destination' 'totalPrice' will be kept, only the 'orderItems' will be restructured
        finalisedOrder.setTotalPrice(order.getTotalPrice());
        finalisedOrder.setDestination(order.getDestination());
        finalisedOrder.setUser(order.getUser());

        // Get the current order items
        ArrayList<ItemOrder> cItems = order.getOrderItems();
        // Prepare the new Items order for the new order
        ArrayList<ItemOrder> nItems = new ArrayList<>();
        // Iterate through the structure list and organise
        for(String i : structure){

            // Iterate through the array list and organise it based on what should appear first
            for(ItemOrder io : cItems){
                if(io.getCat().equals(i)){
                    // The item has the category of the structure section and can be added to the finalised order
                    nItems.add(io);
                }
            }
        }

        // Set the order items which has now been structured on the finalised order
        finalisedOrder.setOrderItems(nItems);

        return finalisedOrder;
    }

    public String formatCat(String category){

        // Set the category (generalise this for use in the Organise order Method)
        if(category.equals("SoftDrinks") || category.equals("Beer") || category.equals("Wine") || category.equals("Cider") || category.equals("HotDrinks") || category.equals("Vodka")
                || category.equals("Gin") || category.equals("Rum") || category.equals("Whisky") || category.equals("Liqueurs")){

            // The category is a sub category of drinks, set the category on the order to drinks
            return "Drinks";

        }else if(category.equals("Curries") || category.equals("Tandoori") || category.equals("Specials") || category.equals("European")){
            // The category is a sub category of mains, set the category on the order to mains
            return "Mains";
        }else if(category.equals("VegSides") || category.equals("Rice") || category.equals("Breads") || category.equals("OtherSides")){
            // The category is a sub category of sides, set the category on the order to sides
            return "Sides";
        }else{
            // The category is either a starter, or a desert and should remain unchanged
            return category;
        }

    }

}
