package Interface;

import Jogo.Jogo;
import Jogo.Ranking;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRanking extends JFrame {

    public TelaRanking() {
        setTitle("Ranking");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#242046"));

        JLabel titulo = new JLabel("RANKING", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.YELLOW);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBackground(Color.decode("#222034"));
        listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        List<String[]> entradas = Ranking.carregar();

        if (entradas.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma pontuação registrada ainda.");
            vazio.setForeground(Color.LIGHT_GRAY);
            vazio.setFont(new Font("Arial", Font.PLAIN, 16));
            vazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaPanel.add(vazio);
        } else {
            for (int i = 0; i < entradas.size(); i++) {
                String[] entrada = entradas.get(i);
                String texto = String.format("%2d.  %-20s %s pts", i + 1, entrada[0], entrada[1]);
                JLabel linha = new JLabel(texto);
                linha.setFont(new Font("Monospaced", Font.PLAIN, 16));
                linha.setForeground(i == 0 ? Color.YELLOW : Color.WHITE);
                linha.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                listaPanel.add(linha);
            }
        }

        add(new JScrollPane(listaPanel), BorderLayout.CENTER);

        JButton voltarBtn = new JButton("VOLTAR");
        voltarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        voltarBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.decode("#222034"));
        btnPanel.add(voltarBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}