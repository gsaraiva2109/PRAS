package pras;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulatorGUI extends JFrame {

    private final JTextField referenceStringField;
    private final JTextField frameCountField;

    private JPanel chartContainer;

    public SimulatorGUI() {
        super("Simulador de Algoritmos de Substituição de Páginas");

        // Entrada
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        inputPanel.add(new JLabel("String de Referência (separada por vírgulas):"));
        referenceStringField = new JTextField("1,2,3,4,1,2,5,1,2,3,4,5", 30);
        inputPanel.add(referenceStringField);

        inputPanel.add(new JLabel("Quadros:"));
        frameCountField = new JTextField("4", 4);
        inputPanel.add(frameCountField);

        JButton simulateButton = new JButton("Simular");
        inputPanel.add(simulateButton);

        // Área do gráfico
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(new JLabel("Pressione Simular para ver os resultados.", SwingConstants.CENTER));

        // Janela
        setLayout(new BorderLayout());
        add(inputPanel,     BorderLayout.NORTH);
        add(chartContainer, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        simulateButton.addActionListener(e -> runSimulation());
    }

    /** Executa a simulação e atualiza o gráfico. */
    private void runSimulation() {

        // Entrada: "7,0,1,2" -> int[] {7,0,1,2}.
        int[] refString;
        int frames;
        try {
            String[] tokens = referenceStringField.getText().trim().split("\\s*,\\s*");
            refString = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                refString[i] = Integer.parseInt(tokens[i]);
            }
            frames = Integer.parseInt(frameCountField.getText().trim());
            if (frames <= 0) throw new NumberFormatException("quadros deve ser > 0");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Entrada inválida: " + ex.getMessage(),
                "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mantém a ordem do gráfico.
        Map<String, PageReplacementAlgorithm> algorithms = new LinkedHashMap<>();
        algorithms.put("FIFO",    new FIFOAlgorithm());
        algorithms.put("LRU",     new LRUAlgorithm());
        algorithms.put("Ótimo",   new OptimalAlgorithm());
        algorithms.put("NFU",     new NFUAlgorithm());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        boolean anyImplemented = false;

        for (Map.Entry<String, PageReplacementAlgorithm> entry : algorithms.entrySet()) {
            String name = entry.getKey();
            int result  = entry.getValue().calcularPageFaults(refString, frames);

            if (result == -1) {
                continue;
            }

            dataset.addValue(result, "Faltas de Página", name);
            System.out.println("Método " + name + " - " + result + " faltas de página");
            anyImplemented = true;
        }

        if (!anyImplemented) {
            JOptionPane.showMessageDialog(this,
                "Nenhum algoritmo implementado ainda.",
                "Simulação Vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Comparação de Faltas de Página",
            "Algoritmo",
            "Faltas de Página",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}
