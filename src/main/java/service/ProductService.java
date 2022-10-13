package service;

import Utilities.Utils;
import com.opencsv.CSVReader;
import entities.Product;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Math;

public class ProductService extends Product {

    public void addProduct(String name, BigDecimal price, String quantity, String category){
        try{
            Product p = new Product();
            Utils u = new Utils();

            p.setName(name);
            p.setPrice(price);
            p.setCategory(category);
            p.setQuantity(Integer.parseInt(quantity));

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

            p.setTaxes(BigDecimal.valueOf(0));
            p.setGrossAmount(BigDecimal.valueOf(0));
            p.setCode(code);
            p.setBarCode(barCode);
            p.setSeries("n/a");
            p.setDescription("n/a");
            p.setManufacturingDate(new Date());
            p.setExpirationDate(null);
            p.setColor("n/a");
            p.setMaterial("n/a");

            productList.add(p);

            u.writeNewCSVFile();
        } catch(Exception e){
            throw new ProductServiceException(e.getMessage());
        }
    }

    public void addProductByImport(String path){
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
                BigDecimal taxes = new BigDecimal(cols.get("impostos (%)").replace(',','.'));
                BigDecimal grossAmount = new BigDecimal(cols.get("valor bruto").replace(',','.'));
                BigDecimal price = grossAmount.add((taxes.divide(BigDecimal.valueOf(100))).multiply(grossAmount));
                price = price.add(price.multiply(BigDecimal.valueOf(0.45)));
                Date manufacturingDate = null, expirationDate = null;

                try {
                    if (!cols.get("data de fabricação").equals("n/a")){
                        manufacturingDate = sdf.parse(cols.get("data de fabricação"));
                    }
                    if (!cols.get("data de validade").equals("n/a")){
                        expirationDate = sdf.parse(cols.get("data de validade"));
                    }
                } catch (ParseException e) {
                    throw new ProductServiceException(e.getMessage());
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
                    p.setGrossAmount(grossAmount);
                    p.setTaxes(taxes);
                    p.setPrice(price);
                    p.setManufacturingDate(manufacturingDate);
                    p.setExpirationDate(expirationDate);
                    p.setColor(color);
                    p.setMaterial(material);
                    p.setQuantity(0);

                    productList.add(p);
                    cont[0]++;
                } catch (Exception e){
                    throw new ProductServiceException(e.getMessage());
                }
            });
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
            if (category != null) productList.get(position).setCategory(category);
            if (grossAmount != null) productList.get(position).setGrossAmount(grossAmount);
            if (taxes != null) productList.get(position).setTaxes(taxes);
            if (price != null) productList.get(position).setPrice(price);
            if (manufacturingDate != null) productList.get(position).setManufacturingDate(manufacturingDate);
            if (expirationDate != null) productList.get(position).setExpirationDate(expirationDate);
            if (color != null) productList.get(position).setColor(color);
            if (material != null) productList.get(position).setMaterial(material);
            if (quantity != null) productList.get(position).setQuantity(Integer.parseInt(quantity));


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