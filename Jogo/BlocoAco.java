package Jogo;

public class BlocoAco extends Entidade{

    public BlocoAco(int horiz, int verti) {
        super(horiz, verti,false);
    }

    @Override
    public char getChar() {
        return '#';
    }
    public char getCharAtingido() {
        return '_';
    }
    
}
