package lapr1_2015;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Group 2
 */
public class Lapr1_2015Test {

    public Lapr1_2015Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

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
        float[][] result = Lapr1_2015.applySimplexMethod(matrix, outputFileName, nrVar, inputFileData, variables);
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

}
