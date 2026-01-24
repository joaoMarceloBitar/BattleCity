import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
    private static final Scanner scan = new Scanner(System.in);
    private List<Entidade> entidades = new ArrayList<>();
    private List<Disparo> disparos = new ArrayList<>();
    private Mapa mapa;
    private int pontos = 0;

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

        if (mapa == null) {
            int numMapa = min + (int) (Math.random() * ((max - min) + 1));
            entidades.clear();

            mapa = new Mapa("mapa" + numMapa + ".txt");
        }

        gameLoop(mapa);
    }

    private void desenhar() {
        if (mapa == null) {

            int opcao;

            System.out.println("Selecione o numero do mapa: \n");
            System.out.println(" 1 | mapa 1");
            System.out.println(" 2 | container bar");
            System.out.println(" 3 | praca pedro osorio");
 
            opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    System.out.println("Carregando mapa 1...");
                    break;
                case 2:
                    System.out.println("Carregando container bar...");
                    break;
                case 3:
                    System.out.println("Carregando praca pedro osorio...");
                    break;

                default:
                    System.out.println("Opção inválida, Carregando mapa 1 por padrão.");
                    opcao = 1;
            }
            mapa = new Mapa("mapa" + opcao + ".txt");
            return;
        } else {
            mapa.imprimeMapa();
        }
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

    public void gameLoop(Mapa mapa) {
        // geraBlocos(mapa);

        Jogador player = geraJogador();
        Inimigo inimigo1 = geraInimigo();
        Inimigo inimigo2 = geraInimigo();

        entidades.add(player);
        entidades.add(inimigo1);
        entidades.add(inimigo2);
        
        while (true) {
            mapa.renderizaMapa();
            posicionaEntidades();
            percorreMapaEntidades();

            moveDisparos();
            verificaVitoria(entidades, player);
            
            System.out.println("---CONTROLES---");
            System.out.println("| W: cima     |\n| A: esquerda |\n| S: baixo    |\n| D: direita  |");
            System.out.println("| Q: atirar   |");
            System.out.println("---------------");
            System.out.println("Pontos: " + pontos);

            
            Direcao comando = lerEntrada();
            acaoPlayer(comando, player, inimigo1, inimigo2);
            verificaEntidades(player);
            
            Direcao comandoInimigo1 = Direcao.randomica();
            acaoInimigo(comandoInimigo1, inimigo1, player);
            
            Direcao comandoInimigo2 = Direcao.randomica();
            acaoInimigo(comandoInimigo2, inimigo2, player);
            verificaEntidades(player);
            /*
             * System.out.println("\n--- LOG ENTIDADES ---");
             * 
             * 
             * for (Entidade e : entidades) {
             * String tipo = e.getClass().getSimpleName();
             * System.out.printf("%s - X: %d Y: %d - %s\n", tipo, e.horiz, e.verti, e.vivo ?
             * "VIVO" : "MORTO");
             * }
             * for (Disparo d : disparos) {
             * System.out.printf("Disparo - X: %d Y: %d\n", d.horiz, d.verti);
             * }
             * System.out.println("---------------------\n");
             * mapa.resetarMapa();
             * 
             * for (int j = 0; j < 13; j++) {
             * for (int i = 0; i < 13; i++) {
             * for (int k = 0; k < entidades.size(); k++) {
             * Entidade e = entidades.get(k);
             * if (e.vivo && i == e.verti && j == e.horiz) {
             * mapa.grid[i][j] = e.getChar();
             * } else if (!e.vivo && i == e.verti && j == e.horiz && e instanceof Atingivel)
             * {
             * mapa.grid[i][j] = ((Atingivel) e).getCharAtingido();
             * }
             * }
             * for (int k = 0; k < disparos.size(); k++) {
             * Disparo tiro = disparos.get(k);
             * if (i == tiro.verti && j == tiro.horiz) {
             * mapa.grid[i][j] = 'O';
             * }
             * }
             * if (i == player.verti && j == player.horiz) {
             * mapa.grid[i][j] = player.getChar();
             * }
             * }
             * }
             * 
             * mapa.imprimeMapa();
             * 
             * moveDisparos();
             * 
             * verificaVitoria(entidades, player);
             * 
             * System.out.println("---CONTROLES---");
             * System.out.
             * println("| W: cima     |\n| A: esquerda |\n| S: baixo    |\n| D: direita  |"
             * );
             * System.out.println("| Q: atirar   |");
             * System.out.println("---------------");
             * System.out.println("Pontos: " + pontos);
             * 
             * Direcao comando = lerEntrada();
             * acaoPlayer(comando, player, inimigo1, inimigo2);
             * verificaEntidades(player);
             * 
             * Direcao comandoInimigo1 = Direcao.randomica();
             * acaoInimigo(comandoInimigo1, inimigo1, player);
             * 
             * Direcao comandoInimigo2 = Direcao.randomica();
             * acaoInimigo(comandoInimigo2, inimigo2, player);
             */
        }
    }

    public Jogador geraJogador() {
        int xPlayer = (int) (10 * (Math.random()));
        Jogador player = new Jogador(xPlayer, 11, Direcao.CIMA);
        mapa.mapaEntidades[10][xPlayer] = player;

        return player;
    }

    public Inimigo geraInimigo() {
        int xInimigo = (int) (10 * (Math.random()));
        Inimigo inimigo = new Inimigo(xInimigo, 2, Direcao.BAIXO);
        mapa.mapaEntidades[2][xInimigo] = inimigo;

        return inimigo;
    }

