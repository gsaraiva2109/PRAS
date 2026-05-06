package pras;

/**
 * NFU (Not Frequently Used) — Substituição da Página Menos Frequentemente Usada.
 *
 * Estratégia: cada página na memória possui um contador de software que acumula
 * quantas vezes ela foi referenciada. Ao ocorrer uma falta, descartar a página
 * com o MENOR contador (menos frequentemente usada).
 *
 * Implementação usa dois arrays paralelos do mesmo tamanho:
 *   frames[]   — qual página ocupa cada slot (-1 = vazio)
 *   counters[] — contador de referências para a página em cada slot
 */
public class NFUAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int calculatePageFaults(int[] referenceString, int memoryFrames) {

        int[] frames   = new int[memoryFrames]; // número da página em cada slot
        int[] counters = new int[memoryFrames]; // contagem de referências por slot

        // Inicializa todos os slots como vazios.
        java.util.Arrays.fill(frames, -1);

        int pageFaults = 0;

        for (int page : referenceString) {

            // Busca a página nos quadros atuais.
            int hitSlot = -1;
            for (int i = 0; i < memoryFrames; i++) {
                if (frames[i] == page) {
                    hitSlot = i; // encontrada no slot i
                    break;
                }
            }

            if (hitSlot != -1) {
                // Acerto: incrementa o contador desta página (ela acabou de ser referenciada).
                counters[hitSlot]++;
                continue; // sem falta
            }

            // --- FALTA DE PÁGINA ---
            pageFaults++;

            // Procura um slot vazio primeiro (fase de preenchimento inicial).
            int targetSlot = -1;
            for (int i = 0; i < memoryFrames; i++) {
                if (frames[i] == -1) {
                    targetSlot = i; // usa este slot vazio
                    break;
                }
            }

            if (targetSlot == -1) {
                // Sem slot vazio: descartar a página com MENOR contador (menos usada).
                int minCount = Integer.MAX_VALUE;
                for (int i = 0; i < memoryFrames; i++) {
                    if (counters[i] < minCount) {
                        minCount   = counters[i];
                        targetSlot = i; // este slot contém a vítima LFU
                    }
                }
                // Zera o contador do slot descartado antes de reutilizá-lo.
                counters[targetSlot] = 0;
            }

            // Carrega a página faltante no slot escolhido.
            // Contador começa em 0 (esta referência conta a partir do próximo acesso).
            frames[targetSlot]   = page;
            counters[targetSlot] = 0;
        }

        return pageFaults;
    }
}
