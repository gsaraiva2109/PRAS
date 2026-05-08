package pras;

import java.util.LinkedList;

public class LRUAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calculatePageFaults(int[] referenceString, int memoryFrames) {
        // A lista é a ordem de uso
        // Início (0): página usada há mais tempo
        // Fim: página usada mais recentemente
        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (frames.contains(page)) {
                // Acerto move para o final para marcar como usado recentement
                frames.remove(Integer.valueOf(page));
                frames.add(page);
            } else {
                // Falta de Página
                pageFaults++;
                if (frames.size() == memoryFrames) {
                    // Remove a página menos usada recentemente
                    frames.removeFirst();
                }
                // Adiciona página ao final
                frames.add(page);
            }
        }

        return pageFaults;
    }
}
