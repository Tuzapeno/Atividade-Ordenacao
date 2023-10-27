import java.util.Random;

public class App {

    public static long numTrocas = 0;
    public static long numIterac = 0;

    // PRINTAR DADOS **********************************************

    public static void printDados(String sortName, long iterac, long trocas, long tempo) {
        System.out.println("##### Média "+ sortName + " #####");
        System.out.println("Iterações: " + iterac);
        System.out.println("Trocas: " + trocas);
        System.out.println("Tempo: " + tempo + " ms"  + "\n");
        numIterac = 0;
        numTrocas = 0;
    }

    // SWAP *******************************************************

    public static void swap(int[] v, int a, int b) {
        int t = v[a]; v[a] = v[b]; v[b] = t;
    }

    // GERA VETOR *************************************************
    
    public static int[] gerarVetor(int tamanho) {
        Random random = new Random();
        int[] vetor = new int[tamanho];

        for (int i = 0; i < tamanho; i++)
            vetor[i] = random.nextInt(100);

        return vetor;
    }

    // .LENGHT *********************************************************

    public static int minhalength(int[] vetor) {
        int tamanho = 0;
        for(int i : vetor) tamanho++;
        return tamanho;
    }


    // BUBBLE SORT *************************************************

    public static void bubbleSort(int[] vetor) {       
        int n = minhalength(vetor);
        int temp = 0;

        for(int i = 0; i < n; i++) {
            numIterac++;
            for(int j = 1; j < (n - i); j++) {
                numIterac++;
                if(vetor[j - 1] > vetor[i]) {
                    temp = vetor[j - 1];
                    vetor[j - 1] = vetor[j];
                    vetor[j] = temp;
                    numTrocas++;
                }
            }
        }
    }

    // MERGE SORT ***********************************************************************

    public static void mergeSort(int[] vetor) {
        int len = minhalength(vetor);

        // Vetor de 1 posição já está ordenado
        if (len < 2) return;
        
        // Pega metade do vetor
        int tamanhoVet = len/2;

        // Metade vai a esquerda
        int[] esquerda = new int[tamanhoVet];
        // O resto a direita
        int[] direita = new int[len - tamanhoVet];

        // Esquerda
        for(int i = 0; i < tamanhoVet; i++) {
            numIterac++;
            esquerda[i] = vetor[i];
        }
        // Direita
        for (int i = tamanhoVet; i < len; i++) {
            numIterac++;
            direita[i - tamanhoVet] = vetor[i];
        }

        // Aplica o merge nas partes menores
        mergeSort(esquerda);
        mergeSort(direita);
        
        // Junta os vetores ordenados
        merge(esquerda, direita);
    }

    public static int[] merge(int[] a, int[] b) {
        int lenA = minhalength(a);
        int lenB = minhalength(b);

        int tamanhoVet = lenA + lenB;
        int[] resultado = new int[tamanhoVet];
        int i = 0, j = 0, k = 0;

        // Ordena pegando o maior e adicionando no terceiro vetor
        // Enquanto nenhum dos dois estiver no final
        while(i < lenA && j < lenB) {
            if (a[i] < b[j])
                resultado[k++] = a[i++];     
            else
                resultado[k++] = b[j++];
            numIterac++;
            numTrocas++;
        }

        // sobrou valores de A
        while (i < lenA) {
            numIterac++;
            resultado[k++] = a[i++];
        }
    
        // sobrou valores de B
        while (j < lenB) {
            numIterac++;
            resultado[k++] = b[j++];
        }

        return resultado;
    }

    // QUICK SORT ***********************************************************************

    public static void quickSort(int[] vetor, int esquerda, int direita) {
        int pivot;

        numIterac++;

        // vetor está ordenado por que só tem um elemento
        if (esquerda >= direita) return;

        // pega o pivo
        pivot = particiona(vetor, esquerda, direita);

        // recursao para as sub-arrays
        quickSort(vetor, esquerda, pivot - 1);
        quickSort(vetor, pivot + 1, direita);
    }

    public static int particiona(int[] vetor, int esquerda, int direita) {
        int pivot = vetor[esquerda];
        int esquerdaOriginal = esquerda;
        int direitaOriginal = direita;

        numIterac++;

        // até esquerda ultrapassar a direita
        while (esquerda <= direita) {
            numIterac++;
            
            // evitar array out of bounds
            // mover ate que seja maior que o pivo
            while (esquerda <= direitaOriginal && vetor[esquerda] <= pivot) { esquerda++; numIterac++; }
            // mover ate que seja menor/igual ao pivo
            while (direita >= esquerdaOriginal && vetor[direita] > pivot) { direita--; numIterac++; }
    
            // trocar posições
            if (esquerda < direita) {
                numTrocas++;
                swap(vetor, direita, esquerda);
            }
        }
    
        //trocar o pivo com a direita 
        swap(vetor, direita, esquerdaOriginal);
        numTrocas++;
        return direita;
    }

    // MAIN ***********************************************************************

    public static void main(String[] args) throws Exception {
        int rounds = 5;
        int nElementos = 10000;
        int[] vetorTeste = gerarVetor(nElementos);
        long tempoinicial;
        long tempofinal;

        // ***************** BUBBLE SORT *************************

        tempoinicial = System.currentTimeMillis();

        for(int i = 0; i < rounds; i++) {
            vetorTeste = gerarVetor(nElementos);
            bubbleSort(vetorTeste);
        }

        tempofinal = System.currentTimeMillis();

        printDados("Bubble Sort", numIterac/rounds, numTrocas/rounds, (tempofinal - tempoinicial)/rounds);

        // ***************** MERGE SORT *************************

        tempoinicial = System.currentTimeMillis();

        for(int i = 0; i < rounds; i++) {
            vetorTeste = gerarVetor(nElementos);
            mergeSort(vetorTeste);
        }
        
        tempofinal = System.currentTimeMillis();

        printDados("Merge Sort", numIterac/rounds, numTrocas/rounds, (tempofinal - tempoinicial)/rounds);

        // ***************** QUICK SORT *************************

        tempoinicial = System.currentTimeMillis();

        for(int i = 0; i < rounds; i++) {
            vetorTeste = gerarVetor(nElementos);
            quickSort(vetorTeste, 0, nElementos - 1);
        }
        
        tempofinal = System.currentTimeMillis();

        printDados("Quick Sort", numIterac/rounds, numTrocas/rounds, (tempofinal - tempoinicial)/rounds);
    }
}
