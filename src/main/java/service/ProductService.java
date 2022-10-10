package service;

import com.opencsv.CSVReader;
import entities.Product;
import org.apache.commons.lang3.RandomStringUtils;

import javax.swing.*;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Math;
//TODO criar e implementar exceptions
public class ProductService extends Product {
    public boolean addProduct(String name, BigDecimal price, String quantity, String category){
        try{
            if (Integer.parseInt(quantity) >= 0){
                Product p = new Product();

                p.setName(name);
                p.setPrice(price);
                p.setCategory(category);
                p.setQuantity(Integer.parseInt(quantity));
                p.setCode(RandomStringUtils.randomAlphanumeric(8).toLowerCase());

                p.setBarCode(Math.abs(new Random().nextLong()));
                p.setSeries("n/a");
                p.setDescription("n/a");
                p.setManufacturingDate(new Date());
                p.setExpirationDate(null);
                p.setColor("n/a");
                p.setMaterial("n/a");

                productList.add(p);

                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean addProductByImport(String path){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            CSVReader csvReader = new CSVReader(new FileReader(path));

            List<Map<String,String>> lines = new ArrayList<>();

            String[] headers = csvReader.readNext(),
                    columns;

            for (int i=0; i < headers.length; i++){
                headers[i] = headers[i].toLowerCase();
            }

            while ((columns = csvReader.readNext()) != null){
                Map<String,String> campos = new HashMap<>();

                for (int i=0; i< columns.length; i++){
                    campos.put(headers[i], columns[i]);
                }

                lines.add(campos);
            }

            final int[] cont = {0};

            lines.forEach(cols -> {
                String code = cols.get("código");
                Long barCode = Long.parseLong(cols.get("codigo de barras"));
                String series = cols.get("série");
                String name = cols.get("nome");
                String description = cols.get("descrição");
                String category = cols.get("categoria");
                BigDecimal price = new BigDecimal(cols.get("valor bruto").replace(",",".")).add(((new BigDecimal(cols.get("impostos (%)").replace(",","."))).divide(new BigDecimal(100))).multiply(new BigDecimal(cols.get("valor bruto").replace(",","."))));
                Date manufacturingDate = null, expirationDate = null;

                try {
                    if (!cols.get("data de fabricação").equals("n/a")){
                        manufacturingDate = sdf.parse(cols.get("data de fabricação"));
                    }
                    if (!cols.get("data de validade").equals("n/a")){
                        expirationDate = sdf.parse(cols.get("data de validade"));
                    }
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null,"Erro ("+e.getMessage()+") ao importar o produto '"+name+"'","Importar Produto", JOptionPane.ERROR_MESSAGE);
                }

                String color = cols.get("cor");
                String material = cols.get("material");

                try{
                    Product p = new Product();

                    p.setCode(code);
                    p.setBarCode(barCode);
                    p.setSeries(series);
                    p.setName(name);
                    p.setDescription(description);
                    p.setCategory(category);
                    p.setPrice(price);
                    p.setManufacturingDate(manufacturingDate);
                    p.setExpirationDate(expirationDate);
                    p.setColor(color);
                    p.setMaterial(material);
                    p.setQuantity(0);

                    productList.add(p);
                    cont[0]++;
                }
                catch (Exception e){
                    throw e;
                }
            });

            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    //TODO ajustar edições de protudo
    public boolean editProduct(String name, BigDecimal price, String quantity, String category, String code){
        try{
            int i = verifyExistingProduct(code);

            if (i != -1){
                if (name != null) productList.get(i).setName(name);
                if (price != null) productList.get(i).setPrice(price);
                if (quantity != null) productList.get(i).setQuantity(Integer.parseInt(quantity));
                if (category != null) productList.get(i).setCategory(category);
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean removeProduct(String code){
        try{
            for (int i=0; i<productList.size(); i++){
                if (productList.get(i).getCode().equals(code)){
                    productList.remove(i);
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean addQuantity(String code, int quantity){
        try{
            int i = verifyExistingProduct(code);
            if (i != -1){
                productList.get(i).setQuantity(productList.get(i).getQuantity()+quantity);
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean removeQuantity(String code, int quantity){
        try{
            int i = verifyExistingProduct(code);

            if (i != -1){
                if (productList.get(i).getQuantity()-quantity < 0){
                    return false;
                }

                productList.get(i).setQuantity(productList.get(i).getQuantity() -quantity);
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public int verifyExistingProduct(String code){
        for (int position=0; position<Product.productList.size(); position++){
            if (code.equalsIgnoreCase(Product.productList.get(position).getCode())){
                return position;
            }
        }
        return -1;
    }
}