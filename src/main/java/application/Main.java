package application;

import entities.Product;
import service.ProductService;
import Utilities.Utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("images/checkmark.png");

        Locale.setDefault(Locale.US);

        APP:
        do{
            Object[] initialOptions = {"Adicionar Novo Produto", "Editar Produto", "Excluir Produto", "Importar Mostruário da Fábrica", "Listar Produtos", "Sair"};

            int option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Bem-vindo(a)!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, initialOptions, initialOptions[0]);

            Product p = new Product();
            ProductService ps = new ProductService();
            Utils u = new Utils();

            switch (option) {
                case 0 -> { //Add product
                    JTextField nameTXT     = new JTextField();    nameTXT.setText(null);
                    JTextField priceTXT    = new JTextField();    priceTXT.setText(null);
                    JTextField quantityTXT = new JTextField();    quantityTXT.setText(null);
                    JTextField categoryTXT = new JTextField();    categoryTXT.setText(null);

                    Object[] addProduct = {"Dados do produto:\n\nNome:", nameTXT, "Preço:", priceTXT,  "Quantidade em Estoque:", quantityTXT, "Categoria:", categoryTXT};

                    do{
                        option = JOptionPane.showConfirmDialog(null, addProduct, "Cadastro de produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                        priceTXT.setText(priceTXT.getText().replace(',','.'));

                        if (option == JOptionPane.OK_OPTION){
                            if (Integer.parseInt(quantityTXT.getText()) >= 0){
                                boolean result = ps.addProduct(nameTXT.getText(), new BigDecimal(priceTXT.getText()), quantityTXT.getText(), categoryTXT.getText());

                                if (result){
                                    JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!","Cadastro de produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                    break;
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"Erro ao cadastrar o produto","Cadastro de produto", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Erro ao cadastrar o produto\n\nO valor referente à quantidade deve ser positivo","Cadastro de produto", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }while(true);
                }
                case 1 -> { //Edit product
                    //TODO alterar arquivo .csv com alterações in-app
                    if (Product.productList.size() >= 1){
                        EDIT_PRODUCT:
                        do{
                            Object[] editOptions = {"Editar Dados Cadastrais do Produto", "Adicionar ao Estoque", "Remover do Estoque", "Cancelar"};

                            option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Editar Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, editOptions, editOptions[0]);

                            switch (option){
                                case 0 -> { //Editar Dados Cadastrais do Produto
                                    do {
                                        String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto à ser editado:\n\n"+
                                                "Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n"+
                                                u.listProductBuilder(), "Editar Dados Cadastrais do Produto", JOptionPane.QUESTION_MESSAGE);
                                        //TODO ajustar verifyExistingProducts
                                        int i = ps.verifyExistingProduct(code);
                                        if (code != null && i != -1){
                                            JTextField nameTXT     = new JTextField();    nameTXT.setText(Product.productList.get(i).getName());
                                            JTextField priceTXT    = new JTextField();    priceTXT.setText(String.format("%.2f",Product.productList.get(i).getPrice()));
                                            JTextField quantityTXT = new JTextField();    quantityTXT.setText(String.valueOf(Product.productList.get(i).getQuantity()));
                                            JTextField categoryTXT = new JTextField();    categoryTXT.setText(Product.productList.get(i).getCategory());

                                            Object[] editProduct = {"Dados do produto "+p.getCode()+":\n\nNome:", nameTXT, "Preço:", priceTXT,  "Quantidade em Estoque:", quantityTXT, "Categoria:", categoryTXT};

                                            do{
                                                option = JOptionPane.showConfirmDialog(null, editProduct, "Editar Dados Cadastrais do Produto", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                                                if (option == JOptionPane.OK_OPTION){
                                                    option = JOptionPane.showConfirmDialog(null, "Código do produto: "+p.getCode()+"\n\nNome: "+nameTXT.getText()+"\nPreço: "+String.format("%.2f",Float.parseFloat(priceTXT.getText()))+
                                                             "\nQuantidade em Estoque: "+quantityTXT.getText()+"\nCategoria: "+categoryTXT.getText()+"\n\nConfirma edição?", "Editar Dados Cadastrais do Produto",
                                                             JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                                    if (option == JOptionPane.YES_OPTION){
                                                        boolean result = ps.editProduct(nameTXT.getText(), new BigDecimal(priceTXT.getText()), quantityTXT.getText(), categoryTXT.getText(), code);

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
                                    }while(true);
                                }
                                case 1 -> { //Adicionar ao Estoque
                                    ADD_QUANTITY:
                                    do{
                                        String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto cuja quantidade de estoque deseja aumentar:\n\n"+
                                                "Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n"+
                                                u.listProductBuilder(), "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                        int i = ps.verifyExistingProduct(code);

                                        if (code != null && i != -1){
                                            String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser adicionada ao estoque:\n\n", "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                            if (quantity != null){
                                                if (Integer.parseInt(quantity) > 0){
                                                    if (ps.addQuantity(code, Integer.parseInt(quantity))){
                                                        JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Adicionar ao Estoque", JOptionPane.INFORMATION_MESSAGE,icon);
                                                        break EDIT_PRODUCT;
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(null,"Erro ao editar o produto","Adicionar ao Estoque", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(null,"Erro ao editar o produto\n\nO valor referente à quantidade deve ser maior que zero","Adicionar ao Estoque", JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                        }
                                        else{
                                            Object[] erroEdicao = {"Inserir novo código", "Cancelar Edição"};

                                            option = JOptionPane.showOptionDialog(null, "O código inserido não corresponde a nenhum produto", "Editar Dados Cadastrais do Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, erroEdicao, erroEdicao[0]);

                                            if (option == 1){
                                                break ADD_QUANTITY;
                                            }
                                        }
                                    }while(true);
                                }
                                case 2 -> { //Remover do Estoque
                                    REMOVE_QUANTITY:
                                    do{
                                        String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto cuja quantidade de estoque deseja diminuir:\n\n"+
                                                "Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n"+
                                                u.listProductBuilder(), "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);


                                        int i = ps.verifyExistingProduct(code);
                                        if (code != null && i != -1){
                                            String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser removida do estoque:\n\n", "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);

                                            if (quantity != null){
                                                if (Integer.parseInt(quantity) > 0){
                                                    if (ps.removeQuantity(code, Integer.parseInt(quantity))){
                                                        JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Remover do Estoque", JOptionPane.INFORMATION_MESSAGE,icon);
                                                        break EDIT_PRODUCT;
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(null,"Erro ao editar o produto","Remover do Estoque", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(null,"Erro ao editar o produto\n\nO valor referente à quantidade deve ser maior que zero","Adicionar ao Estoque", JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                        }
                                        else{
                                            Object[] erroEdicao = {"Inserir novo código", "Cancelar Edição"};

                                            option = JOptionPane.showOptionDialog(null, "O código inserido não corresponde a nenhum produto", "Editar Dados Cadastrais do Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, erroEdicao, erroEdicao[0]);

                                            if (option == 1){
                                                break REMOVE_QUANTITY;
                                            }
                                        }
                                    }while(true);
                                }
                                case 3 -> { //Cancelar
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
                            String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto à ser excluído:\n\n"+
                                    "Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n"+
                                    u.listProductBuilder(), "Excluir Produto", JOptionPane.QUESTION_MESSAGE);

                            int i = ps.verifyExistingProduct(code);
                            if (code != null && i != -1){
                                option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "+Product.productList.get(i).getName()+"?", "Excluir Produto",
                                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                if (option == JOptionPane.YES_OPTION){
                                    boolean result = ps.removeProduct(code);

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
                                boolean result = ps.addProductByImport(path);

                                if (result){
                                    JOptionPane.showMessageDialog(null,"Produtos importados com sucesso!\nLista de novos produtos:\n\n"+u.listProductBuilder(),"Importar Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"Produtos importados com sucesso!\nLista de novos produtos:\n\n"+u.listProductBuilder(),"Importar Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                }
                                break;
                            }
                            catch (Exception e) {
                                JOptionPane.showMessageDialog(null,"Erro ("+e.getMessage()+") ao importar o produto","Importar Produto", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else{
                            break;
                        }
                    }while(true);
                }
                case 4 -> { //List Products
                    JOptionPane.showMessageDialog(null,"Produtos cadastrados:\n\n"+
                            "Código, Código de barras, Série, Nome, Descrição, Categoria, Preço, Data de fabricação, Data de validade, Cor, Material, Quantidade em estoque\n"+
                            u.listProductBuilder(),"Listar Produtos", JOptionPane.INFORMATION_MESSAGE);
                }
                case 5 -> { //Exit
                    break APP;
                }
            }
        }while(true);
    }
}