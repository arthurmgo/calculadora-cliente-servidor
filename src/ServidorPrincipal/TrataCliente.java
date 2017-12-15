package ServidorPrincipal;


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


    public TrataCliente(Socket cliente, Servidor servidor) throws IOException {
        this.cliente = cliente;
        this.in = cliente.getInputStream();
        this.out = cliente.getOutputStream();
        this.servidor = servidor;
    }


    public void run() {

        Scanner s = new Scanner(this.in);
        PrintStream ps = new PrintStream(this.out);



        while (true){

            String line = s.nextLine();

            if (line.equals("quit")){
                break;
            }

            Expressao ex = new Expressao(line);
            Integer resp = servidor.realizaOperacao(ex.getX(), ex.getY(), ex.getOp());
            ps.println(resp);



        }

        s.close();
        ps.close();

        try {
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}