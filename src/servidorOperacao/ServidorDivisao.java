package servidorOperacao;

import java.io.IOException;

public class ServidorDivisao extends ServidorOp {

    public ServidorDivisao(int porta) {
        super(porta);
    }

    public static void main(String[] args) throws IOException {
        new ServidorDivisao(10004).executa();
    }

    @Override
    public int op(int a, int b) {
        return a/b;
    }
}

