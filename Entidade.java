public class Entidade {
    int vida;
    boolean vivo;
    int horiz;
    int verti;

    public Entidade(int horiz, int verti) {
        this.vivo = true;
        this.horiz = horiz;
        this.verti = verti;
    }

    protected void morrer() {
        this.vivo = false;  
    }
}
