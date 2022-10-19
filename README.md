# Desafio Backend Modulo 1
Desafio Módulo 1 da trilha Impulsionar 2.0 - Backend

## Contexto
O contexto apresentado foi o de criar um aplicativo de uma loja simples, com as funções de adicionar, editar, excluir e importar (através de arquivos .csv) produtos

## Orientações para o uso
- As funcionalidades básicas de adicionar, editar e excluir são bem intuitívas. Para o funcionamento adequado dessas funções basta seguir as orientações na interface e preencher os campos de perguntas com valores válidos (campos numéricos com números por exemplo)

- Caso a primeira ação realizada pelo usuário não seja uma importação de uma lista existente, o programa irá requisitar a inserção do caminho de um arquivo onde as alterações serão realizadas, caso não haja um arquivo criado, basta inserir o caminho para a pasta e ao final escrever o nome do arquivo que sedeja criar, como no exemplo: C:\Users\nome\pasta\arquivo.csv

- Quanto a função de importação de uma lista existente, através de um arquivo .csv, um campo de inserção de texto será exibido na tela, esse campo deve ser preenchido com o caminho do arquivo à ser importado (Ex: C:\Users\nome\pasta\arquivo.csv). Após a primeira importação o caminho ficará salvo, e qualquer adição, edição ou remoção de produtos será gravada no arquivo fornecido pela importação

## Dependências utilizadas
- OpenCSV (com.opencsv) -> adicionada através do arquivo "pom.xml", responsável pela leitura de um arquivo .csv ao importar uma lista
- JUnit (junit) -> adicionada através do arquivo "pom.xml", responsável pelos testes unitários das classes do projeto
- Ícone Check (checkmark.png) -> adicionada na pasta "images", utilizada em diversos momentos no código com o objetivo de dar um feedback positivo ao usuário ao completar uma função

chackmark.png: 
![This is an image](images/checkmark.png)
