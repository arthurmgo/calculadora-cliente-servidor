package cliente;

import logger.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private String host;
    private int porta;
    private Logger logger;


    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
        this.logger = new Logger("ClienteLOG.txt");
    }

    public static void main(String[] args) throws IOException {
        new Cliente("127.0.0.1", 12345).executa();
    }

    public void executa() throws IOException {
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");


        logger.writeLog("[INFO] O cliente se conectou ao servidor");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        r.start();


        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());


        while (teclado.hasNextLine()) {
            String s = teclado.nextLine();
            logger.writeLog("[INFO] O cliente enviou a mensagem: " + s);
            saida.println(s);

            if (s.equals("quit")) {
                logger.writeLog("[INFO] O cliente desconectou-se");
                break;
            }
        }

        saida.close();
        teclado.close();
        cliente.close();
    }
}
