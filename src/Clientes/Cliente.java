package Clientes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        new Cliente("127.0.0.1", 12345).executa();
    }

    private String host;
    private int porta;

    public Cliente (String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws  IOException {
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        r.start();


        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());


        while (teclado.hasNextLine()) {
            String s = teclado.nextLine();
            saida.println(s);

            if (s.equals("quit")){
                break;
            }
        }

        saida.close();
        teclado.close();
        cliente.close();
    }
}
