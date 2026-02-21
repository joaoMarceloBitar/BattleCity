package Jogo;

import java.io.*;
import java.util.*;

public class Ranking {

    private static final String ARQUIVO = "ranking.txt";
    private static final int MAX_ENTRADAS = 10;

    public static void salvar(String nome, int pontos) {
        List<String[]> entradas = carregar();

        entradas.add(new String[]{nome, String.valueOf(pontos)});

        entradas.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));

        if (entradas.size() > MAX_ENTRADAS) {
            entradas = entradas.subList(0, MAX_ENTRADAS);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (String[] entrada : entradas) {
                pw.println(entrada[0] + ";" + entrada[1]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar ranking: " + e.getMessage());
        }
    }

    public static List<String[]> carregar() {
        List<String[]> entradas = new ArrayList<>();

        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return entradas;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    entradas.add(partes);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar ranking: " + e.getMessage());
        }

        return entradas;
    }
}