package pras;

import java.util.ArrayList;
import java.util.List;

public class OptimalAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calcularPageFaults(int[] referencias, int quadros) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < referencias.length; i++) {
            int page = referencias[i];

            if (!frames.contains(page)) {
                // Falta de Página
                pageFaults++;

                if (frames.size() < quadros) {
                    frames.add(page);
                } else {
                    // Memória cheia, decidir qual página substituir
                    int victimIndex = -1;
                    int furthestUse = -1;

                    for (int j = 0; j < frames.size(); j++) {
                        int currentPage = frames.get(j);
                        int nextUse = Integer.MAX_VALUE;

                        // Qual a próxima vez que esta página será usada?
                        for (int k = i + 1; k < referencias.length; k++) {
                            if (referencias[k] == currentPage) {
                                nextUse = k;
                                break;
                            }
                        }

                        if (nextUse == Integer.MAX_VALUE) {
                            victimIndex = j;
                            break;
                        }

                        if (nextUse > furthestUse) {
                            furthestUse = nextUse;
                            victimIndex = j;
                        }
                    }
                    // Substitui
                    frames.set(victimIndex, page);
                }
            }
        }

        return pageFaults;
    }
}
