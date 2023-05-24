package com.example.finalproject;

import java.util.ArrayList;

public class ProductRepository {
    private ArrayList<Product> listOfProducts = new ArrayList<Product>();
    private static ProductRepository instance = new ProductRepository();
    public static ProductRepository getInstance(){
        return instance;
    }
    public ProductRepository() {

    }
    public ArrayList<Product> getAllProducts() {
        return listOfProducts;
    }
    public Product getProductById(String productId) {
        Product productById = null;

        for (int i = 0; i < listOfProducts.size(); i++) {
            Product product = listOfProducts.get(i);
            if (product != null && product.getProductId() != null && product.getProductId().equals(productId)) {
                productById = product;
                break;
            }
        }
        return productById;
    }
    public void addProduct(Product product) {
        listOfProducts.add(product);
    }

    public void removeProduct(Product product) {
        listOfProducts.remove(product);
    }
    public void clearlist(){
        listOfProducts.clear();
    }
}
