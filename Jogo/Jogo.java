package Jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import Interface.TelaJogo;
import Som.SoundPlayer;
import PowerUps.*;

public class Jogo {
    private static final Scanner scan = new Scanner(System.in);
    private Jogador player;
    private List<Entidade> entidades = new ArrayList<>();
    List<Disparo> disparosParaRemover = new ArrayList<>();
    List<Entidade> InimigosParaRemover = new ArrayList<>();
    private List<Disparo> disparos = new ArrayList<>();
    private Mapa mapa;
    private int nivelAtual = 1;
    private int mapaEscolhido = -1;
    private TelaJogo tela;
    private boolean pausado = false;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private JogoListener listener;
    boolean jogoEncerrado = false;
    private boolean inimigosCongelados = false;

    public void setListener(JogoListener listener) {
        this.listener = listener;
    }

    public SoundPlayer getSoundPlayer() {
        return this.soundPlayer;
    }

    public TelaJogo getTela() {
        return this.tela;
    }

    public void setTela(TelaJogo tela) {
        this.tela = tela;
    }

    public Jogador getPlayer() {
        return this.player;
    }

    public List<Entidade> getEntidades() {
        return this.entidades;
    }

    public List<Disparo> getDisparos() {
        return this.disparos;
    }

    public List<Entidade> getInimigosParaRemover() {
        return this.InimigosParaRemover;
    }

