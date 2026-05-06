# PRAS — Simulador de Algoritmos de Substituição de Páginas

Projeto universitário em Java que simula os principais algoritmos de substituição de páginas da memória virtual e exibe uma comparação visual via gráfico de barras.

## Algoritmos

| Algoritmo | Responsável | Status |
|-----------|-------------|--------|
| FIFO      | (Gabriel)   | ✅ Implementado |
| NFU       | (Gabriel)   | ✅ Implementado |
| LRU       | (Gustavo)   | 🔲 Pendente |
| Ótimo     | (Gustavo)   | 🔲 Pendente |

> Algoritmos pendentes retornam `-1` e são ignorados na simulação até serem implementados.

## Requisitos

- Java 25+
- Maven 3.6+

## Como executar

```bash
# Compilar
mvn compile

# Rodar direto via Maven
mvn exec:java -Dexec.mainClass=pras.SimulatorGUI

# Ou gerar o JAR e rodar
mvn package -DskipTests
java -cp "target/pras-1.0.jar:$(mvn dependency:build-classpath -q -DforceStdout)" pras.SimulatorGUI
```

## Estrutura do projeto

```
PRAS/
├── pom.xml
└── src/main/java/pras/
    ├── PageReplacementAlgorithm.java   ← interface comum
    ├── FIFOAlgorithm.java              ← First-In, First-Out
    ├── LRUAlgorithm.java               ← Least Recently Used (pendente)
    ├── OptimalAlgorithm.java           ← Algoritmo Ótimo de Belady (pendente)
    ├── NFUAlgorithm.java               ← Not Frequently Used
    └── SimulatorGUI.java               ← Interface gráfica Swing + JFreeChart
```

## Entrada

- **String de referência**: sequência de números de páginas separados por vírgula (ex: `7,0,1,2,0,3,0,4`)
- **Número de quadros**: quantidade de quadros de página disponíveis na memória física

## Exemplo clássico (Belady)

String: `7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1` — Quadros: `3`

| Algoritmo | Faltas de Página |
|-----------|-----------------|
| FIFO      | 15              |
| LRU       | 12              |
| Ótimo     | 9               |
| NFU       | (variável)      |
