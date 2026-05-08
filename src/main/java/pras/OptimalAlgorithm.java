package pras;

import java.util.ArrayList;
import java.util.List;

public class OptimalAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calculatePageFaults(int[] referenceString, int memoryFrames) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];

            if (!frames.contains(page)) {
                // Falta de Página
                pageFaults++;

                if (frames.size() < memoryFrames) {
                    frames.add(page);
                } else {
                    // Memória cheia, decidir qual página substituir
                    int victimIndex = -1;
                    int furthestUse = -1;

                    for (int j = 0; j < frames.size(); j++) {
                        int currentPage = frames.get(j);
                        int nextUse = Integer.MAX_VALUE;

                        // Qual a próxima vez que esta página será usada?
                        for (int k = i + 1; k < referenceString.length; k++) {
                            if (referenceString[k] == currentPage) {
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