    public List<Disparo> getDisparosParaRemover() {
        return this.disparosParaRemover;
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void setMapaEscolhido(int id) {
        this.mapaEscolhido = id;
    }

    public void setInimigosCongelados(boolean congelado) {
        this.inimigosCongelados = congelado;
    }

    public boolean isInimigosCongelados() {
        return this.inimigosCongelados;
    }

    public synchronized void update() {
        verificaEntidades(this.player);
        verificaColisaoCorporal();
        verificaColeta(this.player);
        verificaVitoria(entidades, this.player);
    }

    public synchronized List<Inimigo> getInimigos() {
        List<Inimigo> inimigos = new ArrayList<>();
        for (Entidade e : entidades) {
            if (e instanceof Inimigo) {
                inimigos.add((Inimigo) e);
            }
        }
        return inimigos;
    }

    public synchronized void pausar() {
        this.pausado = true;
    }

    public synchronized void retomar() {
        this.pausado = false;
        notifyAll();
    }

    public boolean isPausado() {
        return this.pausado;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public int getNivelAtual() {
        return this.nivelAtual;
    }

    public synchronized void esperarSePausado() {
        while (pausado) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void encerraThreads() {
        this.pausado = false;
        this.jogoEncerrado = true;
    }

    public void iniciar() {
        int numMapa;
        int pontosAnteriores = (this.player != null) ? this.player.pontos : 0;
        int vidaAnterior = (this.player != null) ? this.player.vida : 3;
        String nomeAnterior = (this.player != null) ? this.player.getNome() : "Jogador";

        if (mapaEscolhido != -1) {
            numMapa = mapaEscolhido;
        } else {
            numMapa = Math.min(nivelAtual, 3);
            // numMapa = min + (int) (Math.random() * ((max - min) + 1));
            // entidades.clear();
        }
        mapa = new Mapa("Mapas/mapa" + numMapa + ".txt");
        this.mapa.renderizaMapa();

        this.entidades.clear();
        this.disparos.clear();
        this.disparosParaRemover.clear();
        this.InimigosParaRemover.clear();
        this.jogoEncerrado = false;

        this.player = geraJogador();
        this.player.pontos = pontosAnteriores;
        this.player.vida = vidaAnterior;
        this.player.setNome(nomeAnterior);
        this.player.vivo = true;

        this.entidades.add(player);
        this.entidades.add(geraInimigo());
        this.entidades.add(geraInimigo());

        if (this.tela != null) {
            this.tela.carregaCenario();
            this.tela.renderizaMapa();
        }

        posicionaEntidades();
        retomar();
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
            System.out.println("Pontos: " + player.pontos);

            Direcao comando = lerEntrada();
            acaoPlayer(comando, player);
            verificaEntidades(player);

            Direcao comandoInimigo1 = Direcao.randomica();
            acaoInimigo(comandoInimigo1, inimigo1, player);

            Direcao comandoInimigo2 = Direcao.randomica();
            acaoInimigo(comandoInimigo2, inimigo2, player);
            verificaEntidades(player);
        }
    }

    public void executarCiclo() {
        moveDisparos();
        posicionaEntidades();

        for (Entidade e : entidades) {
            if (e instanceof Inimigo && e.isVivo() == true) {
                Direcao comandoIni = Direcao.randomica();
                acaoInimigo(comandoIni, (Inimigo) e, this.player);
            }
        }

        verificaEntidades(this.player);
        verificaVitoria(entidades, this.player);
    }

    public Jogador geraJogador() {
        int xPlayer = (int) (10 * (Math.random()));
        Jogador player = new Jogador(xPlayer, 11, Direcao.CIMA);
        mapa.mapaEntidades[10][xPlayer] = player;

        return player;
    }

    public Inimigo geraInimigo() {
        int xInimigo = -1;
        int yInimigo = -1;

        for (int tentativa = 0; tentativa < 20; tentativa++) {
            int xTentativa = (int) (Math.random() * 13);
            int yTentativa = (int) (Math.random() * 3);
            if (mapa.mapaEntidades[yTentativa][xTentativa] instanceof Vazio) {
                xInimigo = xTentativa;
                yInimigo = yTentativa;
                break;
            }
        }
        if (xInimigo == -1) {
            xInimigo = 0;
            yInimigo = 0;
        }
        Inimigo inimigo = new Inimigo(xInimigo, yInimigo, Direcao.BAIXO);
        mapa.mapaEntidades[yInimigo][xInimigo] = inimigo;
        return inimigo;
    }

    public void posicionaEntidades() {

        for (Entidade e : entidades) {
            if (!e.vivo)
                continue;

            int x = e.getX();
            int y = e.getY();

            if (x < 0 || x >= 13 || y < 0 || y >= 13)
                continue;

            mapa.mapaEntidades[y][x] = e;
        }

        for (Disparo d : disparos) {
            int x = d.getX();
            int y = d.getY();

            if (x < 0 || x >= 13 || y < 0 || y >= 13)
                continue;

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

    public void acaoPlayer(Direcao comando, Personagem player) {

        if (comando == Direcao.TIRO) {
            Disparo tiro = player.atirar(player.ultimaDirecao);
            disparos.add(tiro);
        } else {
            int novoX = player.proximoX(comando);
            int novoY = player.proximoY(comando);

            if (podeMoverPlayer(novoX, novoY)) {
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

    public boolean podeMover(int x, int y) {
        if (x < 0 || x > 12 || y < 0 || y > 12)
            return false;
        Entidade alvo = mapa.mapaEntidades[y][x];
        return alvo instanceof Vazio || alvo instanceof PowerUps;
    }

    public boolean podeMoverPlayer(int x, int y) {
        if (x < 0 || x > 12 || y < 0 || y > 12)
            return false;
        Entidade alvo = mapa.mapaEntidades[y][x];
        return alvo instanceof Vazio || alvo instanceof PowerUps || alvo instanceof BlocoCaixa;
    }

    public void verificaColisaoCorporal() {
        if (player == null || !player.vivo)
            return;

        for (Entidade e : entidades) {
            if (e instanceof Inimigo && e.vivo
                    && e.getX() == player.getX() && e.getY() == player.getY()) {
                player.vida--;
                System.out.println("Inimigo colidiu com o jogador! Vida: " + player.vida);
                ((Inimigo) e).horiz = ((Inimigo) e).getOldX();
                ((Inimigo) e).verti = ((Inimigo) e).getOldY();
            }
        }
    }

    public void moveDisparos() {
        List<Disparo> aRemoverAgora = new ArrayList<>();

        for (Disparo tiro : disparos) {
            tiro.move();

            if (tiro.horiz < 0 || tiro.horiz >= 13 ||
                    tiro.verti < 0 || tiro.verti >= 13) {
                aRemoverAgora.add(tiro);
                continue;
            }

            if (mapa.mapaEntidades[tiro.verti][tiro.horiz] instanceof BlocoAco) {
                aRemoverAgora.add(tiro);
            }
        }

        disparosParaRemover.clear();
        disparosParaRemover.addAll(aRemoverAgora);
        disparos.removeAll(aRemoverAgora);
    }

    public void verificaEntidades(Jogador player) {
        List<Disparo> aRemoverAgora = new ArrayList<>();

        for (Disparo tiro : new ArrayList<>(disparos)) {

            for (Entidade e : mapa.blocos) {
                if (e.vivo && e.getX() == tiro.getX() && e.getY() == tiro.getY()) {
                    if (e instanceof Base) {
                        e.vivo = false;
                        aRemoverAgora.add(tiro);
                        break;
                    }
                    if (e instanceof BlocoTijolo && e.destrutivo) {
                        e.vivo = false;
                        PowerUps novoPowerUp = PowerUps.getPowerUps(e.getX(), e.getY());

                        if (novoPowerUp != null) {
                            synchronized (entidades) {
                                this.entidades.add(novoPowerUp);
                            }
                            mapa.getMapEntidades()[e.getY()][e.getX()] = novoPowerUp;
                        } else {
                            mapa.getMapEntidades()[e.getY()][e.getX()] = new Vazio(e.getX(), e.getY());
                        }
                        aRemoverAgora.add(tiro);
                        break;
                    }
                    if (e instanceof BlocoAco) {
                        aRemoverAgora.add(tiro);
                        break;
                    }
                }
            }

            if (aRemoverAgora.contains(tiro))
                continue;

            for (Entidade e : entidades) {
                if (e instanceof Inimigo && e.vivo
                        && e.getX() == tiro.getX() && e.getY() == tiro.getY()) {
                    e.vivo = false;
                    InimigosParaRemover.add(e);
                    aRemoverAgora.add(tiro);
                    player.pontos += 100;
                    System.out.println("Inimigo destruído! +100 pontos.");
                    break;
                }
            }

            if (aRemoverAgora.contains(tiro))
                continue;

            if (player.vivo && player.getX() == tiro.getX() && player.getY() == tiro.getY()
                    && !player.getInvulneravel()) {

                boolean emCima = mapa.mapaEntidades[player.getY()][player.getX()] instanceof BlocoCaixa
                        || estaSobreCaixa(player);

                if (!emCima && !player.getInvulneravel()) {
                    player.vida--;
                    aRemoverAgora.add(tiro);
                    System.out.println("Jogador atingido! Vida: " + player.vida);
                } else {
                    aRemoverAgora.add(tiro);
                    System.out.println("Tiro bloqueado pela caixa!");
                }
            }
        }

        disparosParaRemover.addAll(aRemoverAgora);
        disparos.removeAll(aRemoverAgora);
        entidades.removeAll(InimigosParaRemover);
        InimigosParaRemover.clear();

        verificaColisaoCorporal();
    }

    private boolean estaSobreCaixa(Jogador player) {
        for (Entidade e : mapa.blocos) {
            if (e instanceof BlocoCaixa && e.vivo
                    && e.getX() == player.getX() && e.getY() == player.getY()) {
                return true;
            }
        }
        return false;
    }

    public void verificaVitoria(List<Entidade> elementos, Jogador player) {
        if (jogoEncerrado)
            return;

        boolean inimigoVivo = false;
        for (Entidade e : elementos) {
            if (e instanceof Inimigo && e.vivo) {
                inimigoVivo = true;
                break;
            }
        }

        boolean baseViva = (mapa.mapaEntidades[12][6] instanceof Base)
                && mapa.mapaEntidades[12][6].vivo;

        if (!baseViva || player.vida <= 0 || !player.vivo) {
            jogoEncerrado = true;
            player.vivo = false;
            pausar();
            if (listener != null)
                SwingUtilities.invokeLater(() -> listener.onEncerrarJogo());
            return;
        }

        if (!inimigoVivo) {
            jogoEncerrado = true;
            pausar();
            if (listener != null)
                SwingUtilities.invokeLater(() -> listener.onPassarDeFase());
        }
    }

    public void verificaColeta(Jogador player) {
        for (Entidade e : new ArrayList<>(entidades)) {
            if (e instanceof PowerUps && e.getX() == player.getX() && e.getY() == player.getY()) {
                if (e instanceof Kit) {
                    player.setVida(player.getVida() + 2);
                    e.setVivo((false));
                    entidades.remove(e);
                } else if (e instanceof Capacete) {
                    ((Capacete) e).invulneravel(this);
                    e.setVivo(false);
                    entidades.remove(e);
                } else if (e instanceof Gelo) {
                    ((Gelo) e).congela(this);
                    e.setVivo(false);
                    entidades.remove(e);
                }
            }
        }
    }

    public void resetarPlayer() {
        if (this.player != null) {
            this.player.pontos = 0;
            this.player.vida = 3;
            this.player.setNome("Jogador");
        }
        this.nivelAtual = 1;
    }
}
