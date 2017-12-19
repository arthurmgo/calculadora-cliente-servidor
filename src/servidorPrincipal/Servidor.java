package servidorPrincipal;

import logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {


    private int porta;
    private int clientes;

    private Cache cache;
    private Logger logger;

    private Endereco serverList[];


    public Servidor(int porta, Endereco[] serverList, int maxCache) {

        this.porta = porta;
        this.serverList = serverList;
        this.clientes = 0;
        this.logger = new Logger("ServerLOG.txt");
        this.cache = new Cache(maxCache);

        logger.writeLog("[INFO] Memoria cache do servidor de tamanho: " + maxCache);

    }


    public Logger getLogger() {
        return logger;
    }

    public Cache getCache() {
        return cache;
    }

    public int getClientes() {
        return clientes;
    }

    public Endereco getServer(int id) {
        return serverList[id];
    }


    public void executa() {


        try {
            ServerSocket servidor = new ServerSocket(this.porta);
            System.out.println("Porta 12345 aberta!");

            logger.writeLog("[INFO] Porta " + this.porta + " aberta");

            while (true) {

                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
                logger.writeLog("[INFO] Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                TrataCliente tc = new TrataCliente(cliente, this);
                tc.start();
                this.clientes++;
            }

        } catch (IOException e) {
            logger.writeLog("[ERROR] Erro ao criar o servidor");
        }

    }

    public static void main(String[] args) {

        Endereco serverlist[] = new Endereco[4];

        serverlist[0] = new Endereco("127.0.0.1", 10001);
        serverlist[1] = new Endereco("127.0.0.1", 10002);
        serverlist[2] = new Endereco("127.0.0.1", 10003);
        serverlist[3] = new Endereco("127.0.0.1", 10004);


        new Servidor(12345, serverlist, 5).executa();

    }


}

