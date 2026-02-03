package Interface;

import Jogo.Jogo;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TelaInicial extends JFrame {
    final private Jogo jogo;

    public TelaInicial(Jogo jogo) {
        this.jogo = jogo;
        setSize(1000, 700);
        setTitle("De Bar em War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BackgroundPanel background = new BackgroundPanel();

        setContentPane((background));

        iniciar();
        setLocationRelativeTo(null);
    }

    private void iniciar() {
        BackgroundPanel background = (BackgroundPanel) getContentPane();

        JPanel titulo = criarTitulo();
        background.add(titulo, BorderLayout.NORTH);

        JPanel botao = criarBotaoPanel();
        background.add(botao, BorderLayout.SOUTH);

        background.revalidate();
        background.repaint();
    }


    private static class BackgroundPanel extends JPanel {
        private Image imagemFundo;

        public BackgroundPanel() {
            URL url = getClass().getResource("/Imagens/fundoJogo.png");

            if (url != null) {
                this.imagemFundo = new ImageIcon(url).getImage();
            }

            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagemFundo != null) {
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public JPanel criarTitulo() {
        JPanel tituloPanel = new JPanel();
        tituloPanel.setOpaque(false);
        tituloPanel.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel();

        URL tituloImagemURL = getClass().getResource("/Imagens/logo.gif");

        ImageIcon tituloImagemIcon = new ImageIcon(tituloImagemURL);
        Image imagem = tituloImagemIcon.getImage();
        tituloLabel.setIcon(new ImageIcon(imagem));

        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloPanel.add(tituloLabel, BorderLayout.CENTER);

        return tituloPanel;
    }

    public JPanel criarBotaoPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(10, 10, 10, 10);
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.HORIZONTAL;
        
        JButton jogarBotao = criarBotao("Jogar", "/Imagens/jogarBotao.png");
        JButton escolheMapaBotao = criarBotao("Escolher Mapa", "/Imagens/mapasBotao.png");
        JButton sairBotao = criarBotao("Sair", "/Imagens/sairBotao.png");

        grid.gridy = 0;
        panel.add(jogarBotao, grid);

        grid.gridy = 1;
        panel.add(escolheMapaBotao, grid);

        grid.gridy = 2;
        panel.add(sairBotao, grid);

        return panel;
    }

    public JButton criarBotao(String string, String caminho) {
        URL botaoUrl = getClass().getResource(caminho);
        ImageIcon botaoIcon = new ImageIcon(botaoUrl);
        JButton botao = new JButton(botaoIcon);

        botao.setPreferredSize(new Dimension(304, 62));
    

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                botao.setBackground(Color.decode("#222034"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                botao.setBackground(null);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent event) {
                botao.setBackground(Color.BLUE);
            }
        });

        botao.addActionListener(e -> acaoMouse(string));

        return botao;
    }

    public void acaoMouse(String string) {
        switch (string) {
            case "Jogar":
                telaHistoria();
                break;
            case "Escolher Mapa":
                telaSelecao();
                break;
            case "Sair":
                System.exit(0);
                break;
        }
    }

    public void telaHistoria() {
        BackgroundPanel backgroundPanel = (BackgroundPanel) getContentPane();
        backgroundPanel.removeAll();
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(20, 20, 20, 20);
        grid.gridx = 0;
        grid.anchor = GridBagConstraints.CENTER;

        URL historia = getClass().getResource("/Imagens/historia.png");
        if (historia != null) {
            ImageIcon icon = new ImageIcon(historia);
            JLabel historiaLabel = new JLabel(icon);

            grid.gridy = 0;
            backgroundPanel.add(historiaLabel, grid);
        }

        JButton continuarBotao = criarBotao("Continuar", "/Imagens/continuarBotao.png");
        grid.gridy = 1;
        backgroundPanel.add(continuarBotao, grid);

        continuarBotao.addActionListener(e -> {
            jogo.iniciar();
            TelaJogo telaJogo = new TelaJogo(this.jogo);
            telaJogo.setVisible(true);
            this.dispose();
        });

        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    public void telaSelecao() {
        BackgroundPanel backgroundPanel = (BackgroundPanel) getContentPane();
        backgroundPanel.removeAll();

        JPanel titulo = criarTitulo();
        backgroundPanel.add(titulo, BorderLayout.NORTH);

        JPanel painelMapas = new JPanel(new GridLayout(1, 3, 20, 0));

        painelMapas.add(criarBotaoMapa("Franconi", "/Imagens/franconi.png", 1));
        painelMapas.add(criarBotaoMapa("Container Bar", "/Imagens/container.png", 2));
        painelMapas.add(criarBotaoMapa("Oponente Sinuca", "/Imagens/oponente.png", 3));

        this.add(painelMapas, BorderLayout.CENTER);
        this.revalidate();
    }

    public JPanel criarBotaoMapa(String nome, String caminho, int id) {
        JPanel card = new JPanel(new BorderLayout());

        JLabel labelnome = new JLabel(nome, SwingConstants.CENTER);
        card.add(labelnome, BorderLayout.NORTH);

        JButton botaoImagem = new JButton(new ImageIcon(getClass().getResource(caminho)));
        card.setPreferredSize(new Dimension(200, 200));
        botaoImagem.addActionListener(e -> {
            jogo.setMapaEscolhido(id);
            jogo.iniciar();

            new TelaJogo(jogo).setVisible(true);
            this.dispose();
        });

        card.add(botaoImagem, BorderLayout.CENTER);
        return card;
    }

}
