/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_2015;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class Graph {
    private static final String LINE_SEPARATOR = Lapr1_2015.LINE_SEPARATOR;
    private static final String[] colors = {"black","blue","orange","yellow","red","pink"};
    
    public static boolean makeGraph(String title, String outputFileName, String terminal, String problem){
        String[] lines = problem.split(LINE_SEPARATOR);
        
        String header = getInitialCommands(terminal, outputFileName, title);
        
        String functions = "";
        
        String plotLine = "plot ";
        
        String signal = FileTools.getSignal(lines[1]);
        
        if(signal.equals(">=")){
            signal = "x2";
        }else{
            signal = "x1";
        }
        int nrVar = FileTools.getNumberOfVariables(problem);
        int colorIndex = 0;
        
        for(int i = 1; i< lines.length; i++){
            functions += String.format("%n r%d(x) = %s",i,convertFunctionForScript(lines[i], nrVar));
            if(colorIndex >= colors.length){
                colorIndex = 0;
            }
            plotLine += String.format("r%d(x) with filledcurve %s lt rgb '%s',", i,signal,colors[colorIndex]);
            colorIndex++;
        }
        
        //remove the last comma in the plot line
        plotLine = plotLine.substring(0,plotLine.length() - 1);
        String script = header + functions + LINE_SEPARATOR + plotLine;
        
        try{
            Formatter output = new Formatter(new File("graphScript.plt"));
            output.format("%s", script);
            output.close();
            
            //run the script
            Runtime  rt = Runtime.getRuntime(); 
            Process prcs = rt.exec("gnuplot graphScript.plt");
            
        } catch (Exception ex) {
            return false;
        }
        
        return true;
    }
    
    private static String getInitialCommands(String terminal, String fileName, String title){
        return String.format(
                "reset%n"
                + "set style fill transparent solid 0.4%n"
                + "set terminal %s%n"
                + "set output '%s.%s'%n"
                + "set title '%s'",
                terminal,
                fileName,
                terminal,
                title);
        /*return "reset" + LINE_SEPARATOR +
                "set style fill transparent solid 0.4";*/
    }
    
    private static String convertFunctionForScript(String line, int nrVar){
        float[] coefficients = new float[nrVar +1];
        
        String parts[] = line.split("<=|>=");

        int column;

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);
        while (m.find()) {

            String variable = m.group(1);

            float coefficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            coefficients[column - 1] += coefficient;
        }
        
        coefficients[coefficients.length - 1] = MathTools.parseToFloat(parts[1]);
        
        if(coefficients[0] != 0){
            coefficients[coefficients.length - 1] /=  coefficients[0];
        }
        
       
        String function = String.format("%.2f", coefficients[coefficients.length - 1]);
        
        if(nrVar == 2){
           coefficients[1] *= -1;
           if(coefficients[0] != 0){
                coefficients[1] /= coefficients[0];
           }
           function += String.format(" %+.2f*x", coefficients[1]);
        }
        
        return function.replaceAll(",", ".");
    }
    
    
}
