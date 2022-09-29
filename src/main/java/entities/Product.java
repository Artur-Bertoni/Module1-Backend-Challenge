package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    //Atributos
    private String name, category;
    private Float price;
    private Integer quantity;

    public static List<Product> productList = new ArrayList<>();

    //Constructors
    public Product() {
    }

    //Getters / Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    //Métodos
    public boolean addProduct(String name, Float price, String quantity, String category){
        try{
            Product p = new Product();

            p.setName(name);
            p.setPrice(price);
            p.setCategory(category);
            p.setQuantity(Integer.parseInt(quantity));

            productList.add(p);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean editProduct(String name, Float price, String quantity, String category, int nro){
        try{
            if (name != null) productList.get(nro).setName(name);
            if (price != null) productList.get(nro).setPrice(price);
            if (quantity != null) productList.get(nro).setQuantity(Integer.parseInt(quantity));
            if (category != null) productList.get(nro).setCategory(category);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean removeProduct(int nro){
        try{
            productList.remove(nro);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean addQuantity(int nro, int quantity){
        try{
            productList.get(nro).setQuantity(productList.get(nro).quantity+quantity);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean removeQuantity(int nro, int quantity){
        try{
            if (productList.get(nro).getQuantity()-quantity < 0){
                return false;
            }

            productList.get(nro).setQuantity(productList.get(nro).quantity-quantity);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String toString(int i) {
        return  "| Nome: " + productList.get(i).getName() +
                " | Preço: $ " + String.format("%.2f",productList.get(i).getPrice()) +
                " | Quantidade em Estoque: " + productList.get(i).getQuantity() +
                " | Categoria: " + productList.get(i).getCategory() +
                " |";
    }
}
