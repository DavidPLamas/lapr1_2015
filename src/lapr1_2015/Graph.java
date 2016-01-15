/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_2015;

import java.io.File;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class Graph {
    private static final String LINE_SEPARATOR = Lapr1_2015.LINE_SEPARATOR;
    private static final String[] COLORS = {"black","blue","orange","yellow","red","pink"};
    
    public static boolean makeGraph(String title, String outputFileName, String terminal, String equations, float pointX, float pointY){
        String[] lines = equations.split(LINE_SEPARATOR);
        
        String header = getInitialCommands(terminal, outputFileName, title);
        
        String functions = "";
        
        String plotLine = "plot ";
        
        String signal = FileTools.getSignal(lines[1]);
        
        if(signal.equals(">=")){
            signal = "x2";
        }else{
            signal = "x1";
        }
        int nrVar = FileTools.getNumberOfVariables(equations);
        int colorIndex = 0;
        
        for(int i = 0; i< lines.length; i++){
            String currentFunction = convertFunctionForScript(lines[i], nrVar);
            if(currentFunction.equals("")){
                //its parametric
                continue;
            }
            functions += String.format("%n r%d(x) = %s",i,currentFunction);
            if(colorIndex >= COLORS.length){
                colorIndex = 0;
            }
            //plotLine += String.format("r%d(x) with filledcurve %s lt rgb '%s' title '%s',", i,signal,COLORS[colorIndex], lines[i]);
            plotLine += String.format("r%d(x) lt rgb '%s' title '%s',", i,COLORS[colorIndex], currentFunction);
            colorIndex++;
        }
        
        String pointLine = String.format("set label 1 '     (%.2f ; %.2f)' at %.2f|%.2f point ps 2 pointtype 2", 
                pointX, pointY, pointX, pointY).replaceAll(",", ".").replace("|", ",");
        
        //remove the last comma in the plot line
        plotLine = plotLine.substring(0,plotLine.length() - 1);
        String script = String.format("%s%n%s%n%s%n%s", header, functions, pointLine, plotLine);
        
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
        line = Tools.removeSpaces(line);
        
        float[] coefficients = new float[nrVar +1];
        String parts[];
        if(line.contains("<=") || line.contains(">=")){
             parts = line.split("<=|>=");
        }else{
            parts = line.split("=");
            String temp = parts[1];
            parts[1] = parts[0];
            parts[0] = temp;
        }
        
        

        int column;

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);
        while (m.find()) {

            String variable = m.group(1);

            float coefficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            coefficients[column - 1] += coefficient;
        }
        
        coefficients[coefficients.length - 1] = MathTools.parseToFloat(parts[1]);
        
        String function = "";
        
        if(nrVar == 2){
            if(coefficients[0] != 0 && coefficients[1] != 0){
                //its a function y = mx + b, and m <> 0
                coefficients[2] /= coefficients[1];
                coefficients[0] *= -1;
                coefficients[0] /= coefficients[1];
                
                function = String.format("%.2f*x %+.2f", coefficients[0],coefficients[2]);
            }else {
                if(coefficients[0] != 0){
                    //its a parametric function
                    return "";
                }else{
                    //we assume coefficents[1] is diff than 0
                    //its a function y = b
                    coefficients[2] /= coefficients[1];
                    function = String.format("%.2f", coefficients[2]);
                }
            }
        }
        
        /*if(coefficients[1] != 0){
            coefficients[coefficients.length - 1] /=  coefficients[1];
        }
        //1   2   3
        
       
        String function = String.format("%.2f", coefficients[coefficients.length - 1]);
        
        if(nrVar == 2){
           coefficients[0] *= -1;
           if(coefficients[1] != 0){
                coefficients[0] /= coefficients[1];
           }
           function += String.format(" %+.2f*x", coefficients[0]);
        }*/
        
        return function.replaceAll(",", ".");
    }
    
    private static boolean isParametric(String function){
        return function.contains("[Xx]2");
    }
    
}
