package Utilities;

import application.Main;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import entities.Product;
import service.ProductServiceException;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public StringBuilder listProductBuilder (){
        StringBuilder products = new StringBuilder();
        Product p = new Product();

        for (int i = 0; i<Product.productList.size(); i++) {
            products.append(p.toString(i)).append("\n");
        }

        return new StringBuilder("Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n\n" + products);
    }
    //TODO dar continuidade ao writeNewCSVFile (traduzir, ordenar)
    public void writeNewCSVFile(){
        try{
            Writer writer = Files.newBufferedWriter(Paths.get(Main.path));
            StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

            beanToCsv.write(Product.productList);

            writer.flush();
            writer.close();
        } catch (Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }
}