/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_2015;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class Graph {
    private static final String LINE_SEPARATOR = Lapr1_2015.LINE_SEPARATOR;
    private static final String[] COLORS = {"black","gold","dark-orange","grey","green","turquoise", 
        "blue", "violet"};
    
    public static boolean makeGraph(String title, String outputFileName, String terminal, String equations, 
            float pointX, float pointY){
        String[] lines = equations.split(LINE_SEPARATOR);
        
        String header = getInitialCommands(terminal, outputFileName, title);
        
        String functions = "";
        
        String plotLine = "";
        
        String parametrics = String.format("set parametric%n"
                + "set xrange restore%n" 
                + "set yrange restore%n" 
                + "set trange [-500:500]%n");
        
        String parametricFunctions = "";
        
        String footer = getFooterCommands();
        
        /*String signal = FileTools.getSignal(lines[1]);
        
        if(signal.equals(">=")){
            signal = "x2";
        }else{
            signal = "x1";
        }*/
        int nrVar = FileTools.getNumberOfVariables(equations);
        int colorIndex = 0;
        
        for(int i = 0; i< lines.length; i++){
            //Check if colorIndex is not out of bounds
            if(colorIndex >= COLORS.length){
                colorIndex = 0;
            }
            
            //Format the line into a graphical function
            String currentFunction = convertFunctionForScript(lines[i], nrVar);
            if(currentFunction.startsWith("p")){
                //its a parametric function
                
                //remove the "p"
                currentFunction = currentFunction.substring(1, currentFunction.length());
                
                parametricFunctions += String.format("%s,t lt rgb '%s' notitle,", currentFunction,COLORS[colorIndex]);
                plotLine += String.format("NaN lt rgb '%s' title '%s',", COLORS[colorIndex], lines[i]);
            }else{
                functions += String.format("%nf%d(x) = %s",i,currentFunction);
            
                //plotLine += String.format("r%d(x) with filledcurve %s lt rgb '%s' title '%s',", i,signal,COLORS[colorIndex], lines[i]);
                plotLine += String.format("f%d(x) lt rgb '%s' title '%s',", i,COLORS[colorIndex], lines[i]);
            }
            
            colorIndex++;
        }
        
        //Set the point to draw
        String pointLine = String.format("set label 1 '     (%.2f ; %.2f)' at %.2f|%.2f point ps 2 pointtype 2", 
                pointX, pointY, pointX, pointY).replaceAll(",", ".").replace("|", ",");
        
        if(!plotLine.equals("")){
            //remove the last comma in the plot line
            plotLine = "plot " + plotLine.substring(0,plotLine.length() - 1);
        }
        
        if(!parametricFunctions.equals("")){
            //remove the last comma in the parametrics functions
            parametrics += "plot " + parametricFunctions.substring(0, parametricFunctions.length() - 1);
        }
        
        
        //Build the whole script
        String scriptData = String.format("%s%n"
                + "%s%n"
                + "%s%n%n"
                + "%s%n%n"
                + "%s%n%n"
                + "%s%n", 
                header, functions, pointLine, plotLine, parametrics,footer);
        
        if(FileTools.saveToFile("graphScript.plt", scriptData)){
            try {
                //run the script
                Runtime  rt = Runtime.getRuntime();
                Process prcs = rt.exec("gnuplot graphScript.plt");
                return true;
            } catch (IOException ex) {
                return false;
            }
        }else{
            return false;
        }
    }
    
    private static String getInitialCommands(String terminal, String fileName, String title){
        return String.format(
                "reset%n"
                + "set style fill transparent solid 0.4%n"
                + "set terminal %s%n"
                + "set output '%s'%n"
                + "set multiplot%n"
                + "set title '%s'%n"
                + "set xrange[] writeback%n"
                + "set yrange[] writeback%n" 
                + "set trange[] writeback",
                terminal,
                fileName,
                title);
        /*return "reset" + LINE_SEPARATOR +
                "set style fill transparent solid 0.4";*/
    }
    
    private static String getFooterCommands(){
        return String.format("unset parametric%n"
                + "unset multiplot%n"
                + "unset output%n"
                + "unset terminal");
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
                    coefficients[2] /= coefficients[0];
                    function = String.format("p%.2f", coefficients[2]);
                }else{
                    //we assume coefficents[1] is diff than 0
                    //its a function y = b
                    coefficients[2] /= coefficients[1];
                    function = String.format("%.2f", coefficients[2]);
                }
            }
        }else{
            //its a parametric function
            coefficients[2] /= coefficients[0];
            function = String.format("p%.2f", coefficients[2]);
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
    
}
