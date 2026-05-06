package pras;

/**
 * Contrato comum para todos os algoritmos de substituição de páginas.
 * Cada algoritmo recebe a string de referência completa e o número de quadros,
 * e retorna quantas faltas de página ocorreram durante a simulação.
 * Retorne -1 se o algoritmo ainda não foi implementado.
 */
public interface PageReplacementAlgorithm {

    /**
     * @param referenceString  sequência ordenada de páginas acessadas pelo processo
     * @param memoryFrames     número de quadros de página disponíveis na memória física
     * @return                 total de faltas de página produzidas, ou -1 se não implementado
     */
    int calculatePageFaults(int[] referenceString, int memoryFrames);
}
