package banco;

public class ContaInvestimento extends Conta {

    private final double CDB_PERCENTUAL_ANUAL = 0.1;
    private final double TESOURO_PERCENTUAL_ANUAL = 0.11;
    private final double LCA_PERCENTUAL_ANUAL = 0.12;
    private final double LCI_PERCENTUAL_ANUAL = 0.13;

    public ContaInvestimento(String nome, double rendaMensal, Agencia agencia) {
        super(nome, rendaMensal, agencia);
    }

    private void imprimirDados(double percentual) {
        System.out.printf("\n\nSeu saldo atual é de R$ %.2f", getSaldo());
        System.out.printf("\nApós 01 ano, aplicando o seu saldo você terá um lucro de R$ %.2f",
                (getSaldo() * percentual));
        System.out.printf("\nComo resultado você terá um saldo final de R$ %.2f %n%n",
                ((getSaldo() * percentual) + getSaldo()));
    }

    public void cdb() {
        imprimirDados(CDB_PERCENTUAL_ANUAL);
    }

    public void lca() {
        imprimirDados(LCA_PERCENTUAL_ANUAL);
    }

    public void lci() {
        imprimirDados(LCI_PERCENTUAL_ANUAL);
    }

    public void tesouroDireto() {
        imprimirDados(TESOURO_PERCENTUAL_ANUAL);
    }

}
