public abstract class Entidade {
    int vida;
    boolean destrutivo;
    boolean vivo;
    int horiz;
    int verti;

    public Entidade(int horiz, int verti, boolean destrutivo) {
        this.destrutivo = destrutivo;
        this.horiz = horiz;
        this.verti = verti;
        this.vivo = true;
    }


    
    public int getX() { return horiz; }
    public int getY() { return verti; }

    public abstract char getChar();
}
