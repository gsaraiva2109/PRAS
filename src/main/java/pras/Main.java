package pras;

public class Main {
    public static void main(String[] args) {
        int[] referencias = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        int quadros = 4;

        FIFOAlgorithm fifo = new FIFOAlgorithm();
        int resultado = fifo.calcularPageFaults(referencias, quadros);
        System.out.println("FIFO: " + resultado);

        NFUAlgorithm nfu = new NFUAlgorithm();
        resultado = nfu.calcularPageFaults(referencias, quadros);
        System.out.println("NFU: " + resultado);

        LRUAlgorithm lru = new LRUAlgorithm();
        resultado = lru.calcularPageFaults(referencias, quadros);
        System.out.println("LRU: " + resultado);

        OptimalAlgorithm optimal = new OptimalAlgorithm();
        resultado = optimal.calcularPageFaults(referencias, quadros);
        System.out.println("Optimal: " + resultado);
    }
}
