package Jogo;

public class BlocoTijolo extends Entidade implements Atingivel {

    public BlocoTijolo(int horiz, int verti) {
        super(horiz, verti,true);
    }

    @Override
    public char getChar() {
        return '%';
    }
    public char getCharAtingido() {
        return '_';
    }
}
