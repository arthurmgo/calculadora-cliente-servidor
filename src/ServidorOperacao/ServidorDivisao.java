package ServidorOperacao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorDivisao {

    private int porta;

    private ServidorDivisao(int porta) {
        this.porta = porta;
    }

    private int divisao(int x, int y){
        return x/y;
    }

    private void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta");

        while (true) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            Scanner s = new Scanner(cliente.getInputStream());
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            int x = s.nextInt();
            int y = s.nextInt();
            int resp = divisao(x,y);
            ps.println(resp);


        }

    }


    public static void main(String[] args) throws IOException {
        new ServidorDivisao(10003).executa();
    }

}

