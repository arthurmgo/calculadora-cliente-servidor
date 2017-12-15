package ServidorOperacao;

import java.io.IOException;

public class ServidorSubtracao extends ServidorOp{

    public ServidorSubtracao(int porta) {
        super(porta);
    }

    public static void main(String[] args) throws IOException {
        new ServidorSoma(10002).executa();
    }

    @Override
    public int op(int a, int b) {
        return a - b;
    }

}
