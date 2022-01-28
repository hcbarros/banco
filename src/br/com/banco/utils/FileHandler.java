package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {



    public static void gravarConta(Conta c) {
        try {
            new File("contas-gravadas").mkdir();
            FileOutputStream arquivo = new FileOutputStream(
                    "contas-gravadas/"+c.getCpf()+c.getConta()+".txt",false);
            PrintStream gravador = new PrintStream(arquivo);
            gravador.println (c);
            gravador.close();
            arquivo.close();
        }
        catch (IOException ioe) {
            System.out.println("Erro na gravacao de dados");
        }
    }

    public static void gravarTransacoes(Conta c, String transacao) {
        try {
            new File("transacoes-gravadas").mkdir();
            FileOutputStream arquivo = new FileOutputStream(
                    "transacoes-gravadas/"+c.getCpf()+c.getConta()+".txt",true);
            PrintStream gravador = new PrintStream(arquivo);
            gravador.println (transacao);
            gravador.close();
            arquivo.close();
        }
        catch (IOException ioe) {
            System.out.println("Erro na gravacao de dados");
        }
    }

    public static void lerDados(Conta c, boolean transacoes) {
        try {
            String diretorio = transacoes ? "transacoes-gravadas/" : "contas-gravadas/";
//            BufferedReader br = new BufferedReader(new FileReader(
//                    diretorio + c.getCpf()+c.getConta()+".txt"));
//            List<String> linhas = br.lines().collect(Collectors.toCollection(ArrayList<String>::new));
//            linhas.forEach(System.out::println);

            Files.readAllLines(Paths.get(diretorio + c.getCpf()+c.getConta()+".txt"))
                    .forEach(System.out::println);

            //br.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
