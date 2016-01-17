package lapr1_2015;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Group 2
 */
public class Graph {

    /**
     * The line separator. It's imported from the main class
     * @see Lapr1_2015#LINE_SEPARATOR
     */
    private static final String LINE_SEPARATOR = Lapr1_2015.LINE_SEPARATOR;

    /**
     * The available rgb colors for the graph functions
     */
    private static final String[] COLORS = {"black", "gold", "dark-orange", "grey", "green", "turquoise",
        "blue", "violet"};

    /**
     * Build a graph using unlimited equations and one point.
     * This assumes all equations are from a two variable problem.
     *
     * @param title The title for graphic.
     * @param outputFileName The name of the file this graph will be saved
     * @param terminal The gnuplot terminal that will be used.
     * @param equations The equations that will be used to draw the graphic.
     * @param pointX The abscissa of the optimal point.
     * @param pointY The ordinate of the optimal point.
     * @return If the graph was successfully made or not.
     */
    public static boolean makeGraph(String title, String outputFileName, String terminal, String equations,
            float pointX, float pointY) {

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

        int colorIndex = 0;

        for (int i = 0; i < lines.length; i++) {

            //Check if colorIndex is not out of bounds.
            if (colorIndex >= COLORS.length) {

                colorIndex = 0;

            }

            //Format the line into a graphical function.
            String currentFunction = convertEquationForScript(lines[i]);

            if (currentFunction.startsWith("p")) {
                //It's a parametric function.

                //Remove the "p".
                currentFunction = currentFunction.substring(1, currentFunction.length());

                parametricFunctions += String.format("%s,t lt rgb '%s' notitle,", currentFunction, COLORS[colorIndex]);

                plotLine += String.format("NaN lt rgb '%s' title '%s',", COLORS[colorIndex], lines[i]);

            } else {

                functions += String.format("%nf%d(x) = %s", i, currentFunction);

                plotLine += String.format("f%d(x) lt rgb '%s' title '%s',", i, COLORS[colorIndex], lines[i]);

            }

            colorIndex++;

        }

        //Set the point to draw.
        String pointLine = String.format("set label 1 '     (%.2f ; %.2f)' at %.2f|%.2f point ps 2 pointtype 2",
                pointX, pointY, pointX, pointY).replaceAll(",", ".").replace("|", ",");

        if (!plotLine.equals("")) {

            //Remove the last comma in the plot line.
            plotLine = "plot " + plotLine.substring(0, plotLine.length() - 1);

        }

        if (!parametricFunctions.equals("")) {

            //Remove the last comma in the parametrics functions.
            parametrics += "plot " + parametricFunctions.substring(0, parametricFunctions.length() - 1);

        }

        //Build the whole script.
        String scriptData = String.format("%s%n"
                + "%s%n"
                + "%s%n%n"
                + "%s%n%n"
                + "%s%n%n"
                + "%s%n",
                header, functions, pointLine, plotLine, parametrics, footer);

        if (FileTools.saveToFile("graphScript.plt", scriptData)) {

            try {

                //Run the script.
                Runtime rt = Runtime.getRuntime();

                Process prcs = rt.exec("gnuplot graphScript.plt");

                return true;

            } catch (IOException ex) {

                return false;

            }

        } else {

            return false;

        }

    }

    /**
     * Get the initial commands that will be used in the script.
     *
     * @param terminal The gnuplot terminal that will be used.
     * @param fileName The name of the output file.
     * @param title The title of the graph.
     * @return The initial commands formatted correctly.
     */
    private static String getInitialCommands(String terminal, String fileName, String title) {

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

    }

    /**
     * Get the footer commands.
     *
     * @return The footer commands.
     */
    private static String getFooterCommands() {

        return String.format("unset parametric%n"
                + "unset multiplot%n"
                + "unset output%n"
                + "unset terminal");

    }

    /**
     * Convert an equation to a mathematical function.
     * This means the x1 will be converted to x and x2 will be converted to y.
     * If the equation has x1 only, this method will return "p" before the function
     * which means it's a parametric function. This is absolutely crucial because
     * gnuplot has a different approach when working with parametric functions
     *
     * @param line The equation to be converted.
     * @return The function in an acceptable format for gnuplot.
     */
    private static String convertEquationForScript(String equation) {

        equation = Tools.removeSpaces(equation);

        float[] coefficients = new float[3];

        String parts[];

        if (equation.contains("<=") || equation.contains(">=")) {

            parts = equation.split("<=|>=");

        } else {

            parts = equation.split("=");

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

            if(column <= 2){
                coefficients[column - 1] += coefficient;
            }

        }

        coefficients[2] = MathTools.parseToFloat(parts[1]);

        String function = "";

        if (coefficients[0] != 0 && coefficients[1] != 0) {
            //It's a function y = mx + b, and m <> 0.

            coefficients[2] /= coefficients[1];

            coefficients[0] *= -1;

            coefficients[0] /= coefficients[1];

            function = String.format("%.2f*x %+.2f", coefficients[0], coefficients[2]);

        } else if (coefficients[0] != 0) {
            //It's a parametric function.

            coefficients[2] /= coefficients[0];

            function = String.format("p%.2f", coefficients[2]);

        } else {
            //We assume coefficents[1] is different than 0.
            //It's a function y = b.

            coefficients[2] /= coefficients[1];

            function = String.format("%.2f", coefficients[2]);

        }

        return function.replaceAll(",", ".");

    }

}
