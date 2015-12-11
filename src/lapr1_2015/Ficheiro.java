package lapr1_2015;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    public static boolean interpretarFuncaoObjetivo(String linha, float [][] matriz, int linhaDaMatriz){
        int col = 0;
        Matcher m = Pattern.compile("(" + MathTools.padraoVariavel +")").matcher(linha);
        
        while(m.find()){
            String variavel = m.group(1);
            float coeficiente = MathTools.retirarCoeficienteDeVariavel(variavel);
            col = MathTools.retirarIndiceDeX(variavel);
            matriz[linhaDaMatriz][col] = MathTools.calcularSimetrico(coeficiente);
        }
        
        matriz[linhaDaMatriz][matriz[linhaDaMatriz].length] = 0;
        
        return true;
    }
    
    public static int interpretarRestricao(String linha, float [][] matriz, int linhaDaMatriz){
        String parts[] = linha.split("<=");
        int col = 0;
        Matcher m = Pattern.compile("(" + MathTools.padraoVariavel +")").matcher(linha);
        
        while(m.find()){
            //@todo continuar
            /*String variavel = m.group(1);
            float coeficiente = MathTools.retirarCoeficienteDeVariavel(variavel);
            col = MathTools.retirarIndiceDeX(variavel);*/
            //matriz[linhaDaMatriz][col] = MathTools.calcularSimetrico(coeficiente);
        }
        return ++linhaDaMatriz;
    }
 
    public static int analisarLinha(String linha, float[][] matriz, int linhaDaMatriz){
        linha = Tools.retirarEspacos(linha);
        if(linha.equals("")){
            return linhaDaMatriz;
        }
        
        int nrCols = matriz[linhaDaMatriz].length;
        
        int indice = 0;
        
        float linhaTemp[] = new float[nrCols];
        
        if(linhaDaMatriz == 0){
            interpretarFuncaoObjetivo(linha, matriz, linhaDaMatriz);
        }else{
            interpretarRestricao(linha, matriz, linhaDaMatriz);
        }
        
        
        return ++linhaDaMatriz;
        
    }
    
    public static boolean isValid(File ficheiro){
        int nrLinha = 0;
        try {
            Scanner scan = new Scanner(ficheiro);
            
            while(scan.hasNextLine()){
                String linha = scan.nextLine();
                if(linha.equals("")){
                    continue;
                }
                if(nrLinha == 0){
                    if(!MathTools.validaFuncaoObjetiva(Tools.retirarEspacos(linha))){
                        //@todo log erros?
                        return false;
                    }
                }else{
                    if(!MathTools.validaRestricao(Tools.retirarEspacos(linha))){
                        //@todo log erros?
                        return false;
                    }
                }
                
                nrLinha++;
            }
        } catch (Exception e) {
            return false;
        }
        
        return (nrLinha > 0);
    }
}
