public enum Direcao {
    CIMA,
    BAIXO,
    ESQUERDA,
    DIREITA,
    TIRO;


public static Direcao randomica() {
    int dirOp = (int) (Math.random() * 4);
    switch (dirOp) {
        case 0: return CIMA;
        case 1: return BAIXO;
        case 2: return ESQUERDA;
        default: return DIREITA;
    }
}

}
