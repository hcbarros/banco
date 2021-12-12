package banco;

import java.time.LocalDate;
import java.time.Period;

public class ContaCorrente extends Conta {

    private double limite;
    private static LocalDate data;
    //private final int LIMITE_DIAS_NEGATIVO = 10;

    public ContaCorrente(String nome, double rendaMensal, Agencia agencia) {
        super(nome, rendaMensal, agencia);
        limite = (rendaMensal > 0) ? (rendaMensal/10) : 0;
    }

//    @Override
//    public double saque(double valor) {
//
//        if(data == null) {
//            double result = super.saque(valor);
//            if(result < 0) data = LocalDate.now();
//            return result;
//        }
//        else {
//            int dias = Period.between(data, LocalDate.now()).getDays();
//            if(dias > LIMITE_DIAS_NEGATIVO) {
//                System.out.println("Você não pode sacar ou transferir! Ultrapassou o limite de tempo negativado.");
//                return 0;
//            }
//            else {
//                return super.saque(valor);
//            }
//        }
//    }
//
//    @Override
//    public void deposito(double valor) {
//        super.deposito(valor);
//        if(getSaldo() >= 0) data = null;
//    }

    @Override
    public void alterarDadosCadastrais(String nome, double rendaMensal) {
        limite = (rendaMensal > 0) ? (rendaMensal/10) : 0;
        super.alterarDadosCadastrais(nome, rendaMensal);
    }

    public double getLimite() {
        return limite;
    }

    public String toString() {
        return super.toString() +
                "\nLimite (cheque especial): " + limite;
    }

}
