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
        int position = Product.productList.size() - 1;

        //verificação
        Assert.assertEquals("Banana", Product.productList.get(position).getName());
        Assert.assertEquals(5.0, Product.productList.get(position).getPrice().doubleValue(), 0.01);
        Assert.assertEquals(150, (int) Product.productList.get(position).getQuantity());
        Assert.assertEquals("FRUTA",Product.productList.get(position).getCategory());
    }

    @Test
    public void addProductByImportTest() {
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        int position = Product.productList.size() - 1;

        //verificação
        Assert.assertEquals("2tve3sxb", Product.productList.get(position).getCode());
        Assert.assertEquals(913387273046L, (long) Product.productList.get(position).getBarCode());
        Assert.assertEquals("1/2022", Product.productList.get(position).getSeries());
        Assert.assertEquals("Banana", Product.productList.get(position).getName());
        Assert.assertEquals("Fruta amarela", Product.productList.get(position).getDescription());
        Assert.assertEquals("FRUTA", Product.productList.get(position).getCategory());
        Assert.assertEquals(1.50, Product.productList.get(position).getGrossAmount().doubleValue(), 0.01);
        Assert.assertEquals(150, Product.productList.get(position).getTaxes().intValue());
        Assert.assertEquals(5.43, Product.productList.get(position).getPrice().doubleValue(), 0.01);
        Assert.assertEquals("17/10/2022", Product.productList.get(position).getManufacturingDate());
        Assert.assertEquals("15/11/2022", Product.productList.get(position).getExpirationDate());
        Assert.assertEquals("n/a", Product.productList.get(position).getColor());
        Assert.assertEquals("n/a", Product.productList.get(position).getMaterial());
        Assert.assertEquals(150, (int) Product.productList.get(position).getQuantity());
    }

    @Test
    public void editProductTest() throws ParseException {
        //cenário
        ProductService ps = new ProductService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Main.path = "src/test/java/test.csv";
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
        ps.addProductByImport("src/test/java/pattern.csv");
        ps.editProduct(barCode, series, name, description, category, grossAmount, taxes, price, manufacturingDate, expirationDate, color, material, quantity, position);

        //verificação
        Assert.assertEquals("2tve3sxb",Product.productList.get(position).getCode());
        Assert.assertEquals(913387200000L,(long) Product.productList.get(position).getBarCode());
        Assert.assertEquals("2/2022",Product.productList.get(position).getSeries());
        Assert.assertEquals("Maçã",Product.productList.get(position).getName());
        Assert.assertEquals("Fruta vermelha",Product.productList.get(position).getDescription());
        Assert.assertEquals("FRUTA",Product.productList.get(position).getCategory());
        Assert.assertEquals(2.50,Product.productList.get(position).getGrossAmount().doubleValue(), 0.01);
        Assert.assertEquals(100,Product.productList.get(position).getTaxes().intValue());
        Assert.assertEquals(7.25, Product.productList.get(position).getPrice().doubleValue(), 0.01);
        Assert.assertEquals("27/10/2022",Product.productList.get(position).getManufacturingDate());
        Assert.assertEquals("25/11/2022",Product.productList.get(position).getExpirationDate());
        Assert.assertEquals("vermelho",Product.productList.get(position).getColor());
        Assert.assertEquals("n/a",Product.productList.get(position).getMaterial());
        Assert.assertEquals(200,(int) Product.productList.get(position).getQuantity());
    }

    @Test
    public void removeProductTest(){
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        int position = 0;

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        int tamInicial = Product.productList.size();
        ps.removeProduct(position);
        int tamFinal = tamInicial - 1;

        //verificação
        Assert.assertEquals(tamFinal,Product.productList.size());
    }

    @Test
    public void addQuantityTest(){
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        int position = 0;
        int quantityToBeAdded = 50;

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        ps.addQuantity(position,quantityToBeAdded);

        //verificação
        Assert.assertEquals(200, (int) Product.productList.get(position).getQuantity());
    }

    @Test
    public void removeQuantityTest(){
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        int position = Product.productList.size();
        int quantityToBeRemoved = 50;

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        ps.removeQuantity(position,quantityToBeRemoved);

        //verificação
        Assert.assertEquals(100, (int) Product.productList.get(position).getQuantity());
    }

    @Test
    public void verifyExistingProductTest(){
        //cenário
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        String code = "2tve3sxb";

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        int position = ps.verifyExistingProduct(code);

        //verificação
        Assert.assertEquals(0,position);
    }
}