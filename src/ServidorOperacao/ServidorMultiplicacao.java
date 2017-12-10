package ServidorOperacao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorMultiplicacao {

    private int porta;

    public ServidorMultiplicacao(int porta) {
        this.porta = porta;
    }

    public int multiplicacao(int x, int y){
        return x * y;
    }

    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta");

        while (true) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            Scanner s = new Scanner(cliente.getInputStream());
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            int x = s.nextInt();
            int y = s.nextInt();
            int resp = multiplicacao(x,y);
            ps.println(resp);


        }

    }


    public static void main(String[] args) throws IOException {
        new ServidorMultiplicacao(10003).executa();
    }

}
