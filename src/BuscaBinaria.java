/**
 * Classe utilitária para realizar busca binária em arrays de produtos.
 * Permite buscar produtos por ID ou por descrição em arrays previamente ordenados.
 */
public class BuscaBinaria {

    /**
     * Busca binária por ID do produto
     * @param produtos Array de produtos ordenado por ID
     * @param idProcurado ID do produto a ser procurado
     * @return O produto se encontrado, null caso contrário
     */
    public static Produto buscarPorId(Produto[] produtos, int idProcurado) {
        if (produtos == null || produtos.length == 0) {
            return null;
        }
        
        int esquerda = 0;
        int direita = produtos.length - 1;
        
        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            int idMeio = produtos[meio].hashCode();
            
            if (idMeio == idProcurado) {
                return produtos[meio];
            } else if (idMeio < idProcurado) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }
        
        return null;
    }
}