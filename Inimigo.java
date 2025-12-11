public class Inimigo extends Personagem {
    int dano;

    public Inimigo(int horiz,int verti, Direcao ultimaDirecao){
        super(horiz, verti, ultimaDirecao);
        this.vida=1;
        this.dano=1;
    }

    public Direcao direcaoInimigo;
}
