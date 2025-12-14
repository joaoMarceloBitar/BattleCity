import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
    private static final Scanner scan = new Scanner(System.in);
    private List<Entidade> entidades = new ArrayList<>();
    private Mapa mapa;

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        int opcao;

        do {
            opcao = menu();

            switch (opcao) {
                case 1:
                    jogo.iniciar();
                    break;
                case 2:
                    jogo.desenhar();
                    break;
                case 3:
                    System.exit(0);
            }
        } while (opcao >= 1 && opcao <= 3);
    }

    public void iniciar() {
        int min = 1;
        int max = 3;

        int numMapa = min + (int) (Math.random() * ((max - min) + 1));
        entidades.clear();

        mapa = new Mapa("mapa" + numMapa + ".txt");
        Jogador player = geraJogador();
        Inimigo inimigo1 = geraInimigo();
        Inimigo inimigo2 = geraInimigo();
        Base base = new Base(7, 13, true);

        entidades.add(player);
        entidades.add(inimigo1);
        entidades.add(inimigo2);
        entidades.add(base);

        gameLoop(player, inimigo1, inimigo2, base, mapa);
    }

    private void desenhar() {
        if (mapa == null) {
            System.out.println("Nenhum mapa carregado. Inicie o jogo primeiro.");
            return;
        }
        mapa.imprimeMapa();
    }

    public static int menu() {
        int opcao;

        System.out.println("======= MENU =======");
        System.out.println("1 - Jogar");
        System.out.println("2 - Carregar Mapa");
        System.out.println("3 - Sair");

        opcao = scan.nextInt();
        return opcao;
    }

    public void gameLoop(Jogador player, Inimigo inimigo1, Inimigo inimigo2, Entidade base, Mapa mapa) {
        geraBlocos(mapa);

        while (base.vivo) {

            mapa.resetarMapa();

            for (int j = 0; j < 13; j++) {
                for (int i = 0; i < 13; i++) {
                    if (i == player.verti && j == player.horiz) {
                        mapa.grid[i][j] = 'P';
                    } else if ((i == inimigo1.verti && j == inimigo1.horiz) ||
                            (i == inimigo2.verti && j == inimigo2.horiz)) {
                        mapa.grid[i][j] = 'I';
                    }
                }
            }

            mapa.imprimeMapa();

            System.out.println("---CONTROLES---");
            System.out.println("| W: cima     |\n| A: esquerda |\n| S: baixo    |\n| D: direita  |");
            System.out.println("| Q: atirar   |");
            System.out.println("---------------");

            Direcao comando = lerEntrada();
            acaoPlayer(comando, player, inimigo1, inimigo2);

        }
    }

    public void acaoPlayer(Direcao comando, Jogador player, Inimigo inimigo1, Inimigo inimigo2) {
        if (comando == Direcao.TIRO) {
            // dar tiro
        } else {

            player.andar(comando);
        }

    }

    public Direcao lerEntrada() {
        char c;
        Direcao comando = null;

        String entrada = scan.next();

        c = entrada.charAt(0);
        c = Character.toUpperCase(c);

        switch (c) {
            case 'W':
                comando = Direcao.CIMA;
                break;
            case 'S':
                comando = Direcao.BAIXO;
                break;
            case 'A':
                comando = Direcao.ESQUERDA;
                break;
            case 'D':
                comando = Direcao.DIREITA;
                break;
            case 'Q':
                comando = Direcao.TIRO;
                break;
            case 'X':
                System.exit(0);
                break;
            default:
                System.out.println("entrada inválida\n");
                return lerEntrada();

        }
        return comando;
    }

    public Jogador geraJogador() {
        int xPlayer = (int) (10 * (Math.random()));
        Jogador player = new Jogador(xPlayer, 11, Direcao.CIMA);

        return player;
    }

    public Inimigo geraInimigo() {
        int xInimigo = (int) (10 * (Math.random()));
        Inimigo inimigo = new Inimigo(xInimigo, 2, Direcao.BAIXO);

        return inimigo;
    }

    public void geraBlocos(Mapa mapa) {
        for (int j = 0; j < 13; j++) {
            for (int i = 0; i < 13; i++) {
                if (mapa.grid[i][j] == '#') {
                    entidades.add(new BlocoAco(i, j));
                } else if (mapa.grid[i][j] == '%') {
                    entidades.add(new BlocoTijolo(i, j));
                }
            }
        }

    }
}
