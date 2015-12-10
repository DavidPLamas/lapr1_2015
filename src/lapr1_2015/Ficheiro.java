package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Grupo 2
 */
public class Ficheiro {
    public static int contaNrLinhas(File ficheiro){
        int nrLinhas = 0;
        try {
            Scanner scan = new Scanner(ficheiro);
            while(scan.hasNextLine()){
                String linha = scan.nextLine();
                if(!linha.trim().equals("")){
                    nrLinhas++;
                }
            }
        } catch (Exception e) {
        }
        
        return nrLinhas;
    }
    
    public static boolean intrepertarFuncaoObjetivo(String linha, float [][] matriz, int linhaDaMatriz){
        int posDoIgual = linha.indexOf("=");
        linha = linha.substring(posDoIgual +1);
        linha = Tools.retirarEspacos(linha);
        int col = 0;
        while(!linha.equals("")){
            int proxOperador = 0;
            String variavel = "";
            //Prcura o proximo + ou -
            if((proxOperador = linha.indexOf("+", 1)) != -1 || (proxOperador = linha.indexOf("-",1)) != -1){
                variavel = linha.substring(0, proxOperador);
            }
            //Nao encontrou o proximo operador, logo está no final da linha
            else{
                proxOperador = linha.length();
                variavel = linha;
            }
            
            
            
            //Apaga a variavel da linha
            linha = linha.substring(proxOperador);
            
        }
        
        return true;
    }
 
    public static int analisarLinha(String linha, float[][] matriz, int linhaDaMatriz){
        linha = linha.trim();
        if(linha.equals("")){
            return linhaDaMatriz;
        }
        
        int nrCols = matriz[linhaDaMatriz].length;
        
        int indice = 0;
        
        float linhaTemp[] = new float[nrCols];
        
        if(linhaDaMatriz == 0){
            intrepertarFuncaoObjetivo(linha, matriz, linhaDaMatriz);
        }
        
        
        return ++linhaDaMatriz;
        
    }
}
