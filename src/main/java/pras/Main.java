package pras;

public class Main {
    static void main() {
        int[] referencias = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        int quadros = 4;

        FIFOAlgorithm fifo = new FIFOAlgorithm();
        int resultado = fifo.calcularPageFaults(referencias, quadros);
        System.out.printf("Método FIFO - %d faltas de página\n", resultado);

        LRUAlgorithm lru = new LRUAlgorithm();
        resultado = lru.calcularPageFaults(referencias, quadros);
        System.out.printf("Método LRU - %d faltas de página\n", resultado);

        OptimalAlgorithm optimal = new OptimalAlgorithm();
        resultado = optimal.calcularPageFaults(referencias, quadros);
        System.out.printf("Método Ótimo - %d faltas de página\n", resultado);

        NFUAlgorithm nfu = new NFUAlgorithm();
        resultado = nfu.calcularPageFaults(referencias, quadros);
        System.out.printf("Método NFU - %d faltas de página\n", resultado);

    }
}