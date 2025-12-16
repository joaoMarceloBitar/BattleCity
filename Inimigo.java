public class Inimigo extends Personagem {
    int dano;

    public Inimigo(int horiz, int verti, Direcao ultimaDirecao){
        super(horiz, verti, ultimaDirecao, 'I');
        this.dano=1;
    }
}
