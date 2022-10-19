package Utilities;

import application.Main;
import entities.Product;
import org.junit.Assert;
import org.junit.Test;
import service.ProductService;

public class UtilsTest {

    @Test
    public void listProductBuilderTest(){
        //cenário
        Utils u = new Utils();
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        Product.productList.clear();

        //ação
        try {
            ps.addProductByImport("src/test/java/pattern.csv");
            String result = u.listProductBuilder().toString();

        //verificação
        Assert.assertEquals("""
                Código, Código de barras, Série, Nome, Descrição, Categoria, Valor bruto, Impostos (%), Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque

                2tve3sxb,913387273046,1/2022,Banana,"Fruta amarela",FRUTA,"1.5","150","5.4375",17/10/2022,15/11/2022,n/a,n/a,150
                """,result);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void writeNewCSVFileTest(){
        //cenário
        Utils u = new Utils();
        ProductService ps = new ProductService();

        Main.path = "src/test/java/test.csv";
        Product.productList.clear();

        //ação
        ps.addProductByImport("src/test/java/pattern.csv");
        u.writeNewCSVFile();

        //verificação
        /* Verificação manual, os arquivos test.csv e pattern.csv devem ser iguais
        * */
    }
}