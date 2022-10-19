package service;

import Utilities.Utils;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import entities.Product;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Math;

public class ProductService extends Product {

    public void addProduct(String name, BigDecimal price, Integer quantity, String category){
        try{
            Product p = new Product();
            Utils u = new Utils();

            p.setName(name);
            p.setPrice(price);
            p.setCategory(category.toUpperCase());
            p.setQuantity(quantity);

            String code;
            long barCode;

            boolean exit;
            do {
                exit = true;
                code = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
                barCode = Math.abs(Long.parseLong(RandomStringUtils.randomNumeric(12)));

                for (Product p2 : productList) {
                    if (code.equals(p2.getCode()) || barCode == p2.getBarCode()) {
                        exit = false;
                        break;
                    }
                }
            } while(!exit);

            p.setCode(code);
            p.setBarCode(barCode);
            p.setTaxes(null);
            p.setGrossAmount(null);
            p.setSeries(null);
            p.setDescription(null);
            p.setManufacturingDate(null);
            p.setExpirationDate(null);
            p.setColor(null);
            p.setMaterial(null);

            productList.add(p);

            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public void addProductByImport(String path) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            CSVReader csvReader = new CSVReader(new FileReader(path));

            List<Map<String,String>> lines = new ArrayList<>();

            String[] headers = csvReader.readNext(),
                    columns;

            for (int i=0; i < headers.length; i++){
                headers[i] = headers[i].toLowerCase();
            }

            while (true){
                try {
                    if ((columns = csvReader.readNext()) == null) break;
                } catch (IOException | CsvValidationException e) {
                    throw new RuntimeException(e);
                }
                Map<String,String> campos = new HashMap<>();

                for (int i=0; i< columns.length; i++){
                    campos.put(headers[i], columns[i]);
                }

                lines.add(campos);
            }

            final int[] cont = {0};

            lines.forEach(cols -> {
                String code = cols.get("código");
                Long barCode = cols.get("codigo de barras").equals("null") ? null : Long.parseLong(cols.get("código de barras"));
                String series = cols.get("série");
                String name = cols.get("nome");
                String description = cols.get("descrição");
                String category = cols.get("categoria");
                BigDecimal taxes = cols.get("impostos (%)").equals("null") ? null : new BigDecimal(cols.get("impostos (%)").replace(',','.'));
                BigDecimal grossAmount = cols.get("valor bruto").equals("null") ? null : new BigDecimal(cols.get("valor bruto").replace(',','.'));
                BigDecimal price = null;
                if (taxes != null && grossAmount != null){
                    price = grossAmount.add((taxes.divide(BigDecimal.valueOf(100))).multiply(grossAmount));
                    price = price.add(price.multiply(BigDecimal.valueOf(0.45)));
                }
                Date manufacturingDate = null, expirationDate = null;
                Integer quantity = (cols.get("quantidade") == null || cols.get("quantidade").equals("null")) ? null : Integer.parseInt(cols.get("quantidade"));

                try {
                    if (!cols.get("data de fabricação").equals("n/a") && !cols.get("data de fabricação").equals("") && cols.get("data de fabricação") != null){
                        manufacturingDate = sdf.parse(cols.get("data de fabricação"));
                    }
                    if (!cols.get("data de validade").equals("n/a") && !cols.get("data de validade").equals("") && cols.get("data de validade") != null){
                        expirationDate = sdf.parse(cols.get("data de validade"));
                    }
                } catch (ParseException e) {
                    throw new ProductServiceException(e.getMessage());
                }

                String color = cols.get("cor");
                String material = cols.get("material");

                Product p = new Product();

                p.setCode(code);
                p.setBarCode(barCode);
                p.setSeries(series);
                p.setName(name);
                p.setDescription(description);
                p.setCategory(category);
                p.setGrossAmount(grossAmount);
                p.setTaxes(taxes);
                p.setPrice(price);
                p.setManufacturingDate(manufacturingDate);
                p.setExpirationDate(expirationDate);
                p.setColor(color);
                p.setMaterial(material);
                p.setQuantity(quantity);

                productList.add(p);
                cont[0]++;
            });
            Utils u = new Utils();
            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public void editProduct(Long barCode, String series, String name, String description, String category, BigDecimal grossAmount, BigDecimal taxes, BigDecimal price, Date manufacturingDate, Date expirationDate, String color, String material, String quantity, int position){
        try{
            Utils u = new Utils();

            if (barCode != null) productList.get(position).setBarCode(barCode);
            if (series != null) productList.get(position).setSeries(series);
            if (name != null) productList.get(position).setName(name);
            if (description != null) productList.get(position).setDescription(description);
            if (category != null) productList.get(position).setCategory(category.toUpperCase());
            if (grossAmount != null) productList.get(position).setGrossAmount(grossAmount);
            if (taxes != null) productList.get(position).setTaxes(taxes);
            if (price != null) productList.get(position).setPrice(price);
            else {
                assert grossAmount != null && taxes != null;
                price = grossAmount.add((taxes.divide(BigDecimal.valueOf(100))).multiply(grossAmount));
                price = price.add(price.multiply(BigDecimal.valueOf(0.45)));
                productList.get(position).setPrice(price);
            }
            if (manufacturingDate != null) productList.get(position).setManufacturingDate(manufacturingDate);
            if (expirationDate != null) productList.get(position).setExpirationDate(expirationDate);
            if (color != null) productList.get(position).setColor(color);
            if (material != null) productList.get(position).setMaterial(material);
            if (quantity != null) productList.get(position).setQuantity(Integer.parseInt(quantity));

            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public boolean removeProduct(int position){
        try{
            Utils u = new Utils();

            productList.remove(position);

            u.writeNewCSVFile();
            return true;
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public void addQuantity(int position, int quantity){
        try{
            Utils u = new Utils();

            productList.get(position).setQuantity(productList.get(position).getQuantity()+quantity);

            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public void removeQuantity(int position, int quantity){
        try{
            if (productList.get(position).getQuantity()-quantity < 0){
                throw new ProductServiceException("A quantia inserida para a remoção é maior do que a quantia em estoque");
            }
            Utils u = new Utils();

            productList.get(position).setQuantity(productList.get(position).getQuantity() - quantity);

            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public int verifyExistingProduct(String code){
        try{
            for (int position=0; position<Product.productList.size(); position++){
                if (code.equalsIgnoreCase(Product.productList.get(position).getCode())){
                    return position;
                }
            }
            return -1;
        } catch (Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }
}