package Interface;

import Jogo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class TelaJogo extends JFrame{
    final private Jogo jogo;
    private static final int linhas = 13;
    private static final int colunas = 13;
    private final Grid[][] grid  = new Grid[linhas][colunas];

    private ImageIcon iconJogador;
    private ImageIcon iconInimigo;
    private ImageIcon iconTiro;
    private ImageIcon iconBase;
    private ImageIcon iconTijolo;
    private ImageIcon iconAco;

    public TelaJogo(Jogo jogo) {
        this.jogo = jogo;
        this.jogo.getMapa().renderizaMapa();
        carregarIcones();
        Timer timer = new Timer(800, e -> {
            for (Entidade morto : jogo.getInimigosParaRemover()) {
                grid[morto.getY()][morto.getX()].limpaTela();
            }

            for (Entidade en : jogo.getDisparosParaRemover()) {
                grid[en.getY()][en.getX()].limpaTela();
            }

            jogo.executarCiclo();
            jogo.verificaEntidades(jogo.getPlayer());

            for (Entidade ent : jogo.getEntidades()) {
                if (ent instanceof Inimigo && ent.isVivo()) {
                    int oldX = ((Inimigo) ent).getOldX();
                    int oldY = ((Inimigo) ent).getOldY();

                    if (oldX != ent.getX() || oldY != ent.getY()) {
                        atualizarPosicao(oldX, oldY, ent.getX(), ent.getY(), iconInimigo);
                    }
                }               
            }
            atualizarDisparo();
            jogo.getInimigosParaRemover().clear();
            jogo.getDisparosParaRemover().clear();
        });
        timer.start();

        setTitle("De Bar em War");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel mapaPanel = criarPanelMapa();
        JPanel lateralPanel = criarPanelLateral();

        add(mapaPanel, BorderLayout.CENTER);
        add(lateralPanel, BorderLayout.EAST);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                teclaPressionada(e);
            }
        });

        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        setLocationRelativeTo(null);

        carregaCenario();

        setVisible(true);
    }

    private void carregarIcones() {
        iconJogador = new ImageIcon(getClass().getResource("/Imagens/player.gif"));
        iconInimigo = new ImageIcon(getClass().getResource("/Imagens/inimigo.gif"));
        iconTiro = new ImageIcon(getClass().getResource("/Imagens/tiro.png"));
        iconBase = new ImageIcon(getClass().getResource("/Imagens/base.gif"));
        iconTijolo = new ImageIcon(getClass().getResource("/Imagens/tijolo.gif"));
        iconAco = new ImageIcon(getClass().getResource("/Imagens/aco.png"));


    }

    public static class Grid extends JPanel {
        private final JLabel imagemLabel;

        public Grid() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.lightGray));
            setOpaque(false);
            imagemLabel = new JLabel();
            imagemLabel.setHorizontalAlignment(JLabel.CENTER);
            imagemLabel.setVerticalAlignment(JLabel.CENTER);
            add(imagemLabel, BorderLayout.CENTER);
        }

        public void setImagem(ImageIcon icon) {
            imagemLabel.setIcon(icon);
        }

        public void limpaTela() {
            imagemLabel.setIcon(null);
        }
    }

    public void renderizaMapa() {
        /*
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grid[i][j].limpaTela();
            }
        }
        */

        for (Entidade e : jogo.getEntidades()) {
            if (e.isVivo() == true && e instanceof Inimigo) {
                grid[e.getY()][e.getX()].setImagem(iconInimigo);
            }
        }

        Jogador p = jogo.getPlayer();
        if (p != null && p.isVivo()) {
            grid[p.getY()][p.getX()].setImagem(iconJogador);
        }

        for (Disparo d : jogo.getDisparos()) {
            grid[d.getY()][d.getX()].setImagem((iconTiro));
        }
    }

    public void atualizarPosicao(int x, int y, int newX, int newY, ImageIcon icon) {
        grid[y][x].limpaTela();
        grid[newY][newX].setImagem(icon);
    }

    public JPanel criarPanelMapa() {
        Image backgroundImage = null;

        URL backgroundImageURL = getClass().getResource("/Imagens/backgroundJogo.png");
        ImageIcon backgroundIcon = new ImageIcon(backgroundImageURL);
        backgroundImage = backgroundIcon.getImage();

        BackgroundPanel mapaPanel = new BackgroundPanel(backgroundImage);
        mapaPanel.setLayout(new GridLayout(linhas, colunas, 0, 0));
        mapaPanel.setPreferredSize(new Dimension(500, 500));

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Grid gr = new Grid();
                grid[i][j] = gr;
                mapaPanel.add(gr);
            }
        }

        return mapaPanel;

    }

    public void carregaCenario() {
        Entidade[][] entidades = jogo.getMapa().getMapEntidades();

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Entidade e = entidades[i][j];

                if (e instanceof BlocoAco) {
                    grid[i][j].setImagem(iconAco);
                } else if (e instanceof BlocoTijolo) {
                    grid[i][j].setImagem(iconTijolo);
                } else if (e instanceof Base) {
                    grid[i][j].setImagem(iconBase);
                }
            }
        }
    }
    
    public JPanel criarPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.DARK_GRAY);
        panel.setPreferredSize(new Dimension(300, 700));

        JLabel labelVida = new JLabel("Vida: " + jogo.getPlayer().getVida());
        JLabel labelPontos = new JLabel("Pontos: " + jogo.getPlayer().getPontos());
        labelVida.setForeground(Color.WHITE);
        labelPontos.setForeground(Color.WHITE);
        panel.add(labelVida);
        panel.add(labelPontos);

        return panel;

    }

    public void teclaPressionada(KeyEvent e) {
        Direcao comando = null;
        Jogador p = this.jogo.getPlayer();

        if (p == null) {
            return;
        }

        int oldX = jogo.getPlayer().getX();
        int oldY = jogo.getPlayer().getY();

        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                comando = Direcao.CIMA;
                break;
            }
            case KeyEvent.VK_DOWN -> {
                comando = Direcao.BAIXO;
                break;
            }
            case KeyEvent.VK_LEFT -> {
                comando = Direcao.ESQUERDA;
                break;
            }
            case KeyEvent.VK_RIGHT -> {
                comando = Direcao.DIREITA;
                break;
            }
            case KeyEvent.VK_Q -> {
                comando = Direcao.TIRO;
                break;
            }
        }
        jogo.acaoPlayer(comando, p);
        atualizarPosicao(oldX, oldY, jogo.getPlayer().getX(), jogo.getPlayer().getY(), iconJogador);
    }

    public void atualizarDisparo() {
        for (Disparo d : jogo.getDisparosParaRemover()) {
            if (d.getX() >= 0 && d.getY() >= 0 && d.getX() < 13 && d.getY() < 13) {
                grid[d.getY()][d.getX()].limpaTela();
            }
        }
        for (Disparo d : jogo.getDisparos()) {
            if (d.getOldX() >= 0 && d.getOldY() >= 0 && d.getOldX() < 13 && d.getOldY() < 13) {
                grid[d.getOldY()][d.getOldX()].limpaTela();
            }

            if (d.getX() >= 0 && d.getY() >= 0 && d.getX() < 13 && d.getY() < 13) {
                grid[d.getY()][d.getX()].setImagem(iconTiro);
            }
        }
    }

    private static class BackgroundPanel extends JPanel {
        private Image imagemFundo;

        public BackgroundPanel(Image imagem) {
            this.imagemFundo = imagem;
            setLayout(new GridLayout(linhas, colunas));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagemFundo != null) {
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
