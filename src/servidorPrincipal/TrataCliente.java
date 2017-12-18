package servidorPrincipal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente extends Thread {

    private InputStream in;
    private OutputStream out;
    private Servidor servidor;
    private Socket cliente;
    private int id;


    public TrataCliente(Socket cliente, Servidor servidor) throws IOException {

        this.cliente = cliente;
        this.in = cliente.getInputStream();
        this.out = cliente.getOutputStream();
        this.servidor = servidor;
        this.id = servidor.getClientes();

    }


    public void run() {

        Scanner s = new Scanner(this.in);
        PrintStream ps = new PrintStream(this.out);


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
            }catch (ArithmeticException e){
                ps.println("Divisão por 0 não suportada");
                servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Divisão por 0");
            }






        }

        s.close();
        ps.close();

        try {
            cliente.close();
            servidor.getLogger().writeLog("[Thread " + this.id + " INFO] Conexão com o cliente " + this.id + " fechada");
        } catch (IOException e) {
            servidor.getLogger().writeLog("[Thread " + this.id + " ERROR] Erro ao fechar conexão com o cliente " + this.id );
        }


    }

}