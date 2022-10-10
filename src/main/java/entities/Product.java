package entities;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    //Atributos
    private String name, category, code, series, description, color, material;
    private BigDecimal price;
    private Integer quantity;
    private Long barCode;
    private Date manufacturingDate, expirationDate;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getBarCode() {
        return barCode;
    }

    public void setBarCode(Long barCode) {
        this.barCode = barCode;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    //Métodos
    public String toString(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String expirationDate = getExpirationDate() == null ? "n/a" : sdf.format(productList.get(i).getExpirationDate());

        return  productList.get(i).getCode() + ", " + productList.get(i).getBarCode() + ", " + productList.get(i).getSeries() + ", " + productList.get(i).getName() + ", '" +
                productList.get(i).getDescription() + "', " + productList.get(i).getCategory() + ", '" + String.format("%.2f",productList.get(i).getPrice()) + "', " +
                sdf.format(productList.get(i).getManufacturingDate()) + ", " + expirationDate + ", " + productList.get(i).getColor() + ", " + productList.get(i).getMaterial() + ", " +
                productList.get(i).getQuantity();
    }
}