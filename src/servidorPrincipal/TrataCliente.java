package servidorPrincipal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente extends Thread {


    private Servidor servidor;
    private Socket cliente;
    private int id;


    public TrataCliente(Socket cliente, Servidor servidor) {

        this.cliente = cliente;
        this.servidor = servidor;
        this.id = servidor.getClientes();

    }


    public void run() {


        try {

            Scanner s = new Scanner(this.cliente.getInputStream());
            PrintStream ps = new PrintStream(this.cliente.getOutputStream());


            while (true) {

                String line = s.nextLine();
                servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Mensagem recebida do cliente: " + line);

                if (line.equals("quit")) {

                    break;
                }

                Expressao ex = new Expressao(line);

                
                try {
                    Integer resp = servidor.realizaOperacao(ex);
                    ps.println(resp);
                    servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Mensagem enviada ao cliente: " + resp);
                } catch (ArithmeticException e) {
                    ps.println("Divisão por 0 não suportada");
                    servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Divisão por 0");
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