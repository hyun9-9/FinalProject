package com.example.finalproject;

import java.util.ArrayList;

public class SearchRepository {
    private ArrayList<String> Ingredients = new ArrayList<String>();
    private static SearchRepository instance = new SearchRepository();
    public static SearchRepository getInstance(){
        return instance;
    }
    public SearchRepository() {

    }
    public ArrayList<String> getAllProducts() {
        return Ingredients;
    }

    public void addProduct(String product) {
        Ingredients.add(product);
    }
    public void clearlist(){
        Ingredients.clear();
    }


}
