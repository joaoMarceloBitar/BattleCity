package Interface;

import Jogo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import PowerUps.*;

public class TelaJogo extends JFrame implements JogoListener {
    final private Jogo jogo;
    private static final int linhas = 13;
    private static final int colunas = 13;
    private final Grid[][] grid = new Grid[linhas][colunas];

    private ImageIcon iconJogador;
    private ImageIcon iconInimigo;
    private ImageIcon iconTiro;
    private ImageIcon iconBase;
    private ImageIcon iconTijolo;
    private ImageIcon iconAco;
    private ImageIcon iconGelo;
    private ImageIcon iconGelado;
    private ImageIcon iconCapacete;
    private ImageIcon iconKit;
    private ImageIcon iconEscudo;
    private InimigoAI threadIA;
    private ProjetilThread threadProjetil;
    private GameLoop threadGameLoop;

    public TelaJogo(Jogo jogo) {
        this.jogo = jogo;
        // jogo.getSoundPlayer().tocar("/Som/Sons/Skank - Saideira versão 8 bit
        // [kPrPaCw1UIU].wav");
        this.jogo.getMapa().renderizaMapa();
        carregarIcones();
        jogo.setTela(this);
        jogo.setListener(this);

        this.threadGameLoop = new GameLoop(jogo);
        Thread tGame = new Thread(threadGameLoop);
        tGame.setDaemon(true);
        tGame.start();

        this.threadIA = new InimigoAI(jogo);
        Thread tIA = new Thread(threadIA);
        tIA.setDaemon(true);
        tIA.start();

        this.threadProjetil = new ProjetilThread(jogo);
        Thread tProj = new Thread(threadProjetil);
        tProj.setDaemon(true);
        tProj.start();

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
        renderizaMapa();

        setVisible(true);
    }

    public void pararTudo() {
        if (threadIA != null) {
            threadIA.parar();
        }
        if (threadProjetil != null) {
            threadProjetil.parar();
        }
        if (threadGameLoop != null) {
            threadGameLoop.parar();
        }
        jogo.setTela(null);
        jogo.setListener(null);

        this.dispose();
    }

