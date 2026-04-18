import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if (o1.getQuantosProdutos() == o2.getQuantosProdutos()) {
            if (o1.getDataPedido().equals(o2.getDataPedido())) {
    		    if(o1.getIdPedido() > o2.getIdPedido()){
                    return -1;
                } else {
                    return 1;
                }
    	    } else if (o1.getDataPedido().isBefore(o2.getDataPedido())) {
        	    return -1;
            } else {
        	    return 1;
                } 
        } else if(o1.getQuantosProdutos() > o2.getQuantosProdutos()){
            return -1;
        } else {
            return 1;
        }
    }
}
