package main;

import transformador.Transformador;
import model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Mercado {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Produto> produtos;
    private static Map<Produto, Integer> carrinho;

    public static void main(String[] args){
        produtos = new ArrayList<>();
        carrinho = new HashMap<>();
        menu();
    }

    private static void menu(){
        System.out.println("----------- BEM VINDO ----------");
        System.out.println("-    Digite 1 para Cadastrar   -");
        System.out.println("-    Digite 2 para Listar      -");
        System.out.println("-    Digite 3 para Comprar     -");
        System.out.println("-    Digite 4 para Carrinho    -");
        System.out.println("-    Digite 5 para excluir     -");
        System.out.println("-    Digite 0 para sair        -");

        int option = input.nextInt();

        switch (option){
            case 1: cadastrar();
                    break;
            case 2: lista();
                    break;
            case 3: comprar();
                    break;
            case 4: verCarrinho();
                    break;
            case 5: excluir();
                    break;
            case 0: System.out.println("Obrigada, volte sempre");
                    System.exit(0);
            default: System.out.println("Opção invalida");
                     menu();
                     break;
        }
    }

    private static void cadastrar(){
        System.out.println("Nome do produto: ");
        String nome = input.next();
        System.out.println("Preco do produto: ");
        Double preco = input.nextDouble();

        Produto produto = new Produto(nome, preco);
        produtos.add(produto);//esse produtos é o ArrayList
        System.out.println(produto.getNome() + " cadastrado com sucesso");
        menu();
    }

    private static void lista(){
        if(produtos.size() > 0){
            System.out.println("Lista de produtos \n");

            for(Produto p : produtos){
                System.out.println(p);
            }
        }
        else {
            System.out.println("Nenhum produto cadastrado");
        }
        menu();
    }

    private static void comprar() {
        if (produtos.size() > 0) {
            System.out.println("----- Produtos disponiveis-----");
            for (Produto p : produtos) {
                System.out.println(p + "\n");
            }
            System.out.println("Codigo do produto: \n");
            int id = Integer.parseInt(input.next());
            boolean isPresent = false;

            // Procura o produto com o ID fornecido pelo usuário
            for (Produto p : produtos) {
                if (p.getId() == id) {
                    isPresent = true;
                    int qtd = 0;
                    try {
                        qtd = carrinho.get(p);
                        carrinho.put(p, qtd + 1);// se já tem esse produto no carrinho ent adiciona mais 1
                    } catch (NullPointerException e) {
                        carrinho.put(p, 1); // se for o primeiro
                    }
                    System.out.println(p.getNome() + " adicionado ao carrinho ");
                    break; // Saia do loop assim que encontrar o produto
                }
            }

            if (isPresent) {
                System.out.println("Digite 1 caso deseje adicionar outro produto, 4 caso queira ver sua lista, ou 0 caso queira finalizar \n");
                int option = Integer.parseInt(input.next());
                if (option == 1) {
                    comprar();
                } else if (option == 4) {
                    verCarrinho();
                } else {
                    finalizar();
                }
            } else {
                System.out.println("Produto não encontrado");
                menu();
            }
        } else {
            System.out.println("Não existem produtos cadastrados");
            menu();
        }
    }

    private static void verCarrinho(){
        System.out.println("----Carrinho----");
        if(carrinho.size() > 0){
            for (Produto p :carrinho.keySet()){
                System.out.println("Produto: " + p + "\nQuantidade: " + carrinho.get(p));
            }
            System.out.println("Se deseja finalizar sua compra digite 0, ou se desejar excluir algum item digite 5");
        }else{
            System.out.println("carrinho esta vazio");
        }

        int option = Integer.parseInt(input.next());
        if(option == 0){
            finalizar();
        }
        if(option == 5){
            excluir();
        }
        else{
            menu();
        }
    }

    private static void excluir() {
        if (carrinho.size() > 0) {
            System.out.println("Digite o ID do produto que deseja excluir: ");
            int id = Integer.parseInt(input.next());
            boolean isPresent = false;

            for (Produto p : produtos) {
                if (p.getId() == id) {
                    isPresent = true;
                    if (carrinho.containsKey(p)) {
                        int qtd = carrinho.get(p);
                        if (qtd > 0) {
                            carrinho.put(p, qtd - 1);
                            System.out.println("Um item do produto " + p.getNome() + " foi removido do carrinho.");
                        }
                        if(isPresent){
                            System.out.println("Digite 5 caso deseje exluir outro produto, ou 0 caso queira finalizar \n");
                            int option = Integer.parseInt(input.next());
                            if (option == 5) {
                                excluir();
                            } else {
                                finalizar();
                            }
                        } else {
                            System.out.println("O produto " + p.getNome() + " não está no carrinho.");
                            menu();
                        }
                    } else {
                        System.out.println("O produto " + p.getNome() + " não está no carrinho.");
                        menu();
                    }
                    break; // Sai do loop assim que o produto é encontrado
                }
            }

            if (!isPresent) {
                System.out.println("Produto não encontrado.");
            }
        } else {
            System.out.println("Não há produtos no carrinho.");
        }
    }



    private static void finalizar() {
        Double valorTotal = 0.0;
        System.out.println("Seus produtos");

        for (Produto p : carrinho.keySet()) {
            int qtd = carrinho.get(p);
            valorTotal += p.getPreco() * qtd;
            System.out.println("Produto: " + p.getNome());
            System.out.println("Quantidade: " + qtd);
            System.out.println("Valor total do produto: " + Transformador.transform(p.getPreco() * qtd));
        }

        System.out.println("Valor total da compra: " + Transformador.transform(valorTotal));
        carrinho.clear(); // Limpa o carrinho após exibir os produtos e calcular o valor total
        System.out.println("Obrigado, volte sempre");
    }

}
