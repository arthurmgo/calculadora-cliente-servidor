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



    public TrataCliente(Socket cliente, Servidor servidor) throws IOException {
        this.in = cliente.getInputStream();
        this.out = cliente.getOutputStream();
        this.servidor = servidor;
    }



    public void run() {

        Scanner s = new Scanner(this.in);
        PrintStream ps = new PrintStream(this.out);

        int x = Integer.parseInt(s.nextLine());
        int y = Integer.parseInt(s.nextLine());
        String op = s.nextLine();

        Integer resp = servidor.realizaOperacao(x,y,op);
        ps.println(resp);

        s.close();
    }
}