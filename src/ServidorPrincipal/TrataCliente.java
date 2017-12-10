package ServidorPrincipal;


import java.io.InputStream;
import java.util.Scanner;

public class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    private static Integer realizaOperacao(Integer n1, Integer n2, String op) throws
            ArithmeticException {
        switch (op) {
            case "+": // Em caso do operador ser um "+"
                return n1 + n2;
            case "-": // Em caso do operador ser um "-"
                return n1 - n2;
            case "*": // Em caso do operador ser um "*"
                return n1 * n2;
            case "/": // Em caso do operador ser um "/"
                if (n2 == 0) {
                    throw new ArithmeticException("O divisor nao pode ser 0 !"); //Caso o segundo numero seja 0 e o operador seja "/"

                }
                return n1 / n2;
            default:
                return null;

        }
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {
            servidor.distribuiMensagem(s.nextLine());
        }
        s.close();
    }
}