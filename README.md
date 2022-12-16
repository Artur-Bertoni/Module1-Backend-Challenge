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

## Requisitos e Funcionalidades Esperadas
- Construir uma aplicação Java Standalone que execute por linha de comando.
- As funcionalidades deveráo ter cobertura de testes unitários.
- Ao iniciar a aplicação, exibir um menu numerado com as seguintes opções:
- 1. Adicionar Novo Produto: Permitir incluir um novo item na lista de produtos da loja. Ao selecionar essa opção solicitar os seguintes dados para o usuário: nome, preço, quantidade em estoque e categoria.
- 2. Editar Produto: Permitir editar os dados de um produto. Ao selecionar essa opção, deverá listar todos os produtos cadastrados e o usuário poderá selecionar um para edição dos dados previamente cadastrados.
- 3. Excluir Produto: Listar todos os produtos cadastrados e permitir selecionar um para apagar. Deverá sempre haver pelo menos um produto na lista.
- 4. Importar Mostruário da Fábrica: Muitas vezes, lojas virtuais recebem das fábricas arquivos com os dados dos produtos. Ao selecionar essa opção, a aplicação deverá ser um arquivo .csv (ver exemplo no repositório) que deve ser indicado pelo usuário, tratar os dados e gravar na lista de produtos.
- Cálculo do valor final do produto: valor bruto + imposto + margem de lucro de 45%
- 5. Sair: opção para o usuário encerrar o sistema.
- Qualquer manipulação dos dados deverá ser armazenado no arquivo CSV.
- Após o usuário efetuar alguma das operações acima (das opções de 1-4), a aplicação deverá voltar para o menu inicial.
- Cada opção do menu, deve ter uma opção de "Cancelar" a operação e voltar para o menu inicial, sem promover alteração dos dados.
- Não pode ser usado Lombok.
- Criar um README.md contendo as orientações para rodar a aplicação e suas dependências pela linha de comando.
