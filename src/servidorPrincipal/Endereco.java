package servidorPrincipal;

public class Endereco {

    private String ip;
    private int porta;

    public Endereco(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}
