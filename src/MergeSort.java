import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        iniciar();
        mergeSortRec(dadosOrdenados, 0, dadosOrdenados.length - 1);
        terminar();
        return dadosOrdenados;
    }

    private void mergeSortRec(T[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            mergeSortRec(vetor, esquerda, meio);
            mergeSortRec(vetor, meio + 1, direita);
            merge(vetor, esquerda, meio, direita);
        }
    }

    private void merge(T[] vetor, int esquerda, int meio, int direita) {
        int tamanhoEsquerda = meio - esquerda + 1;
        int tamanhoDireita = direita - meio;

        T[] vetorEsquerda = Arrays.copyOfRange(vetor, esquerda, meio + 1);
        T[] vetorDireita = Arrays.copyOfRange(vetor, meio + 1, direita + 1);

        int i = 0, j = 0, k = esquerda;

        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            comparacoes++;
            if (vetorEsquerda[i].compareTo(vetorDireita[j]) <= 0) {
                vetor[k] = vetorEsquerda[i];
                i++;
            } else {
                vetor[k] = vetorDireita[j];
                j++;
            }
            k++;
            movimentacoes++;
        }

        while (i < tamanhoEsquerda) {
            vetor[k] = vetorEsquerda[i];
            i++;
            k++;
            movimentacoes++;
        }

        while (j < tamanhoDireita) {
            vetor[k] = vetorDireita[j];
            j++;
            k++;
            movimentacoes++;
        }
    }
}
