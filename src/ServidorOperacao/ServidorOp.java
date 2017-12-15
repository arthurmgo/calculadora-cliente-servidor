package ServidorOperacao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public abstract class ServidorOp {

    private int porta;

    public ServidorOp(int porta) {
        this.porta = porta;
    }

    public abstract int op(int a, int b);

    public void executa() throws IOException{

        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta");

        while (true) {

            Socket cliente = servidor.accept();
            Scanner s = new Scanner(cliente.getInputStream());
            PrintStream ps = new PrintStream(cliente.getOutputStream());

            int x = s.nextInt();
            int y = s.nextInt();

            int resp = op(x,y);

            ps.println(resp);


        }

    }


}
