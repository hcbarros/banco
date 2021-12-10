package banco;

public class ContaPoupanca extends Conta {


    public ContaPoupanca(String nome, double rendaMensal, Agencia agencia) {
        super(nome, rendaMensal, agencia);
    }

    public void rendimento(int tempoMeses, double percentAnual){

        double percentMes = percentAnual / (12*100);

        double total = getSaldo();
        for(int i = 0; i < tempoMeses; i++) {
            total = total + total*percentMes;
        }
        System.out.printf("\nSeu saldo atual é de R$ %.2f", getSaldo());
        System.out.printf("\nApós %d meses você terá um lucro de R$ %.2f", tempoMeses, total - getSaldo());
        System.out.printf("\nVocê terá um saldo final de R$ %.2f %n%n", total);
    }

}
