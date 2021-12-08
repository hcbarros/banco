package banco;

public class Banco {

    public static void main(String[] args) {

        Conta c1 = new ContaCorrente("Henrique", "02657226400", 2000, Agencia.SAO_JOSE_002, 1000);
        ContaPoupanca c2 = new ContaPoupanca("Henrique", "02657226400", 1500, Agencia.SAO_JOSE_002, 1000);

        System.out.println(c1.getConta());
        c1.transferir(c2, 1210);
        //System.out.println(c2.getConta());
    }
}
