package servidorPrincipal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente extends Thread {


    private Servidor servidor;
    private Socket cliente;
    private int id;

    private Socket som;
    private Socket sub;
    private Socket mul;
    private Socket div;


    public TrataCliente(Socket cliente, Servidor servidor) {

        this.cliente = cliente;
        this.servidor = servidor;
        this.id = servidor.getClientes();

        try {


            this.som = new Socket(servidor.getServer(0).getIp(), servidor.getServer(0).getPorta());
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor se conectou ao servidor de soma!");

            this.sub = new Socket(servidor.getServer(1).getIp(), servidor.getServer(1).getPorta());
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor se conectou ao servidor subtracao!");

            this.mul = new Socket(servidor.getServer(2).getIp(), servidor.getServer(2).getPorta());
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor se conectou ao servidor multiplicacao!");

            this.div = new Socket(servidor.getServer(3).getIp(), servidor.getServer(3).getPorta());
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor se conectou ao servidor divisao!");


        } catch (IOException e) {
            servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Falha ao se conectar a algum dos servidores de operacao");
        }

    }

    public Integer enviaMsg(Integer x, Integer y, Socket server) {
        try {

            PrintStream ps = new PrintStream(server.getOutputStream());
            Scanner s = new Scanner(server.getInputStream());

            ps.println(x);
            ps.println(y);
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor enviou os numeros: " + x + " e " + y + " ao servidor de operacao");

            Integer resp = Integer.parseInt(s.nextLine());
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] O servidor de operacao retornou: " + resp);

            return resp;

        } catch (IOException e) {
            servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Erro ao enviar mensagem ao servidor de operacao");
        }

        return null;
    }

    public Integer realizaOperacao(Expressao expr) throws ArithmeticException {

        String op = expr.getOp();
        Integer n1 = expr.getX();
        Integer n2 = expr.getY();

        Integer resp = servidor.getCache().get(expr);

        if (resp != null) {
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Resposta obtida atraves da memoria cache");
            return resp;
        }

        switch (op) {
            case "+":
                resp = enviaMsg(n1, n2, this.som);
                break;
            case "-":
                resp = enviaMsg(n1, n2, this.sub);
                break;
            case "*":
                resp = enviaMsg(n1, n2, this.mul);
                break;
            case "/":
                if (n2 == 0) {
                    throw new ArithmeticException("O divisor nao pode ser 0 !");
                }
                resp = enviaMsg(n1, n2, this.div);
                break;
            default:
                servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Operacao invalida");
                return null;

        }

        servidor.getCache().put(expr, resp);
        servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Cache atualizado");
        servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Conteudo da cache: " + servidor.getCache().keySet());
        return resp;
    }


    public void run() {


        try {

            Scanner s = new Scanner(this.cliente.getInputStream());
            PrintStream ps = new PrintStream(this.cliente.getOutputStream());


            while (s.hasNextLine()) {

                String line = s.nextLine();
                servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Mensagem recebida do cliente: " + line);

                if (line.equals("quit")) {
                    break;
                }


                try {
                    Expressao ex = new Expressao(line);
                    Integer resp = realizaOperacao(ex);
                    ps.println(resp);
                    servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Mensagem enviada ao cliente: " + resp);
                } catch (ArithmeticException e) {
                    ps.println("Divisão por 0 não suportada");
                    servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Divisão por 0");
                } catch (NumberFormatException e) {
                    ps.println("Expressão com formato invalido");
                    servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Expressão com formato invalido (parametros)");
                } catch (NullPointerException e) {
                    ps.println("Expressão invalida");
                    servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Expressão com formato invalido (operação)");

                }


            }
            s.close();
            ps.close();

        } catch (IOException e) {
            servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Erro ao obter stream de comunicação com o cliente " + this.id);
        }


        try {
            cliente.close();
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Conexão com o cliente " + this.id + " fechada");
        } catch (IOException e) {
            servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Erro ao fechar conexão com o cliente " + this.id);
        }


    }

}
