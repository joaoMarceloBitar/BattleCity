public class Vazio extends Entidade {
    public Vazio (int horiz, int verti) {
        super(horiz, verti, 1, false, '_');
    }

    @Override
    public boolean podeAtravessar() {
        return true;
    }

    @Override
    public void atingido() {
        
    }
    
}
