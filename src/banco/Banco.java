package banco;

import java.util.List;

public class Banco {

    public static void main(String[] args) {

        Conta c1 = new ContaCorrente("Henrique", "02657226400",
                2000, Agencia.SAO_JOSE_002, 1000)
                .validarCPF("02657226400");
        ContaPoupanca c2 = new ContaPoupanca("Henrique", "02657226400", 1500, Agencia.SAO_JOSE_002, 1000);
        c1.saque(500);
        c1.extrato();
        //c1.transferir(c2, 1210);
        //System.out.println(c2.getConta());
    }
}
