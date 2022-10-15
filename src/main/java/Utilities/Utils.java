package Utilities;

import Utilities.CsvWriter.HeaderColumnNameAndOrderMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import service.ProductServiceException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import application.Main;
import entities.Product;

public class Utils {

    public StringBuilder listProductBuilder (){
        StringBuilder products = new StringBuilder();
        Product p = new Product();

        for (int i = 0; i<Product.productList.size(); i++) {
            products.append(p.toString(i)).append("\n");
        }

        return new StringBuilder("Código, Código de barras, Série, Nome, Descrição, Categoria, Valor bruto, Impostos (%), Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n\n" + products);
    }

    public void writeNewCSVFile(){
        try{
            Writer writer = Files.newBufferedWriter(Paths.get(Main.path));
            StatefulBeanToCsv<Product> csvWriter = new StatefulBeanToCsvBuilder<Product>(writer)
                    .withApplyQuotesToAll(false)
                    .withMappingStrategy(new HeaderColumnNameAndOrderMappingStrategy<>(Product.class))
                    .withQuotechar('"')
                    .withEscapechar('"')
                    .build();
            csvWriter.write(Product.productList);


            writer.flush();
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
            throw new ProductServiceException(e.getMessage());
        }
    }
}