<<<<<<< Updated upstream
import java.util.Arrays;

public class BubbleSort<T extends Comparable<T>> implements IOrdenador<T>{

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

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        movimentacoes+=3;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        iniciar();
        for (int i = tamanho - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                comparacoes++;
                if ((dadosOrdenados[j].compareTo(dadosOrdenados[j+1]) > 0))
                swap (j, j + 1, dadosOrdenados);
            }
        }	
        terminar();
        return dadosOrdenados;
    }

}
=======
import java.util.Comparator;

public class BubbleSort<T extends Comparable<T>> implements IOrdenator<T> {

	private T[] dadosOrdenados;
	private Comparator<T> comparador;
	private long comparacoes;
	private long movimentacoes;
	private long inicio;
	private long termino;
	
	public BubbleSort() {
		
		comparacoes = 0;
		movimentacoes = 0;
		setComparador(T::compareTo);
	}
	
	public BubbleSort(Comparator<T> comparador) {
		
		comparacoes = 0;
		movimentacoes = 0;
		setComparador(comparador);
	}
	
	@Override
	public void setComparador(Comparator<T> comparador) {
		this.comparador = comparador;
	}
	
	@Override
	public T[] ordenar(T[] dados) {

		dadosOrdenados = dados;
		
		comparacoes = 0;
		movimentacoes = 0;
		iniciar();
		
		for (int i = dadosOrdenados.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				comparacoes++;
				if (comparador.compare(dadosOrdenados[j], dadosOrdenados[j + 1]) > 0)
					swap (j, j + 1);
			}
		}
		
		terminar();
		
		return  dadosOrdenados;
	}
	
	private void swap(int i, int j) {
	      
		movimentacoes++;
		
		T temp = dadosOrdenados[i];
	    dadosOrdenados[i] = dadosOrdenados[j];
	    dadosOrdenados[j] = temp;
	}
	
	@Override
	public long getComparacoes() {
		return comparacoes;
	}
	
	@Override
	public long getMovimentacoes() {
		return movimentacoes;
	}
	
	private void iniciar() {
		inicio = System.nanoTime();
	}
	
	private void terminar() {
		termino = System.nanoTime();
	}
	
	@Override
	public double getTempoOrdenacao() {
		
		double tempoTotal;
		
	    tempoTotal = (termino - inicio) / 1_000_000;
	    return tempoTotal;
	}
}
>>>>>>> Stashed changes
