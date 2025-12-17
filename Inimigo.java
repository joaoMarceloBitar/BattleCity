public class Inimigo extends Personagem implements Atingivel {
    int dano;

    public Inimigo(int horiz,int verti, Direcao ultimaDirecao){
        super(horiz, verti, ultimaDirecao);
        this.vida=1;
        this.dano=1;
    }

    public Direcao direcaoInimigo;

    @Override
    public char getChar() {
        return 'I';
    }
    public char getCharAtingido() {
        return '_';
    }
    
}
