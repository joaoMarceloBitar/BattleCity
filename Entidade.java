public abstract class Entidade {
    private int vida;
    private char caractere;
    private boolean destrutivo;
    private boolean vivo = true;
    private int horiz;
    private int verti;

    public Entidade(int horiz, int verti, int vida, boolean destrutivo, char caractere) {
        this.destrutivo = destrutivo;
        this.caractere = caractere;
        this.horiz = horiz;
        this.verti = verti;
        this.vida = vida;
        this.vivo = true;
    }

    public boolean taVivo() {
        return vivo;
    }

    public void atingido() {
        vida--;
        if (vida <= 0) {
            morrer();
        }
    }

    protected void morrer() {
        vivo = false;
    }
    
    public int getHoriz() { return horiz; }
    public int getVerti() { return verti; }
    public void setHoriz(int h) { this.horiz = h; }
    public void setVerti(int v) { this. verti = v; }

    public char getCaractere() {
        return this.caractere;
    }

    public boolean podeAtravessar() {
        return false;
    }
}