public void posicionaEntidades() {

    for (Entidade e : entidades) {
        if (!e.vivo) continue;

        int x = e.getX();
        int y = e.getY();

        if (x < 0 || x >= 13 || y < 0 || y >= 13) continue;

        mapa.mapaEntidades[y][x] = e;
    }

    for (Disparo d : disparos) {
        int x = d.getX();
        int y = d.getY();

        if (x < 0 || x >= 13 || y < 0 || y >= 13) continue;

        mapa.mapaEntidades[y][x] = d;
    }
}

public void percorreMapaEntidades() {
    System.out.println("--- ENTIDADES NO MAPA ---");

    for (int i = 0; i < 13; i++) {
        for (int j = 0; j < 13; j++) {

            Entidade e = mapa.mapaEntidades[i][j];

            if (e instanceof Atingivel && !e.vivo) {

                Vazio vazio = new Vazio(j, i);
                mapa.mapaEntidades[i][j] = new Vazio(j, i);
                System.out.print(vazio.getChar());
            } else {
                System.out.print(e.getChar());
            }
        }
        System.out.println();
    }
}

    public void acaoPlayer(Direcao comando, Personagem player, Inimigo inimigo1, Inimigo inimigo2) {

        if (comando == Direcao.TIRO) {
            Disparo tiro = player.atirar(player.ultimaDirecao);
            disparos.add(tiro);
        } else {
            int novoX = player.proximoX(comando);
            int novoY = player.proximoY(comando);

            if (podeMover(novoX, novoY)) {
                mapa.mapaEntidades[player.getY()][player.getX()] = new Vazio(player.getX(), player.getY());
                player.andar(comando);
            }
        }
    }

    public void acaoInimigo(Direcao comando, Personagem inimigo, Jogador player) {

        if (comando == Direcao.TIRO) {
            Disparo tiro = inimigo.atirar(inimigo.ultimaDirecao);
            disparos.add(tiro);
        } else {
            int novoX = inimigo.proximoX(comando);
            int novoY = inimigo.proximoY(comando);

            if (podeMover(novoX, novoY)) {
                mapa.mapaEntidades[inimigo.getY()][inimigo.getX()] = new Vazio(inimigo.getX(), inimigo.getY());
                inimigo.andar(comando);
            }
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

    // public void geraBlocos(Mapa mapa) {
    // for (int j = 0; j < 13; j++) {
    // for (int i = 0; i < 13; i++) {
    // if (mapa.grid[i][j] == '#') {
    // entidades.add(new BlocoAco(j, i));
    // } else if (mapa.grid[i][j] == '%') {
    // entidades.add(new BlocoTijolo(j, i));
    // } else if (mapa.grid[i][j] == 'B') {
    // entidades.add(new Base(j, i, true));
    // mapa.grid[i][j] = '_';
    // }
    // }
    // }

    // }

    public boolean podeMover(int x, int y) {
        if (x < 0 || x > 12 || y < 0 || y > 12)
            return false;
        if (mapa.mapaEntidades[y][x] instanceof Vazio)
            return true;
        else
            return false;
    }

public void moveDisparos() {
    List<Disparo> disparosParaRemover = new ArrayList<>();

    for (Disparo tiro : disparos) {
        tiro.move();

        if (tiro.horiz < 0 || tiro.horiz >= 13 ||
            tiro.verti < 0 || tiro.verti >= 13) {

            disparosParaRemover.add(tiro);
            continue;
        }

        if ((mapa.mapaEntidades[tiro.verti][tiro.horiz] instanceof BlocoAco)) {
            disparosParaRemover.add(tiro);
        }
    }

    disparos.removeAll(disparosParaRemover);
}
    public void verificaEntidades(Jogador player) {
        List<Disparo> disparosParaRemover = new ArrayList<>();
        List<Entidade> InimigosParaRemover = new ArrayList<>();

        for (Disparo tiro : disparos) {
            for (Entidade e : entidades) {
                if (e.vivo && e.getX() == tiro.getX() && e.getY() == tiro.getY() && !e.destrutivo) {
                    disparosParaRemover.add(tiro);
                    break;
                }
                if (e.vivo && e.getX() == tiro.getX() && e.getY() == tiro.getY() && e.destrutivo) {
                    e.vivo = false;
                    disparosParaRemover.add(tiro);

                    if (e instanceof Inimigo) {
                        InimigosParaRemover.add(e);
                        pontos += 100;
                        System.out.println("Inimigo destruído! +100 pontos.");
                    }
                    break;
                }
            }

            for (Entidade e : mapa.blocos) {
                if (e.vivo && e.getX() == tiro.getX() && e.getY() == tiro.getY() && e.destrutivo) {
                    e.vivo = false;
                    disparosParaRemover.add(tiro);
                    break;
                }
            }

            if (player.vivo && player.getX() == tiro.getX() && player.getY() == tiro.getY()) {
                player.vida--;
                disparosParaRemover.add(tiro);
                System.out.println("Jogador atingido!");
            }
        }
        disparos.removeAll(disparosParaRemover);
        entidades.removeAll(InimigosParaRemover);
    }

    public void verificaVitoria(List<Entidade> elementos, Jogador player) {
        boolean inimigoVivo = false;
        boolean baseViva = false;

        for (Entidade e : elementos) {
            if (e instanceof Inimigo && e.vivo) {
                inimigoVivo = true;
            }
            if (mapa.mapaEntidades[12][6] instanceof Base && mapa.mapaEntidades[12][6].vivo) {
                baseViva = true;
            }
        }

        if (!inimigoVivo) {
            System.out.println("Você venceu! Todos os inimigos foram destruídos.");
            System.exit(0);
        }

        if (!baseViva) {
            System.out.println("Você perdeu! A base foi destruída.");
            System.exit(0);
        }
        if (player.vida <= 0 || !player.vivo) {
            player.vivo = false;
            System.exit(0);
        }
    }
}
