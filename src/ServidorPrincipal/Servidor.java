package ServidorPrincipal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    private int porta;

    private Socket som;
    private Socket sub;
    private Socket mul;
    private Socket div;

    public Servidor (int porta) {
        this.porta = porta;
        try {
            this.som = new Socket("127.0.0.1", 10001);
            System.out.println("O cliente se conectou ao servidor soma!");
            this.sub = new Socket("127.0.0.1", 10002);
            System.out.println("O cliente se conectou ao servidor subt!");
            this.mul = new Socket("127.0.0.1", 10003);
            System.out.println("O cliente se conectou ao servidor mult!");
            this.div = new Socket("127.0.0.1", 10004);
            System.out.println("O cliente se conectou ao servidor divi!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setSom(Socket som) {
        this.som = som;
    }

    public void setSub(Socket sub) {
        this.sub = sub;
    }

    public void setMul(Socket mul) {
        this.mul = mul;
    }

    public void setDiv(Socket div) {
        this.div = div;
    }

    public Integer enviaMsg(Integer x, Integer y, Socket server){
        try {
            PrintStream ps = new PrintStream(server.getOutputStream());
            Scanner s = new Scanner(server.getInputStream());
            ps.println(x);
            ps.println(y);

            return Integer.parseInt(s.nextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void executa () {

        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(this.porta);
            System.out.println("Porta 12345 aberta!");

            while (true) {

                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());


                TrataCliente tc = new TrataCliente(cliente, this);
                tc.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public Integer realizaOperacao(Integer n1, Integer n2, String op) throws ArithmeticException {

        switch (op) {

            case "+":
                return enviaMsg(n1,n2,this.som);

            case "-":
                return enviaMsg(n1,n2,this.sub);

            case "*":
                return enviaMsg(n1,n2,this.mul);

            case "/":

                if (n2 == 0) {
                    throw new ArithmeticException("O divisor nao pode ser 0 !");
                }

                return enviaMsg(n1,n2,this.div);

            default:
                return null;

        }
    }

    public static void main(String[] args) {
        new  Servidor(12345).executa();

    }

}

