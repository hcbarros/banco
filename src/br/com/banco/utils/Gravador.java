package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Gravador {



    public static void gravarConta(Conta c) {
        try {
            new File("contas-gravadas").mkdir();
            FileOutputStream arquivo = new FileOutputStream(
                    "contas-gravadas/"+c.getCpf()+c.getConta()+".txt",false);
            PrintStream gravador = new PrintStream(arquivo);
            gravador.println ("\r\n"+c);
            gravador.close();
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
            gravador.println ("\r\n"+transacao);
            gravador.close();
        }
        catch (IOException ioe) {
            System.out.println("Erro na gravacao de dados");
        }
    }

    public static void carregarArquivo(Conta c, boolean trasacoes) {
        try {
            String diretorio = trasacoes ? "transacoes-gravadas/" : "contas-gravadas/";
            java.awt.Desktop.getDesktop().open(
                    new File(diretorio + c.getCpf()+c.getConta()+".txt"));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
