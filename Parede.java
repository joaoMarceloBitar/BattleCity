public abstract class Parede extends Entidade {
    public Parede (int horiz, int verti, int vida, char caractere) {
        super(horiz, verti, vida, false, caractere);
    }
    
    @Override
    public boolean podeAtravessar() {
        return false;
    }
}
