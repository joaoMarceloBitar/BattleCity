public class Base extends Entidade{

    public Base(int horiz, int verti, boolean destrutivo) {
        super(horiz, verti, destrutivo);
    }

    @Override
    public char getChar() {
        throw new UnsupportedOperationException("Unimplemented method 'getChar'");
    }
    
}
