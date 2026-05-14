package pras;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calcularPageFaults(int[] referencias, int quadros) {

        // é as páginas atualmente na memória
        // frente da fila = mais antiga
        // final da fila = mais recente
        Queue<Integer> frames = new LinkedList<>();

        int faltaDePaginas = 0;

        for (int page : referencias) {

            // Verifica se a pagina carregada
            if (frames.contains(page)) {
                continue;
            }

            // Falta de página
            faltaDePaginas++;

            if (frames.size() == quadros) {
                frames.poll();
            }

            frames.add(page);
        }

        return faltaDePaginas;
    }
}
