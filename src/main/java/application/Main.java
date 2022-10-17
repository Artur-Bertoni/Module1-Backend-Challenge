package application;

import entities.Product;
import service.ProductService;
import Utilities.Utils;

import service.ProductServiceException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static String path = null;
    public static void main(String[] args) throws ParseException {
        ImageIcon icon = new ImageIcon("images/checkmark.png");
        MaskFormatter dateMask = new MaskFormatter("##/##/####");
        MaskFormatter seriesMask = new MaskFormatter("#/####");

        APP:
        do{
            Object[] initialOptions = {"Adicionar Novo Produto", "Editar Produto", "Excluir Produto", "Importar Mostruário da Fábrica", "Listar Produtos", "Sair"};

            int option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Bem-vindo(a)!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, initialOptions, initialOptions[0]);

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
                        try{
                            option = JOptionPane.showConfirmDialog(null, addProduct, "Cadastro de produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                            priceTXT.setText(priceTXT.getText().replace(',','.'));

                            if (option == JOptionPane.OK_OPTION){
                                if (Integer.parseInt(quantityTXT.getText()) >= 0){
                                    if (path == null){
                                        path = JOptionPane.showInputDialog(null, "Insira o caminho e o nome do arquivoem que deseja salvar a sua lista de produtos (exemplo: 'C:\\Users\\nome\\arquivo.csv'):", "Adicionar Produto", JOptionPane.QUESTION_MESSAGE);
                                    }

                                    ps.addProduct(nameTXT.getText(), new BigDecimal(priceTXT.getText()), quantityTXT.getText(), categoryTXT.getText());

                                    JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!","Cadastro de produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                    break;
                                } else{
                                    throw new InputMismatchException("O valor referente à quantidade não pode ser negativo");
                                }
                            } else{
                                break;
                            }
                        } catch (ProductServiceException | InputMismatchException | NumberFormatException e){
                            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Cadastro de produto", JOptionPane.ERROR_MESSAGE);
                        }
                    } while(true);
                }
                case 1 -> { //Edit product
                    if (Product.productList.size() >= 1){
                        EDIT_PRODUCT:
                        do{
                            Object[] editOptions = {"Editar Dados Cadastrais do Produto", "Adicionar ao Estoque", "Remover do Estoque", "Cancelar"};

                            option = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Editar Produto", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, editOptions, editOptions[0]);

                            switch (option){
                                case 0 -> { //Editar Dados Cadastrais do Produto
                                    do {
                                        try{
                                            String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto à ser editado:\n\n"+
                                                    u.listProductBuilder(), "Editar Dados Cadastrais do Produto", JOptionPane.QUESTION_MESSAGE);

                                            if (code != null){
                                                int position = ps.verifyExistingProduct(code);

                                                if (position != -1){
                                                    JTextField barCodeTXT                    = new JTextField();                    barCodeTXT.setText(Product.productList.get(position).getBarCode().toString());
                                                    JFormattedTextField seriesTXT            = new JFormattedTextField(seriesMask); seriesTXT.setText(Product.productList.get(position).getSeries());
                                                    JTextField nameTXT                       = new JTextField();                    nameTXT.setText(Product.productList.get(position).getName());
                                                    JTextField descriptionTXT                = new JTextField();                    descriptionTXT.setText(Product.productList.get(position).getDescription());
                                                    JTextField categoryTXT                   = new JTextField();                    categoryTXT.setText(Product.productList.get(position).getCategory());
                                                    JTextField grossAmountTXT                = new JTextField();                    grossAmountTXT.setText(String.format("%.2f", Product.productList.get(position).getGrossAmount()));
                                                    JTextField taxesTXT                      = new JTextField();                    taxesTXT.setText(String.format("%.2f", Product.productList.get(position).getTaxes()));
                                                    JTextField priceTXT                      = new JTextField();                    priceTXT.setText(String.format("%.2f", Product.productList.get(position).getPrice()));
                                                    JFormattedTextField manufacturingDateTXT = new JFormattedTextField(dateMask);   manufacturingDateTXT.setText(Product.productList.get(position).getManufacturingDate());
                                                    JFormattedTextField expirationDateTXT    = new JFormattedTextField(dateMask);   expirationDateTXT.setText(Product.productList.get(position).getExpirationDate());
                                                    JTextField colorTXT                      = new JTextField();                    colorTXT.setText(Product.productList.get(position).getColor());
                                                    JTextField materialTXT                   = new JTextField();                    materialTXT.setText(Product.productList.get(position).getMaterial());
                                                    JTextField quantityTXT                   = new JTextField();                    quantityTXT.setText(String.valueOf(Product.productList.get(position).getQuantity()));

                                                    Object[] editProduct = {"Dados do produto "+Product.productList.get(position).getCode()+":\n\nCódigo de barras:", barCodeTXT, "Série:", seriesTXT, "Nome:", nameTXT, "Descrição:", descriptionTXT,
                                                            "Categoria:", categoryTXT, "Valor bruto:", grossAmountTXT, "Impostos (%):", taxesTXT, "Valor líquido:", priceTXT, "Data de fabricação:", manufacturingDateTXT, "Data de validade:", expirationDateTXT, "Quantidade:", quantityTXT};

                                                    do{
                                                        try{
                                                            option = JOptionPane.showConfirmDialog(null, editProduct, "Editar Dados Cadastrais do Produto", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                                                            if (option == JOptionPane.OK_OPTION){
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                                                                Date expirationDate = null, manufacturingDate = null;

                                                                if (!expirationDateTXT.getText().equals("  /  /    ")) {
                                                                    expirationDate = sdf.parse(expirationDateTXT.getText());
                                                                }
                                                                if (!manufacturingDateTXT.getText().equals("  /  /    ")) {
                                                                    manufacturingDate = sdf.parse(manufacturingDateTXT.getText());
                                                                }

                                                                ps.editProduct(Long.parseLong(barCodeTXT.getText()), seriesTXT.getText(), nameTXT.getText(), descriptionTXT.getText(), categoryTXT.getText(),
                                                                        BigDecimal.valueOf(Double.parseDouble(grossAmountTXT.getText().replace(',','.'))),BigDecimal.valueOf(Double.parseDouble(taxesTXT.getText().replace(',','.'))),
                                                                        BigDecimal.valueOf(Double.parseDouble(priceTXT.getText().replace(',','.'))), manufacturingDate, expirationDate, colorTXT.getText(), materialTXT.getText(),
                                                                        quantityTXT.getText(), position);

                                                                JOptionPane.showMessageDialog(null,"Edição efetuada com sucesso!","Editar Dados Cadastrais do Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                                                break EDIT_PRODUCT;
                                                            } else{
                                                                break;
                                                            }
                                                        } catch (Exception e){
                                                            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Editar Dados Cadastrais do Produto", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } while(true);
                                                } else{
                                                    if (code.equals("")){
                                                        throw new ProductServiceException("O campo deve ser preenchido");
                                                    }
                                                    throw new ProductServiceException("O código inserido não corresponde a nenhum produto");
                                                }
                                            } else{
                                                break;
                                            }
                                        } catch (ProductServiceException e){
                                            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Editar Dados Cadastrais do Produto", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } while(true);
                                }
                                case 1 -> { //Adicionar ao Estoque
                                    do {
                                        try {
                                            String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto cuja quantidade de estoque deseja aumentar:\n\n" +
                                                    u.listProductBuilder(), "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                            if (code != null){
                                                int position = ps.verifyExistingProduct(code);

                                                if (position != -1) {
                                                    String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser adicionada ao estoque:", "Adicionar ao Estoque", JOptionPane.QUESTION_MESSAGE);

                                                    if (quantity != null) {
                                                        if (quantity.equals("")){
                                                            throw new ProductServiceException("O campo deve ser preenchido");
                                                        } else if (Integer.parseInt(quantity) > 0) {
                                                            ps.addQuantity(position, Integer.parseInt(quantity));

                                                            JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Adicionar ao Estoque", JOptionPane.INFORMATION_MESSAGE, icon);
                                                            break EDIT_PRODUCT;
                                                        } else {
                                                            throw new InputMismatchException("O valor referente à quantidade deve ser maior que zero");
                                                        }
                                                    } else{
                                                        throw new InputMismatchException("O campo deve ser preenchido");
                                                    }
                                                } else {
                                                    if (code.equals("")){
                                                        throw new ProductServiceException("O campo deve ser preenchido");
                                                    }
                                                    throw new ProductServiceException("O código inserido não corresponde a nenhum produto");
                                                }
                                            } else{
                                                break;
                                            }
                                        } catch (ProductServiceException | InputMismatchException e) {
                                            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Adicionar ao Estoque", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } while (true);
                                }
                                case 2 -> { //Remover do Estoque
                                    do {
                                        try {
                                            String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto cuja quantidade de estoque deseja aumentar:\n\n" +
                                                    u.listProductBuilder(), "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);

                                            if (code != null){
                                                int position = ps.verifyExistingProduct(code);

                                                if (position != -1) {
                                                    String quantity = JOptionPane.showInputDialog(null, "Insira a quantidade a ser adicionada ao estoque:", "Remover do Estoque", JOptionPane.QUESTION_MESSAGE);

                                                    if (quantity != null) {
                                                        if (quantity.equals("")){
                                                            throw new ProductServiceException("O campo deve ser preenchido");
                                                        } else if (Integer.parseInt(quantity) > 0) {
                                                            ps.removeQuantity(position, Integer.parseInt(quantity));

                                                            JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Remover do Estoque", JOptionPane.INFORMATION_MESSAGE, icon);
                                                            break EDIT_PRODUCT;
                                                        } else {
                                                            throw new InputMismatchException("O valor referente à quantidade deve ser maior que zero");
                                                        }
                                                    } else{
                                                        throw new InputMismatchException("O campo deve ser preenchido");
                                                    }
                                                } else {
                                                    if (code.equals("")){
                                                        throw new ProductServiceException("O campo deve ser preenchido");
                                                    }
                                                    throw new ProductServiceException("O código inserido não corresponde a nenhum produto");
                                                }
                                            } else{
                                                break;
                                            }
                                        } catch (ProductServiceException | InputMismatchException e) {
                                            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Remover do Estoque", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } while (true);
                                }
                                case 3 -> { //Cancelar
                                    break EDIT_PRODUCT;
                                }
                            }
                        } while(true);
                    } else{
                        JOptionPane.showMessageDialog(null,"Não há produtos cadastrados","Editar Produto", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case 2 -> { //Delete product
                    if (Product.productList.size() >= 1){
                        do{
                            try{
                                String code = JOptionPane.showInputDialog(null, "Insira o código correspondente ao produto à ser excluído:\n\n"+
                                        u.listProductBuilder(), "Excluir Produto", JOptionPane.QUESTION_MESSAGE);

                                if (code != null){
                                    int position = ps.verifyExistingProduct(code);

                                    if (position != -1){
                                        option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "+Product.productList.get(position).getName()+"?", "Excluir Produto",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                        if (option == JOptionPane.YES_OPTION){
                                            if (ps.removeProduct(position)){
                                                JOptionPane.showMessageDialog(null,"Produto excluído!","Excluir Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                                                break;
                                            }
                                        }
                                    } else{
                                        if (code.equals("")){
                                            throw new ProductServiceException("O campo deve ser preenchido");
                                        }
                                        throw new ProductServiceException("O código inserido não corresponde a nenhum produto");
                                    }
                                } else{
                                    break;
                                }
                            } catch (ProductServiceException | NullPointerException e){
                                JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Excluir Produto", JOptionPane.ERROR_MESSAGE);
                            }
                        } while(true);
                    } else{
                        JOptionPane.showMessageDialog(null,"Não há produtos cadastrados","Excluir Produto", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case 3 -> { //Import product
                    do{
                        try{
                            path = JOptionPane.showInputDialog(null, "Insira o caminho para o arquivo a ser importado (exemplo: 'C:\\Users\\nome\\arquivo.csv'):", "Importar Produto", JOptionPane.QUESTION_MESSAGE);

                            if (path != null){
                                if (path.equals("")){
                                    throw new ProductServiceException("O campo deve ser preenchido");
                                }
                                ps.addProductByImport(path);

                                JOptionPane.showMessageDialog(null,"Produtos importados com sucesso!\nLista de novos produtos:\n\n"+u.listProductBuilder(),"Importar Produto", JOptionPane.INFORMATION_MESSAGE,icon);
                            }
                            break;
                        } catch (Exception e){
                            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Importar Produto", JOptionPane.ERROR_MESSAGE);
                        }
                    } while(true);
                }
                case 4 -> { //List Products
                    if (Product.productList.size() >= 1){
                        JOptionPane.showMessageDialog(null,"Produtos cadastrados:\n\n"+
                                u.listProductBuilder(),"Listar Produtos", JOptionPane.INFORMATION_MESSAGE);
                    } else{
                        JOptionPane.showMessageDialog(null,"Não há produtos cadastrados","Listar Produtos", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case 5 -> { //Exit
                    break APP;
                }
            }
        } while(true);
    }
}