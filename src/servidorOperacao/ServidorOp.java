package servidorOperacao;

import logger.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public abstract class ServidorOp {

    private int porta;
    private Logger logger = new Logger( this.getClass().getName() + ".txt");

    public ServidorOp(int porta) {
        this.porta = porta;
    }

    public abstract int op(int a, int b);

    public void executa() throws IOException{


        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta");
        logger.writeLog("Porta " + this.porta + " aberta");

        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
            logger.writeLog("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

            TrataClienteOP tc = new TrataClienteOP(cliente,this);
            tc.start();

        }



    }


}
