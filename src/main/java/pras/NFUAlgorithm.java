package pras;

public class NFUAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calcularPageFaults(int[] referencias, int quadros) {

        int[] frames   = new int[quadros]; // número da página em cada slot
        int[] counters = new int[quadros]; // contagem de referências por slot

        // Inicia todos os slots vazios
        java.util.Arrays.fill(frames, -1);

        int pageFaults = 0;

        for (int page : referencias) {

            // Busca a página nos slots atuais
            int hitSlot = -1;
            for (int i = 0; i < quadros; i++) {
                if (frames[i] == page) {
                    hitSlot = i; // encontrada no slot i
                    break;
                }
            }

            if (hitSlot != -1) {
                // Acerto incrementa o contador dessa página
                counters[hitSlot]++;
                continue; // sem falta
            }

            // FALTA DE PÁGINA
            pageFaults++;

            // Procura um slot vazio primeiro
            int targetSlot = -1;
            for (int i = 0; i < quadros; i++) {
                if (frames[i] == -1) {
                    targetSlot = i; // usa este slot vazio
                    break;
                }
            }

            if (targetSlot == -1) {
                // Tudo ocupado: descartar a página menos usada
                int minCount = Integer.MAX_VALUE;
                for (int i = 0; i < quadros; i++) {
                    if (counters[i] < minCount) {
                        minCount   = counters[i];
                        targetSlot = i; // este slot contém a vítima LFU
                    }
                }
                // Zera o contador do slot descartado
                counters[targetSlot] = 0;
            }

            // Carrega a página no slot
            frames[targetSlot]   = page;
            counters[targetSlot] = 0;
        }

        return pageFaults;
    }
}