    private void carregarIcones() {
        iconJogador = new ImageIcon(getClass().getResource("/Imagens/player.gif"));
        iconInimigo = new ImageIcon(getClass().getResource("/Imagens/inimigo.gif"));
        iconTiro = new ImageIcon(getClass().getResource("/Imagens/tiro.png"));
        iconBase = new ImageIcon(getClass().getResource("/Imagens/base.gif"));
        iconTijolo = new ImageIcon(getClass().getResource("/Imagens/tijolo.gif"));
        iconAco = new ImageIcon(getClass().getResource("/Imagens/aco.png"));
        iconCapacete = new ImageIcon(getClass().getResource("/Imagens/iconCapacete.gif"));
        iconGelo = new ImageIcon(getClass().getResource("/Imagens/iconGelo.gif"));
        iconKit = new ImageIcon(getClass().getResource("/Imagens/iconKit.gif"));
        iconGelado = new ImageIcon(getClass().getResource("/Imagens/iconGelado.gif"));
        iconEscudo = new ImageIcon(getClass().getResource("/Imagens/playerEscudo.gif"));
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

    public void atualizarTela() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                Entidade e = jogo.getMapa().getMapEntidades()[i][j];
                if (e instanceof Vazio || e instanceof PowerUps) {
                    grid[i][j].limpaTela();
                }
            }
        }

        for (Entidade e : jogo.getEntidades()) {
            if (e.isVivo()) {
                if (e instanceof Inimigo) {
                    if (jogo.isInimigosCongelados()) {
                        grid[e.getY()][e.getX()].setImagem(iconGelado);
                    } else {
                        grid[e.getY()][e.getX()].setImagem(iconInimigo);
                    }
                } else if (e instanceof Kit)
                    grid[e.getY()][e.getX()].setImagem(iconKit);
                else if (e instanceof Gelo)
                    grid[e.getY()][e.getX()].setImagem(iconGelo);
                else if (e instanceof Capacete)
                    grid[e.getY()][e.getX()].setImagem(iconCapacete);
            }
        }

        for (Disparo d : jogo.getDisparosParaRemover()) {
            int x = d.getX(), y = d.getY();
            if (x >= 0 && x < 13 && y >= 0 && y < 13)
                grid[y][x].limpaTela();
            int ox = d.getOldX(), oy = d.getOldY();
            if (ox >= 0 && ox < 13 && oy >= 0 && oy < 13)
                grid[oy][ox].limpaTela();
        }

        for (Entidade morto : jogo.getInimigosParaRemover()) {
            int x = morto.getX(), y = morto.getY();
            if (x >= 0 && x < 13 && y >= 0 && y < 13)
                grid[y][x].limpaTela();
        }

        for (Entidade e : jogo.getEntidades()) {
            if (e instanceof Inimigo && e.isVivo()) {
                int ox = ((Inimigo) e).getOldX(), oy = ((Inimigo) e).getOldY();
                if (ox >= 0 && ox < 13 && oy >= 0 && oy < 13)
                    grid[oy][ox].limpaTela();
                grid[e.getY()][e.getX()].setImagem(iconInimigo);
            }
        }

        for (Disparo d : jogo.getDisparos()) {
            int ox = d.getOldX(), oy = d.getOldY();
            if (ox >= 0 && ox < 13 && oy >= 0 && oy < 13)
                grid[oy][ox].limpaTela();
            int x = d.getX(), y = d.getY();
            if (x >= 0 && x < 13 && y >= 0 && y < 13)
                grid[y][x].setImagem(iconTiro);
        }

        Jogador p = jogo.getPlayer();
        if (p != null && p.isVivo()) {
            grid[p.getY()][p.getX()].setImagem(iconJogador);
        }

        for (Entidade e : jogo.getMapa().getBlocos()) {
            if (e instanceof BlocoAco && e.isVivo()) {
                grid[e.getY()][e.getX()].setImagem(iconAco);
            }
        }
    }

    private void mostrarOverlayFimDeJogo(boolean venceu) {
        String caminhoImagem = venceu ? "/Imagens/telaVitoria.png" : "/Imagens/telaDerrota.png";
        Image bgOverlay = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
        JPanel overlay = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgOverlay, 0, 0, getWidth(), getHeight(), this);
            }
        };

        overlay.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        overlay.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE)), gbc);

        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;

        // JLabel titulo = new JLabel(venceu ? "VOCÊ VENCEU!" : "GAME OVER");
        // titulo.setFont(new Font("Arial", Font.BOLD, 32));
        // titulo.setForeground(venceu ? Color.YELLOW : Color.RED);
        // gbc.gridy = 0;
        // overlay.add(titulo, gbc);

        JLabel pts = new JLabel("Pontuação: " + jogo.getPlayer().getPontos() + " pts");
        pts.setFont(new Font("Arial", Font.PLAIN, 18));
        pts.setForeground(Color.WHITE);
        gbc.gridy = 1;
        overlay.add(pts, gbc);

        JButton menuBtn = new JButton("VOLTAR AO MENU");
        menuBtn.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 12, 30, 12);
        overlay.add(menuBtn, gbc);

        if (venceu) {
            JButton continuarBtn = new JButton("CONTINUAR");
            continuarBtn.setFont(new Font("Arial", Font.BOLD, 20));
            gbc.gridy = 3;
            gbc.insets = new Insets(0, 12, 30, 12);
            overlay.add(continuarBtn, gbc);

            continuarBtn.addActionListener(e -> {
                pararTudo();
                int proxNivel = this.jogo.getNivelAtual() + 1;
                this.jogo.setNivelAtual(proxNivel);
                this.jogo.iniciar();
                this.jogo.retomar();
                SwingUtilities.invokeLater(() -> {
                    new TelaJogo(this.jogo).setVisible(true);
                });
            });
        }

        menuBtn.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(
                    this,
                    "Digite seu nome para o ranking:",
                    "Fim de jogo",
                    JOptionPane.PLAIN_MESSAGE);
            if (nome == null || nome.trim().isEmpty())
                nome = "Jogador";

            Ranking.salvar(nome.trim(), jogo.getPlayer().getPontos());

            pararTudo();
            this.jogo.setNivelAtual(1);
            SwingUtilities.invokeLater(() -> {
                this.jogo.resetarPlayer();
                new TelaInicial(this.jogo).setVisible(true);
            });
        });

        getLayeredPane().add(overlay, JLayeredPane.POPUP_LAYER);
        overlay.setBounds(0, 0, getWidth(), getHeight());
        revalidate();
        repaint();
    }

    @Override
    public void onPassarDeFase() {
        mostrarOverlayFimDeJogo(true);
    }

    @Override
    public void onEncerrarJogo() {
        mostrarOverlayFimDeJogo(false);
    }

    public void renderizaMapa() {

        for (Entidade e : jogo.getEntidades()) {
            if (e.isVivo()) {

                if (e instanceof Inimigo) {
                    if (jogo.isInimigosCongelados()) {
                        grid[e.getY()][e.getX()].setImagem(iconGelado);
                    } else {
                        grid[e.getY()][e.getX()].setImagem(iconInimigo);
                    }
                } else if (e instanceof Kit) {
                    grid[e.getY()][e.getX()].setImagem(iconKit);
                } else if (e instanceof Gelo) {
                    grid[e.getY()][e.getX()].setImagem(iconGelo);
                } else if (e instanceof Capacete) {
                    grid[e.getY()][e.getX()].setImagem(iconCapacete);
                }
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

        if (p == null)
            return;

        int oldX = jogo.getPlayer().getX();
        int oldY = jogo.getPlayer().getY();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> comando = Direcao.CIMA;
            case KeyEvent.VK_DOWN -> comando = Direcao.BAIXO;
            case KeyEvent.VK_LEFT -> comando = Direcao.ESQUERDA;
            case KeyEvent.VK_RIGHT -> comando = Direcao.DIREITA;
            case KeyEvent.VK_Q -> comando = Direcao.TIRO;
            case KeyEvent.VK_P -> {
                if (jogo.isPausado()) {
                    jogo.retomar();
                } else {
                    jogo.pausar();
                }
                return;
            }
        }

        if (comando == null)
            return;

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
