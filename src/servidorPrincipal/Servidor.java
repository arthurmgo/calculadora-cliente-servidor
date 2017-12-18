package servidorPrincipal;

import logger.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    private int porta;
    private int clientes;

    private Cache cache;
    private Logger logger;

    private Socket som;
    private Socket sub;
    private Socket mul;
    private Socket div;

    public Servidor (int porta) {

        this.porta = porta;
        this.clientes = 0;
        this.logger = new Logger("ServerLOG.txt");
        this.cache = new Cache(5);


        logger.writeLog("[INFO] Memoria cache do servidor de tamanho: 5");

        try {
            this.som = new Socket("127.0.0.1", 10001);
            logger.writeLog("[INFO] O servidor se conectou ao servidor de soma!");


            this.sub = new Socket("127.0.0.1", 10002);
            logger.writeLog("[INFO] O servidor se conectou ao servidor subtracao!");


            this.mul = new Socket("127.0.0.1", 10003);
            logger.writeLog("[INFO] O servidor se conectou ao servidor multiplicacao!");


            this.div = new Socket("127.0.0.1", 10004);
            logger.writeLog("[INFO] O servidor se conectou ao servidor divisao!");
        } catch (IOException e) {
            logger.writeLog("[ERROR] Falha ao se conectar a algum dos servidores de operacao");
        }

    }

    public static void main(String[] args) {
        new  Servidor(12345).executa();

    }

    public Logger getLogger() {
        return logger;
    }

    public int getClientes() {
        return clientes;
    }

    public Integer enviaMsg(Integer x, Integer y, Socket server){
        try {
            PrintStream ps = new PrintStream(server.getOutputStream());
            Scanner s = new Scanner(server.getInputStream());
            ps.println(x);
            ps.println(y);

            logger.writeLog("[INFO] O servidor enviou os numeros: " + x + " e " + y + " ao servidor de operacao");

            Integer resp = Integer.parseInt(s.nextLine());

            logger.writeLog("[INFO] O servidor de operacao retornou: " + resp);


            return resp;


        } catch (IOException e) {
            logger.writeLog("[ERROR] Erro ao enviar mensagem ao servidor de operacao");
        }

        return null;
    }

    public void executa () {


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

    public Integer realizaOperacao(Expressao expr) throws ArithmeticException {

        String op = expr.getOp();
        Integer n1 = expr.getX();
        Integer n2 = expr.getY();

        Integer resp = this.cache.get(expr);

        if(resp != null){
            logger.writeLog("[INFO] Resposta obtida atraves da memoria cache");
            return resp;
        }

        switch (op) {
            case "+":
                resp = enviaMsg(n1,n2,this.som);
                break;
            case "-":
                resp =  enviaMsg(n1,n2,this.sub);
                break;
            case "*":
                resp =  enviaMsg(n1,n2,this.mul);
                break;
            case "/":
                if (n2 == 0) {
                    throw new ArithmeticException("O divisor nao pode ser 0 !");
                }
                resp =  enviaMsg(n1,n2,this.div);
                break;
            default:
                logger.writeLog("[INFO] Operacao invalida");
                return null;

        }

        cache.put(expr,resp);
        logger.writeLog("[INFO] Cache atualizado");
        logger.writeLog("[INFO] Conteudo da cache: " + cache.keySet());
        return resp;
    }

}

