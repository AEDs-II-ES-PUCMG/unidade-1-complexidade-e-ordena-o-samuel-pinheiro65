
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static Produto[] produtosOrdenadorPorId;
    static Produto[] produtosOrdenadorPorDescricao;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;

    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Por identificador (padrão)");
        System.out.println("2 - Por código (ID)");
        System.out.println("3 - Por descrição");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
            
            // Criar cópias ordenadas por ID e descrição
            if (dadosCarregados != null && quantProdutos > 0) {
                criarCopiasOrdenadas(dadosCarregados);
            }
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    /**
     * Cria duas cópias dos produtos: uma ordenada por ID e outra por descrição
     * @param dados Array de produtos original
     */
    static void criarCopiasOrdenadas(Produto[] dados) {
        // Criar cópia ordenada por ID
        produtosOrdenadorPorId = Arrays.copyOf(dados, dados.length);
        IOrdenador<Produto> ordenadorId = new Mergesort<>();
        produtosOrdenadorPorId = ordenadorId.ordenar(produtosOrdenadorPorId);
        
        // Criar cópia ordenada por descrição
        produtosOrdenadorPorDescricao = Arrays.copyOf(dados, dados.length);
        IOrdenador<Produto> ordenadorDescricao = new Mergesort<>();
        produtosOrdenadorPorDescricao = ordenadorDescricao.ordenar(produtosOrdenadorPorDescricao, new ComparadorPorDescricao());
    }


    static Produto localizarProduto() {
        cabecalho();
        System.out.println("BUSCA DE PRODUTOS");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por Descrição");
        int tipoBusca = lerNumero("Escolha o tipo de busca", Integer.class);
        
        Produto localizado = null;
        
        if (tipoBusca == 1) {
            int idProcurado = lerNumero("Digite o ID do produto", Integer.class);
            localizado = BuscaBinaria.buscarPorId(produtosOrdenadorPorId, idProcurado);
        } else if (tipoBusca == 2) {
            System.out.print("Digite a descrição do produto: ");
            String descricaoProcurada = teclado.nextLine();
            localizado = BuscaBinaria.buscarPorDescricao(produtosOrdenadorPorDescricao, descricaoProcurada);
        }
        
        return localizado;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        cabecalho();
        System.out.println("ORDENAÇÃO DE PRODUTOS");
        
        int opcaoAlgoritmo = exibirMenuOrdenadores();
        if (opcaoAlgoritmo == 0) return;
        
        int opcaoComparador = exibirMenuComparadores();
        if (opcaoComparador == 0) return;
        
        // Criar o ordenador baseado na opção
        switch(opcaoAlgoritmo) {
            case 1 -> ordenador = new Bubblesort<>();
            case 2 -> ordenador = new InsertSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new Mergesort<>();
            default -> {
                System.out.println("Opção inválida");
                return;
            }
        }
        
        // Ordenar com o comparador escolhido
        Produto[] produtosOrdenados = null;
        switch(opcaoComparador) {
            case 1 -> produtosOrdenados = ordenador.ordenar(Arrays.copyOf(produtos, produtos.length));
            case 2 -> produtosOrdenados = ordenador.ordenar(Arrays.copyOf(produtos, produtos.length), new ComparadorPorCodigo());
            case 3 -> produtosOrdenados = ordenador.ordenar(Arrays.copyOf(produtos, produtos.length), new ComparadorPorDescricao());
            default -> {
                System.out.println("Opção inválida");
                return;
            }
        }
        
        // Mostrar estatísticas
        cabecalho();
        System.out.println("RESULTADO DA ORDENAÇÃO");
        System.out.println("Comparações: " + ordenador.getComparacoes());
        System.out.println("Movimentações: " + ordenador.getMovimentacoes());
        System.out.println("Tempo (ms): " + String.format("%.4f", ordenador.getTempoOrdenacao()));
        
        // Perguntar se deseja substituir
        System.out.print("\nDeseja substituir os dados originais pelos ordenados (S/N)? ");
        String resposta = teclado.nextLine().toUpperCase();
        if (resposta.equals("S")) {
            produtos = Arrays.copyOf(produtosOrdenados, produtosOrdenados.length);
        }
    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }                        
}
