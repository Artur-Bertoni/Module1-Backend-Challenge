package application;

import com.opencsv.exceptions.CsvValidationException;
import entities.Product;

import com.opencsv.CSVReader;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("images/checkmark.png");

        //teste
        Locale.setDefault(Locale.US);

        APP:
        do{
            Object[] initialOptions = {"Adicionar Novo Produto", "Editar Produto", "Excluir Produto", "Importar Mostruário da Fábrica", "Sair"};

            int option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Bem-vindo(a)!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, initialOptions, initialOptions[0]);

            Product p = new Product();

            switch (option) {
                case 0 -> { //Add product
                    JTextField nameTXT     = new JTextField();    nameTXT.setText(null);
                    JTextField priceTXT    = new JTextField();    priceTXT.setText(null);
                    JTextField quantityTXT = new JTextField();    quantityTXT.setText(null);
                    JTextField categoryTXT = new JTextField();    categoryTXT.setText(null);

                    Object[] addProduct = {"Dados do produto:\n\nNome:", nameTXT, "Preço:", priceTXT,  "Quantidade em Estoque:", quantityTXT, "Categoria:", categoryTXT};

                    option = JOptionPane.showConfirmDialog(null, addProduct, "Cadastro de produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                    priceTXT.setText(priceTXT.getText().replace(',','.'));

                    if (option == JOptionPane.OK_OPTION){
                        boolean result = p.addProduct(nameTXT.getText(), Float.parseFloat(priceTXT.getText()), quantityTXT.getText(), categoryTXT.getText());

                        if (result){
                            JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!","Cadastro de produto", JOptionPane.INFORMATION_MESSAGE,icon);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Erro ao cadastrar o produto","Cadastro de produto", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                case 1 -> { //Edit product
                    if (Product.productList.size() >= 1){
                        EDIT_PRODUCT:
                        do{
                            Object[] editOptions = {"Editar Dados Cadastrais do Produto", "Adicionar ao Estoque", "Remover do Estoque", "Cancelar"};

                            option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Editar Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, editOptions, editOptions[0]);

                            switch (option){
                                case 0 -> {
                                    do {
                                        StringBuilder products = new StringBuilder();

                                        for (int i = 0; i < Product.productList.size(); i++) {
                                            products.append("#").append(i+1).append(": ").append(p.toString(i)).append("\n");
                                        }

                                        String nro = JOptionPane.showInputDialog(null, "Insira o número correspondente ao produto à ser editado:\n\n"+products, "Editar Dados Cadastrais do Produto", JOptionPane.QUESTION_MESSAGE);

                                        if (nro != null){
                                            boolean OK = false;

                                            for (int i=0; i<Product.productList.size(); i++){
                                                if (Integer.parseInt(nro) < (Product.productList.size()+1)){
                                                    OK = true;
                                                    p.setName(Product.productList.get(i).getName());
                                                    p.setPrice(Product.productList.get(i).getPrice());
                                                    p.setQuantity(Product.productList.get(i).getQuantity());
                                                    p.setCategory(Product.productList.get(i).getCategory());
                                                }
                                            }

                                            if (OK){
                                                JTextField nameTXT     = new JTextField();    nameTXT.setText(p.getName());
                                                JTextField priceTXT    = new JTextField();    priceTXT.setText(String.format("%.2f",p.getPrice()));
                                                JTextField quantityTXT = new JTextField();    quantityTXT.setText(String.valueOf(p.getQuantity()));
                                                JTextField categoryTXT = new JTextField();    categoryTXT.setText(p.getCategory());

                                                Object[] editProduct = {"Dados do produto:\n\nNome:", nameTXT, "Preço:", priceTXT,  "Quantidade em Estoque:", quantityTXT, "Categoria:", categoryTXT};

                                                do{
                                                    option = JOptionPane.showConfirmDialog(null, editProduct, "Editar Dados Cadastrais do Produto", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                                                    if (option == JOptionPane.OK_OPTION){
                                                        option = JOptionPane.showConfirmDialog(null, "Nome: "+nameTXT.getText()+"\nPreço: "+String.format("%.2f",Float.parseFloat(priceTXT.getText()))+
                                                                 "\nQuantidade em Estoque: "+quantityTXT.getText()+"\nCategoria: "+categoryTXT.getText()+"\n\nConfirma edição?", "Editar Dados Cadastrais do Produto",
                                                                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                                        if (option == JOptionPane.YES_OPTION){
                                                            boolean result = p.editProduct(nameTXT.getText(), Float.parseFloat(priceTXT.getText()), quantityTXT.getText(), categoryTXT.getText(), (Integer.parseInt(nro)-1));

                                                            if (result){
                                                                JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Editar Dados Cadastrais do Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                                                break EDIT_PRODUCT;
                                                            }
                                                            else{
                                                                JOptionPane.showMessageDialog(null,"Erro ao editar o produto","Editar Dados Cadastrais do Produto", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    else{
                                                        break;
                                                    }
                                                }while(true);
                                            }
                                            else{
                                                Object[] erroEdicao = {"Inserir novo código", "Cancelar Edição"};

                                                option = JOptionPane.showOptionDialog(null, "O código inserido não corresponde a nenhum produto", "Editar Dados Cadastrais do Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, erroEdicao, erroEdicao[0]);

                                                if (option == 1){
                                                    break EDIT_PRODUCT;
                                                }
                                            }
                                        }
                                        else{
                                            break;
                                        }
                                    }while(true);
                                }
                                case 1 -> {
                                    StringBuilder products = new StringBuilder();

                                    for (int i = 0; i < Product.productList.size(); i++) {
                                        products.append("#").append(i+1).append(": ").append(p.toString(i)).append("\n");
                                    }

                                    String nro = JOptionPane.showInputDialog(null, "Insira o número correspondente ao produto cuja quantidade de estoque deseja aumentar:\n\n"+products, "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                    if (nro != null){
                                        String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser adicionada ao estoque:\n\n", "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                        if (quantity != null){
                                            boolean result = p.addQuantity((Integer.parseInt(nro)-1), Integer.parseInt(quantity));

                                            if (result){
                                                JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Adicionar ao Estoque", JOptionPane.INFORMATION_MESSAGE,icon);
                                                break EDIT_PRODUCT;
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null,"Erro ao editar o produto","Adicionar ao Estoque", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                    }
                                }
                                case 2 -> {
                                    StringBuilder products = new StringBuilder();

                                    for (int i = 0; i < Product.productList.size(); i++) {
                                        products.append("#").append(i+1).append(": ").append(p.toString(i)).append("\n");
                                    }

                                    String nro = JOptionPane.showInputDialog(null, "Insira o número correspondente ao produto cuja quantidade de estoque deseja diminuir:\n\n"+products, "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);

                                    if (nro != null){
                                        String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser removida do estoque:\n\n", "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);

                                        if (quantity != null){
                                            boolean result = p.removeQuantity((Integer.parseInt(nro)-1), Integer.parseInt(quantity));

                                            if (result){
                                                JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Remover do Estoque", JOptionPane.INFORMATION_MESSAGE,icon);
                                                break EDIT_PRODUCT;
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null,"Erro ao editar o produto","Remover do Estoque", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                    }
                                }
                                case 3 -> {
                                    break EDIT_PRODUCT;
                                }
                            }
                        }while(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Não há produtos cadastrados","Editar Produto", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case 2 -> { //Delete product
                    if (Product.productList.size() >= 1){
                        do{
                            StringBuilder products = new StringBuilder();

                            for (int i = 0; i < Product.productList.size(); i++) {
                                products.append("#").append(i+1).append(": ").append(p.toString(i)).append("\n");
                            }

                            String nro = JOptionPane.showInputDialog(null, "Insira o número correspondente ao produto à ser excluído:\n\n"+products, "Excluir Produto", JOptionPane.QUESTION_MESSAGE);

                            if (nro != null){
                                option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "+Product.productList.get(Integer.parseInt(nro)-1).getName()+"?", "Excluir Produto",
                                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                if (option == JOptionPane.YES_OPTION){
                                    boolean result = p.removeProduct(Integer.parseInt(nro)-1);

                                    if (result){
                                        JOptionPane.showMessageDialog(null,"Produto excluído!","Excluir Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                        break;
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null,"Erro ao excluir o produto","Excluir Produto", JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                }
                            }
                            else{
                                break;
                            }
                        }while(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Não há produtos cadastrados","Excluir Produto", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case 3 -> { //Import product
                    do{
                        String path = JOptionPane.showInputDialog(null, "Insira o caminho para o arquivo a ser importado (exemplo: 'C:\\Users\\nome\\arquivo.csv'):", "Importar Produto", JOptionPane.QUESTION_MESSAGE);

                        if (path != null){
                            try {
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
                                    String name = cols.get("nome");
                                    String category = cols.get("categoria");
                                    float price = Float.parseFloat(cols.get("valor bruto").replace(",",".")) + ((Float.parseFloat(cols.get("impostos (%)").replace(",","."))) / 100) * (Float.parseFloat(cols.get("valor bruto").replace(",",".")));

                                    boolean result = p.addProduct(name, price, "0", category);

                                    if(result){
                                        cont[0]++;
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null,"Erro ao importar o produto '"+name+"'","Importar Produto", JOptionPane.ERROR_MESSAGE);
                                    }
                                });

                                StringBuilder products = new StringBuilder();

                                for (int i = cont[0]; i > 0; i--) {
                                    products.append("#").append((cont[0]-i)+1).append(": ").append(p.toString(Product.productList.size()-i)).append("\n");
                                }

                                JOptionPane.showMessageDialog(null,"Produtos importados com sucesso!\nLista de novos produtos:\n\n"+products,"Importar Produto", JOptionPane.INFORMATION_MESSAGE,icon);

                                break;
                            }
                            catch (IOException | CsvValidationException e) {
                                JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Importar Produto", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else{
                            break;
                        }
                    }while(true);
                }
                case 4 -> { //Exit
                    break APP;
                }
            }
        }while(true);
    }
}