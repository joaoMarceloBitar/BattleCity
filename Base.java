public class Base extends Entidade implements Atingivel {

    public Base(int horiz, int verti, boolean destrutivo) {
        super(horiz, verti, destrutivo);
    }

    @Override
    public char getChar() {
        return 'B';
    }

    @Override
    public char getCharAtingido() {
        return 'X';
    }
    
}
