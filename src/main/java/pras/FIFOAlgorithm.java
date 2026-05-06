package pras;

import java.util.LinkedList;
import java.util.Queue;

/**
 * FIFO (First-In, First-Out) — Substituição de Página por Ordem de Chegada.
 * Estratégia: quando ocorre uma falta de página e a memória está cheia,
 * descartar a página que está há mais tempo na memória (a mais antiga).
 * Uma fila modela isso naturalmente: novas páginas entram no final,
 * a página mais antiga está sempre no início.
 */
public class FIFOAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calculatePageFaults(int[] referenceString, int memoryFrames) {

        // A fila representa as páginas atualmente na memória.
        // Frente da fila = página carregada há mais tempo (mais antiga).
        // Final da fila = página carregada mais recentemente.
        Queue<Integer> frames = new LinkedList<>();

        int pageFaults = 0; // contador incrementado toda vez que uma página não está na memória

        for (int page : referenceString) {

            // Verifica se a página já está carregada na memória (acerto).
            if (frames.contains(page)) {
                // Acerto: nada a fazer, sem falta, sem substituição.
                continue;
            }

            // --- FALTA DE PÁGINA ---
            pageFaults++; // esta referência causou uma falta

            if (frames.size() == memoryFrames) {
                // Memória cheia: remove a página MAIS ANTIGA (frente da fila).
                frames.poll();
            }

            // Carrega a página faltante na memória (insere no final da fila).
            frames.add(page);
        }

        return pageFaults;
    }
}
