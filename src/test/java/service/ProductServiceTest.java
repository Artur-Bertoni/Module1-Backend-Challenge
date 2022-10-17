package service;

import application.Main;
import entities.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductServiceTest {

    @Test
    public void addProductTest(){
        //cenário
        ProductService ps = new ProductService();

        String name = "Banana";
        BigDecimal price = BigDecimal.valueOf(5.00);
        Integer quantity = 150;
        String category = "Fruta";
        Main.path = "test.csv";

        //ação
        ps.addProduct(name, price, quantity, category);

        //verificação
        Assert.assertEquals("Banana", Product.productList.get(0).getName());
        Assert.assertEquals(BigDecimal.valueOf(5.00), Product.productList.get(0).getPrice());
        Assert.assertEquals(150, (int) Product.productList.get(0).getQuantity());
        Assert.assertEquals("FRUTA",Product.productList.get(0).getCategory());
    }

    @Test
    public void addProductByImportTest() {
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");

        //verificação
        Assert.assertEquals("2tve3sxb",Product.productList.get(0).getCode());
        Assert.assertEquals(913387273046L,(long) Product.productList.get(0).getBarCode());
        Assert.assertEquals("1/2022",Product.productList.get(0).getSeries());
        Assert.assertEquals("Banana",Product.productList.get(0).getName());
        Assert.assertEquals("Fruta amarela",Product.productList.get(0).getDescription());
        Assert.assertEquals("FRUTA",Product.productList.get(0).getCategory());
        Assert.assertEquals(1.50,Product.productList.get(0).getGrossAmount().doubleValue(), 0.01);
        Assert.assertEquals(150,Product.productList.get(0).getTaxes().intValue());
        Assert.assertEquals(5.43, Product.productList.get(0).getPrice().doubleValue(), 0.01);
        Assert.assertEquals("17/10/2022",Product.productList.get(0).getManufacturingDate());
        Assert.assertEquals("15/11/2022",Product.productList.get(0).getExpirationDate());
        Assert.assertEquals("n/a",Product.productList.get(0).getColor());
        Assert.assertEquals("n/a",Product.productList.get(0).getMaterial());
        Assert.assertEquals(150,(int) Product.productList.get(0).getQuantity());
    }

    @Test
    public void editProductTest() throws ParseException {
        //cenário
        ProductService ps = new ProductService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Main.path = "src/test/java/test.csv";
        ps.addProductByImport("src/test/java/pattern.csv");

        long barCode = 913387200000L;
        String series = "2/2022";
        String name = "Maçã";
        String description = "Fruta vermelha";
        String category = "Fruta";
        BigDecimal grossAmount = BigDecimal.valueOf(2.50);
        BigDecimal taxes = BigDecimal.valueOf(100);
        BigDecimal price = null;
        Date manufacturingDate = sdf.parse("27/10/2022");
        Date expirationDate = sdf.parse("25/11/2022");
        String color = "vermelho";
        String material = "n/a";
        String quantity = "200";
        int position = 0;

        //ação
        ps.editProduct(barCode, series, name, description, category, grossAmount, taxes, price, manufacturingDate, expirationDate, color, material, quantity, position);

        //verificação
        Assert.assertEquals("2tve3sxb",Product.productList.get(0).getCode());
        Assert.assertEquals(913387200000L,(long) Product.productList.get(0).getBarCode());
        Assert.assertEquals("2/2022",Product.productList.get(0).getSeries());
        Assert.assertEquals("Maçã",Product.productList.get(0).getName());
        Assert.assertEquals("Fruta vermelha",Product.productList.get(0).getDescription());
        Assert.assertEquals("FRUTA",Product.productList.get(0).getCategory());
        Assert.assertEquals(2.50,Product.productList.get(0).getGrossAmount().doubleValue(), 0.01);
        Assert.assertEquals(100,Product.productList.get(0).getTaxes().intValue());
        Assert.assertEquals(7.25, Product.productList.get(0).getPrice().doubleValue(), 0.01);
        Assert.assertEquals("27/10/2022",Product.productList.get(0).getManufacturingDate());
        Assert.assertEquals("25/11/2022",Product.productList.get(0).getExpirationDate());
        Assert.assertEquals("vermelho",Product.productList.get(0).getColor());
        Assert.assertEquals("n/a",Product.productList.get(0).getMaterial());
        Assert.assertEquals(200,(int) Product.productList.get(0).getQuantity());
    }
}