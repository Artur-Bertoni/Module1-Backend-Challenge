package entities;

import application.Main;
import org.junit.Assert;
import org.junit.Test;
import service.ProductService;

public class ProductTest {

    @Test
    public void toStringTest(){
        //cenário
        Product p = new Product();
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        int position = 0;

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        String result = p.toString(position);

        //verificação
        Assert.assertEquals("2tve3sxb,913387273046,1/2022,Banana,\"Fruta amarela\",FRUTA,\"1.5\",\"150\",\"5.4375\",17/10/2022,15/11/2022,n/a,n/a,150",result);
    }
}