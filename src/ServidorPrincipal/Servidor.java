package ServidorPrincipal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) throws IOException {
        new Servidor(12345).executa();
    }

    private int porta;


    public Servidor (int porta) {
        this.porta = porta;
    }

    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");

        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());


            // cria tratador de cliente numa nova thread
            TrataCliente tc = new TrataCliente(cliente, this);
            tc.start();
        }

    }


    public Integer realizaOperacao(Integer n1, Integer n2, String op) throws
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

}

