package servidorOperacao;

import logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ServidorOp {

    private int porta;
    private Logger logger = new Logger(this.getClass().getName() + ".txt");

    public ServidorOp(int porta) {
        this.porta = porta;
    }


    /**
     * Função abstrata para uma operação
     * @param a primeiro numero
     * @param b segundo numero
     * @return resultado da operação
     */
    public abstract int op(int a, int b);

    public void executa() throws IOException {


        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta");
        logger.writeLog("Porta " + this.porta + " aberta");

        while (true) {
            // Aceita uma conexão de um cliente e inicia uma Thread para trabalhar com ele
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
            logger.writeLog("[INFO] Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

            TrataClienteOP tc = new TrataClienteOP(cliente, this);
            tc.start();

        }


    }


}
