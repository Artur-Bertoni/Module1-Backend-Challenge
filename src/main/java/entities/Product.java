package entities;

import service.ProductServiceException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {

    private String code, category, series, description, color, material, name;
    private BigDecimal grossAmount, taxes, price;
    private Integer quantity;
    private Long barCode;
    private Date manufacturingDate, expirationDate;

    public static List<Product> productList = new ArrayList<>();

    public Product() {
    }

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

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
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

    public String getManufacturingDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return manufacturingDate == null ? null : sdf.format(manufacturingDate);
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getExpirationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return expirationDate == null ? null : sdf.format(expirationDate);
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString(int i) {
        try{
            return  productList.get(i).getCode() + ","            + productList.get(i).getBarCode() + "," +
                    productList.get(i).getSeries() + ","          + productList.get(i).getName() + ",\"" +
                    productList.get(i).getDescription() + "\","   + productList.get(i).getCategory() + ",\"" +
                    productList.get(i).getGrossAmount() + "\",\"" + productList.get(i).getTaxes() + "\",\"" +
                    productList.get(i).getPrice() + "\","         + productList.get(i).getManufacturingDate() + "," +
                    productList.get(i).getExpirationDate() + ","  + productList.get(i).getColor() + "," +
                    productList.get(i).getMaterial() + ","        + productList.get(i).getQuantity();
        } catch (Exception e){
            throw new ProductServiceException(e.getMessage());
            //throw new ProductServiceException("Retornar lista de produtos");
        }
    }
}