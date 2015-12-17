package lapr1_2015;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Group 2
 */
public class FileTools {
    
    public static int getNumberOfLines(File file){
        
        int nrLines = 0;
        
        try {
            
            Scanner scan = new Scanner(file);
            
            while(scan.hasNextLine()){
                
                String line = scan.nextLine();
                
                if(!line.trim().equals("")){
                    
                    nrLines++;
                }
            }
            
        } catch (Exception e) {
            
        }
        
        return nrLines;
    }
    
    public static boolean interpretObjectiveFunction(String line, float [][] matrix, int matrixLine){
        
        int column;
        
        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN +")").matcher(line);
        
        while(m.find()){
            
            String variable = m.group(1);
            
            float coeficient = MathTools.getVariableCoeficient(variable);
            
            column = MathTools.getXIndex(variable);
            
            matrix[matrixLine][column -1] = MathTools.calculateSimetric(coeficient);
        }
        
        matrix[matrixLine][matrix[matrixLine].length - 1] = 0;
        
        return true;
    }
    
    public static boolean interpretRestriction(String line, float [][] matrix, int matrixLine){
        
        String parts[] = line.split("<=");
        
        int col;
        
        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN +")").matcher(parts[0]);
        
        //Fills X1, X2, etc.
        while(m.find()){
            
            String variable = m.group(1);
            
            float coeficient = MathTools.getVariableCoeficient(variable);
            
            col = MathTools.getXIndex(variable);
            
            matrix[matrixLine][col -1] = coeficient;
        }
        
        //Fills S1, S2, etc.
        matrix[matrixLine][matrixLine + 1] = 1;
        
        //Fills solution.
        matrix[matrixLine][matrix[matrixLine].length -1] = Float.parseFloat(parts[1]);
        
        
        return true;
    }
 
    public static int readLine(String line, float[][] matrix, int matrixLine){
        
        line = Tools.removeSpaces(line);
        
        if(line.equals("")){
            
            return matrixLine;
            
        }
        
        if(matrixLine == 0){
            
            interpretObjectiveFunction(line, matrix, matrixLine);
            
        }else{
            
            interpretRestriction(line, matrix, matrixLine);
            
        }
        
        return ++matrixLine;
        
    }
    
    public static boolean isValid(File file){
        
        int nrLine = 0;
        
        try {
            
            Scanner scan = new Scanner(file);
            
            while(scan.hasNextLine()){
                
                String line = scan.nextLine();
                
                if(line.equals("")){
                    
                    continue;
                }
                
                if(nrLine == 0){
                    
                    if(!MathTools.validatesObjectiveFunction(Tools.removeSpaces(line))){
                        //@todo log errors?
                        
                        return false;
                    }
                    
                }else{
                    
                    if(!MathTools.validatesRestriction(Tools.removeSpaces(line))){
                        //@todo log errors?
                        
                        return false;
                    }
                }
                
                nrLine++;
            }
        } catch (Exception e) {
            
            return false;
            
        }
        
        return (nrLine > 0);
    }
}
