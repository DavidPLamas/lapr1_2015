package lapr1_2015;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Group 2
 */
public class Lapr1_2015Test {

    /**
     * Test of formatZValue method, of class Lapr1_2015.
     */
    @Test
    public void testFindZValue() {
        System.out.printf("%nTesting Lapr1_2015.formatZValue...%n");
        float[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        String expResult = "Z = 12,00";
        String result = Lapr1_2015.formatZValue(matrix);
        assertEquals(expResult, result);

        float[][] matrix2 = {{1, 2, 3, -32}};
        expResult = "Z = -32,00";
        result = Lapr1_2015.formatZValue(matrix2);
        assertEquals(expResult, result);
        System.out.printf("End of testing Lapr1_2015.formatZValue...%n");
    }

    /**
     * Test of getOutputLineFormat method, of class Lapr1_2015.
     */
    @Test
    public void testGetOutputLineFormat() {
        System.out.printf("%nTesting Lapr1_2015.getOutputLineFormat...%n");
        int nrColumns = 5;
        String expResult = "%8s|%8s|%8s|%8s|%8s%n";
        String result = Lapr1_2015.getOutputLineFormat(nrColumns);
        assertEquals(expResult, result);

        nrColumns = 0;
        expResult = "%n";
        result = Lapr1_2015.getOutputLineFormat(nrColumns);
        assertEquals(expResult, result);

        nrColumns = 1;
        expResult = "%8s%n";
        result = Lapr1_2015.getOutputLineFormat(nrColumns);
        assertEquals(expResult, result);

        System.out.println("End of testing Lapr1_2015.getOutputLineFormat...%n");
    }

    /**
     * Test of applySimplexMethod method, of class Lapr1_2015.
     */
    @Test
    public void testApplySimplexMethod() {
        System.out.printf("%nTesting Lapr1_2015.applySimplexMethod...%n");
        String lineSeparator = System.getProperty("line.separator");
        float[][] matrix = {
            {2, 2, 1, 1, 0, 0, 12},
            {2, 1, 1, 0, 1, 0, 9},
            {1, 3, 2, 0, 0, 1, 16},
            {-8, -8, -1, 0, 0, 0, 0}
        };
        String outputFileName = "outputfiletest.txt";
        int nrVar = 3;
        String inputFileData = "Z = 8X1 + 8X2 + X3" + lineSeparator
                + "2X1 + 2X2 + X3 <= 12" + lineSeparator
                + "2X1 + X2 + X3 <= 9" + lineSeparator
                + "X1 + 3X2 + 2X3 <= 16";
        String[] variables = {"X1", "X2", "X3", "S1", "S2", "S3", "SOL"};
        float[][] expResult = {
            {0, 1, 0, 1, -1, 0, 3},
            {1, 0, 0.50F, -0.50F, 1, 0, 3},
            {0, 0, 1.50F, -2.50F, 2, 1, 4},
            {0, 0, 3, 4, 0, 0, 48}
        };
        float[][] result = Lapr1_2015.applySimplexMethod(matrix, outputFileName, nrVar, inputFileData, variables, "");
        assertArrayEquals(expResult, result);
        System.out.printf("End of testing Lapr1_2015.applySimplexMethod...%n");
    }

    /**
     * Test of findVariableValues method, of class Lapr1_2015.
     */
    @Test
    public void testFindVariableValues() {
        System.out.printf("%nTesting Lapr1_2015.findVariableValues...%n");
        float[][] matrix = {{1, 2, 3, 4, 5}, {-1, -4, 2, 2.4F, 5}, {-5, 5.2F, 3, 1, 4}};
        int nrVar = 2;
        String[] variableNames = {"Y1", "Y2", "X1", "X2", "SOL"};
        String expResult = "(X1, X2) = (3,00, 1,00)";
        String result = Lapr1_2015.findVariableValues(matrix, nrVar, variableNames);
        assertEquals(expResult, result);

        float[][] matrix2 = {{0, 1, 0, 1, -1, 0, 3}, {1, 0, 0.50F, -0.50F, 1, 0, 3},
        {0, 0, 1.50F, -2.50F, 2, 1, 4}, {0, 0, 3, 4, 0, 0, 48}};
        nrVar = 2;
        String[] variableNames2 = {"X1", "X2", "X3", "S1", "S2", "S3", "SOL"};
        expResult = "(X1, X2, X3, S1, S2, S3) = (3,00, 3,00, 0,00, 0,00, 0,00, 4,00)";
        result = Lapr1_2015.findVariableValues(matrix2, nrVar, variableNames2);
        assertEquals(expResult, result);

        System.out.printf("End of testing Lapr1_2015.findVariableValues...%n");
    }

    /**
     * Test of formatZValue method, of class Lapr1_2015.
     */
    @Test
    public void testGetZValue() {
        System.out.printf("%nTesting Lapr1_2015.formatZValue...%n");
        float[][] matrix = {
            {-1,2,-3},
            {-3,4.5F,1}};
        float expResult = 1;
        float result = Lapr1_2015.getZValue(matrix);
        assertEquals(expResult, result,0.0F);
        
        System.out.printf("End of testing Lapr1_2015.formatZValue...%n");
    }

    /**
     * Test of maximizeFunction method, of class Lapr1_2015.
     */
    /*@Test
    public void testMaximizeFunction() {
        System.out.printf("%nTesting Lapr1_2015.maximizeFunction...%n");
        float[][] matrix = {
            {1,0,4},
            {0,2,12},
            {3,2,18},
            {3,5,0}
        };
        String outputFileName = "outputfiletest.txt";
        int nrVar = 2;
        String problem = "Z = 3X1 + 5X2" + Lapr1_2015.LINE_SEPARATOR +
        "X1 + 0X2 <= 4" + Lapr1_2015.LINE_SEPARATOR +
        "0X1 + 2X2 <= 12" + Lapr1_2015.LINE_SEPARATOR +
        "3X1 + 2X2 <= 18";
        float[][] expResult = {
            {0,0,1,0.33F,-0.33F,2},
            {0,1,0,0,0.50F,0,6},
            {1,0,0,-0.33F,0.33F,2},
            {0,0,0,1.5F,1,36}
        };
        float[][] result = Lapr1_2015.maximizeFunction(matrix, outputFileName, nrVar, problem);
        assertArrayEquals(expResult, result);
        
        System.out.printf("End of testing Lapr1_2015.maximizeFunction...%n");
    }*/

    /**
     * Test of minimizeFunction method, of class Lapr1_2015.
     */
    /*@Test
    public void testMinimizeFunction() {
        System.out.printf("%nTesting Lapr1_2015.minimizeFunction...%n");
        float[][] matrix = {
            {50,10,150},
            {30,30,210},
            {0.05F, 0.03F, 0}
        };
        String outputFileName = "outputfiletest.txt";
        int nrVar = 2;
        String problem = "Z = 0.05X1 + 0.03X2" + Lapr1_2015.LINE_SEPARATOR+
        "50X1 + 10X2 >= 150" + Lapr1_2015.LINE_SEPARATOR+
        "30X1 + 30X2 >= 210";
        float[][] expResult = {
            {1,0,0.03F,-0.03F,0},
            {0,1,-0.01F,0.04F,0},
            {0,0,2,5,0.25F}
        };
        float[][] result = Lapr1_2015.minimizeFunction(matrix, outputFileName, nrVar, problem);
        assertArrayEquals(expResult, result);
        
        System.out.printf("End of testing Lapr1_2015.minimizeFunction...%n");
    }*/

    /**
     * Test of getNonBasicVariableValue method, of class Lapr1_2015.
     */
    @Test
    public void testGetNonBasicVariableValue() {
        System.out.printf("%nTesting Lapr1_2015.getNonBasicVariableValue...%n");
        float[][] matrix = {
            {0,0,1,0.33F,-0.33F,2},
            {0,1,0,0,0.50F,0.6F},
            {1,0,0,-0.33F,0.33F,2},
            {0,0,0,1.5F,1,36}
        };
        String variable = "X1";
        int nrVariables = 2;
        String[] variables = {"X1","X2","S1","S2","S3","SOL"};
        float expResult = 2;
        float result = Lapr1_2015.getNonBasicVariableValue(matrix, variable, nrVariables, variables);
        assertEquals(expResult, result, 0.0);
        
        variable = "X2";
        expResult = 0.6F;
        result = Lapr1_2015.getNonBasicVariableValue(matrix, variable, nrVariables, variables);
        assertEquals(expResult, result, 0.0);
        
        System.out.printf("End of testing Lapr1_2015.getNonBasicVariableValue...%n");
    }

}
