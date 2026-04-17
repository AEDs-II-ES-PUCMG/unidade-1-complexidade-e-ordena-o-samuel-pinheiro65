import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }


    public static void main(String[] args) {
        int tam = 20;
        Integer[] vetor = gerarVetorObjetos(tam);

        System.out.println("=== TESTE DE ALGORITMOS DE ORDENAÇÃO ===\n");
        
        // Teste Bubblesort
        Bubblesort<Integer> bolha = new Bubblesort<>();
        Integer[] vetorOrdenadoBolha = bolha.ordenar(Arrays.copyOf(vetor, vetor.length));
        System.out.println("Vetor ordenado método Bolha:");
        System.out.println("Comparações: " + bolha.getComparacoes());
        System.out.println("Movimentações: " + bolha.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());
        
        // Teste InsertionSort
        System.out.println("\n");
        InsertionSort<Integer> insercao = new InsertionSort<>();
        Integer[] vetorOrdenadoInsercao = insercao.ordenar(Arrays.copyOf(vetor, vetor.length));
        System.out.println("Vetor ordenado método Inserção:");
        System.out.println("Comparações: " + insercao.getComparacoes());
        System.out.println("Movimentações: " + insercao.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + insercao.getTempoOrdenacao());
        
        // Teste SelectionSort
        System.out.println("\n");
        SelectionSort<Integer> selecao = new SelectionSort<>();
        Integer[] vetorOrdenadoSelecao = selecao.ordenar(Arrays.copyOf(vetor, vetor.length));
        System.out.println("Vetor ordenado método Seleção:");
        System.out.println("Comparações: " + selecao.getComparacoes());
        System.out.println("Movimentações: " + selecao.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + selecao.getTempoOrdenacao());
        
        // Teste Mergesort
        System.out.println("\n");
        Mergesort<Integer> merge = new Mergesort<>();
        Integer[] vetorOrdenadoMerge = merge.ordenar(Arrays.copyOf(vetor, vetor.length));
        System.out.println("Vetor ordenado método Mergesort:");
        System.out.println("Comparações: " + merge.getComparacoes());
        System.out.println("Movimentações: " + merge.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + merge.getTempoOrdenacao());
    }
}
