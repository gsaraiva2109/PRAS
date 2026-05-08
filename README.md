# Algoritmos de Substituição de Páginas

- Autor 1: Gustavo Chaves Macêdo.
- Autor 2: Gabriel Saraiva.

---

## Resumo
Esse projeto apresenta o desenvolvimento de um simulador de gerenciamento de memória virtual e na análise de desempenho de algoritmos de substituição de páginas. Foram implementados os algoritmos FIFO, LRU, NFU e Ótimo, com o objetivo é medir a eficiência de cada algororítimo através da contagem de faltas de página.

---

## 1. Introdução
O gerenciamento de memória virtual permite que processos executem utilizando mais memória do que a disponível fisicamente. No entanto, quando uma página necessária não está na RAM, ocorre uma *falta de página*. A escolha de qual página remover para dar lugar à nova é extremamente importante. Esse simulador explora essa problemática, comparando quatro abordagens para otimização do sistema operacional.

## 2. Metodologia
O simulador foi desenvolvido em **Java**, utilizando os seguintes componentes:
- **Lógica de Negócio:** Interface `PageReplacementAlgorithm` para padronização básica.
- **Interface Gráfica:** Java Swing para entrada de dados e JFreeChart para geração de gráficos.
- **Entrada:** Cadeia de referências (inteiros) e número de quadros disponíveis.

### Algoritmos Implementados:
1.  **FIFO (First-In First-Out):** Substitui a página mais antiga na memória.
2.  **LRU (Least Recently Used):** Substitui a página não usada há mais tempo.
3.  **NFU (Not Frequently Used):** Substitui a página com menor frequência de uso (contador de acessos).
4.  **Ótimo (OPT):** Substitui a página que será usada mais tarde no futuro, ou seja, demorará mais tempo para ser utilizada novamente.

---

## 3. Resultados e Discussão
Ao executar os algorítimos, o sistema gera uma saída no console e um gráfico de barras comparativo.

**Exemplo de Saída no Console:**
```text
Método FIFO - 15 faltas de página.
Método LRU - 12 faltas de página.
Método Ótimo - 9 faltas de página.
Método NFU - 14 faltas de página.
```

O gráfico permite visualizar a "Anomalia de Belady" em alguns cenários e como o algoritmo Ótimo sempre estabelece o limite inferior de faltas de página, servindo como métrica de excelência para os demais.

---

## 4. Conclusão
O simulador cumpre os requisitos acadêmicos, demonstrando que algoritmos que consideram o histórico de uso (LRU) tendem a ser mais eficientes que abordagens simples (FIFO). A implementação do algoritmo Ótimo comprova que, embora impraticável em sistemas reais, sua previsibilidade é essencial para o estudo teórico da eficiência de gerenciamento de memória.

---

## Como Executar
1. Certifique-se de ter o **JDK 21** instalado.
2. Compile o projeto (via Maven ou IDE):
   ```bash
   mvn clean compile
   ```
3. Execute a classe principal:
   ```bash
   java -cp target/classes:target/dependency/* pras.SimulatorGUI
   ```

## Tecnologias Utilizadas
- [Java](https://www.oracle.com/java/) - Linguagem principal.
- [Swing](https://docs.oracle.com/javase/tutorial/uiswing/) - Interface gráfica.
- [JFreeChart](https://www.jfree.org/jfreechart/) - Geração de gráficos comparativos.
- [Maven](https://maven.apache.org/) - Gerenciamento de dependências.

---

# Referências

ORACLE. **Java Documentation**. Disponível em: https://docs.oracle.com/en/java/. Acesso em: 30 abril. 2026.

SDPM. **Page Replacement Algorithms Simulation**. Disponível em: https://sdpm-simulator.netlify.app/. Acesso em: 07 mai. 2026.

DEVMEDIA. **Introdução à Interface GUI no Java**. Disponível em: https://www.devmedia.com.br/introducao-a-interface-gui-no-java/25646. Acesso em: 08 mai. 2026.

# PROJETO NO GITHUB:
- [Clique aqui (Github)](https://github.com/gsaraiva2109/PRAS) - Github do projeto.
