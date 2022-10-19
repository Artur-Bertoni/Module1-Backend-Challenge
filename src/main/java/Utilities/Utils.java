package Utilities;

import application.Main;
import service.ProductServiceException;

import entities.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Main.path))){
            bw.write("código,codigo de barras,série,nome,descrição,categoria,valor bruto,impostos (%),valor líquido,data de fabricação,data de validade,cor,material,quantidade");
            bw.newLine();

            Product p = new Product();

            for (int i = 0; i<Product.productList.size(); i++) {
                bw.write(p.toString(i));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ProductServiceException(e.getMessage());
        }
    }
}