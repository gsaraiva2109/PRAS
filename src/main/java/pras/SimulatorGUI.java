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

/**
 * Interface gráfica do Simulador de Algoritmos de Substituição de Páginas.
 * Layout:
 *   [NORTE]  painel de entrada — string de referência, nº de quadros, botão Simular
 *   [CENTRO] painel do gráfico — gráfico de barras atualizado após cada simulação
 * Algoritmos que retornam -1 (não implementados) são ignorados no gráfico.
 */
public class SimulatorGUI extends JFrame {

    // Campos de entrada mantidos como variáveis de instância para o listener do botão acessá-los.
    private final JTextField referenceStringField;
    private final JTextField frameCountField;

    // Painel central que contém o gráfico; substituído a cada execução da simulação.
    private JPanel chartContainer;

    public SimulatorGUI() {
        super("Simulador de Algoritmos de Substituição de Páginas");

        // ── Painel de Entrada (NORTE) ────────────────────────────────────────
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        inputPanel.add(new JLabel("String de Referência (separada por vírgulas):"));
        referenceStringField = new JTextField("7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1", 30);
        inputPanel.add(referenceStringField);

        inputPanel.add(new JLabel("Quadros:"));
        frameCountField = new JTextField("3", 4);
        inputPanel.add(frameCountField);

        JButton simulateButton = new JButton("Simular");
        inputPanel.add(simulateButton);

        // ── Contêiner do Gráfico (CENTRO) ────────────────────────────────────
        // Começa vazio; substituído pelo gráfico após a primeira simulação.
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(new JLabel("Pressione Simular para ver os resultados.", SwingConstants.CENTER));

        // ── Montagem da Janela ───────────────────────────────────────────────
        setLayout(new BorderLayout());
        add(inputPanel,     BorderLayout.NORTH);
        add(chartContainer, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null); // centraliza na tela

        // ── Ação do Botão ────────────────────────────────────────────────────
        simulateButton.addActionListener(e -> runSimulation());
    }

    /** Lê as entradas, executa os algoritmos implementados e renderiza o gráfico de barras. */
    private void runSimulation() {

        // Converte a string de referência (ex: "7,0,1,2") em int[] {7,0,1,2}.
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

        // Mapa ordenado: nome do algoritmo → instância.
        // Mantemos a ordem de inserção para o gráfico ficar consistente.
        Map<String, PageReplacementAlgorithm> algorithms = new LinkedHashMap<>();
        algorithms.put("FIFO",    new FIFOAlgorithm());
        algorithms.put("LRU",     new LRUAlgorithm());
        algorithms.put("Ótimo",   new OptimalAlgorithm());
        algorithms.put("NFU",     new NFUAlgorithm());

        // Executa cada algoritmo e coleta os resultados.
        // Algoritmos que retornam -1 ainda não foram implementados e são ignorados.
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        boolean anyImplemented = false;

        for (Map.Entry<String, PageReplacementAlgorithm> entry : algorithms.entrySet()) {
            String name = entry.getKey();
            int result  = entry.getValue().calculatePageFaults(refString, frames);

            if (result == -1) {
                // Algoritmo não implementado: pula sem adicionar ao gráfico.
                continue;
            }

            // Adiciona ao dataset: addValue(valor, série, categoria)
            dataset.addValue(result, "Faltas de Página", name);
            anyImplemented = true;
        }

        // Se nenhum algoritmo estiver implementado, exibe aviso.
        if (!anyImplemented) {
            JOptionPane.showMessageDialog(this,
                "Nenhum algoritmo implementado ainda.",
                "Simulação Vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Cria o gráfico de barras a partir do dataset.
        JFreeChart chart = ChartFactory.createBarChart(
            "Comparação de Faltas de Página", // título do gráfico
            "Algoritmo",                       // rótulo do eixo X
            "Faltas de Página",                // rótulo do eixo Y
            dataset,                           // dados
            PlotOrientation.VERTICAL,          // orientação
            false,                             // incluir legenda
            true,                              // tooltips
            false                              // URLs
        );

        // Embute o gráfico num painel Swing e substitui o centro da janela.
        ChartPanel chartPanel = new ChartPanel(chart);
        chartContainer.removeAll();                      // remove gráfico anterior (ou placeholder)
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();                     // força re-layout do Swing
        chartContainer.repaint();                        // redesenha
    }

    public static void main(String[] args) {
        // Todo trabalho de UI Swing deve ocorrer na Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}
