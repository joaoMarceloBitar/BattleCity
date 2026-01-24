public class Vazio extends Entidade{
    public Vazio(int horiz, int verti) {
        super(horiz, verti, false);
    }

    @Override
    public char getChar() {
        return '_';
    }
    
}
