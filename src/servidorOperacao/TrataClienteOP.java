package servidorOperacao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataClienteOP extends Thread {

    private Socket cliente;
    private ServidorOp servidor;

    public TrataClienteOP(Socket cliente, ServidorOp servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    /**
     * Metodo chamado ao iniciar a Thread. Faz a leitura de dois numeros do cliente e envia ao cliente o resuldado da
     * operação entre estes numeros.
     */
    @Override
    public void run() {


        try {

            Scanner s = new Scanner(cliente.getInputStream());
            PrintStream ps = new PrintStream(cliente.getOutputStream());

            while (s.hasNextLine()) {

                int x = Integer.parseInt(s.nextLine());
                int y = Integer.parseInt(s.nextLine());
                int resp = servidor.op(x, y);

                ps.println(resp);


            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }


    }
}
