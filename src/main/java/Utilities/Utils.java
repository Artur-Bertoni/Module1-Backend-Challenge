package Utilities;

import entities.Product;

public class Utils {

    public StringBuilder listProductBuilder (){
        StringBuilder products = new StringBuilder();
        Product p = new Product();

        for (int i = 0; i<p.productList.size(); i++) {
            products.append(p.toString(i)).append("\n");
        }

        return products;
    }
}