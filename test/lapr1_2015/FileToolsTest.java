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
public class FileToolsTest {

    public FileToolsTest() {
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
     * Test of getObjectiveFunction method, of class FileTools.
     */
    @Test
    public void testGetObjectiveFunction() {
        System.out.printf("%nTesting FileTools.getObjectiveFunction...%n");
        String line = "Z=3X1+2X2";
        int nrVariables = 2;
        float[] expResult = {3, 2, 0};
        float[] result = FileTools.getObjectiveFunction(line, nrVariables);
        assertArrayEquals(expResult, result, 0.0F);

        line = "Z=5X1";
        nrVariables = 2;
        float[] expResult2 = {5, 0, 0};
        result = FileTools.getObjectiveFunction(line, nrVariables);
        assertArrayEquals(expResult2, result, 0.0F);

        line = "Z=-X2";
        nrVariables = 2;
        float[] expResult3 = {0, -1, 0};
        result = FileTools.getObjectiveFunction(line, nrVariables);
        assertArrayEquals(expResult3, result, 0.0F);

        System.out.printf("End of testing FileTools.getObjectiveFunction...%n");
    }

    /**
     * Test of getRestriction method, of class FileTools.
     */
    @Test
    public void testGetRestriction() {
        System.out.printf("%nTesting FileTools.getRestriction...%n");
        String line = "3X1-2X2<=4";
        int matrixLine = 1;
        int nrVariables = 2;
        float[] expResult = {3, -2, 4};
        float[] result = FileTools.getRestriction(line, matrixLine, nrVariables);
        assertArrayEquals(expResult, result, 0.0F);

        line = "-2X2<=5";
        matrixLine = 2;
        float[] expResult2 = {0, -2, 5};
        float[] result2 = FileTools.getRestriction(line, matrixLine, nrVariables);
        assertArrayEquals(expResult2, result2, 0.0F);

        line = "-2X1<=7";
        matrixLine = 1;
        float[] expResult3 = {-2, 0, 7};
        float[] result3 = FileTools.getRestriction(line, matrixLine, nrVariables);
        assertArrayEquals(expResult3, result3, 0.0F);

        line = "-2X1 +3X1 +5.20X2 -3X3<=7/2";
        matrixLine = 1;
        nrVariables = 3;
        float[] expResult4 = {1, 5.2F, -3, 3.5F};
        float[] result4 = FileTools.getRestriction(line, matrixLine, nrVariables);
        assertArrayEquals(expResult4, result4, 0.0F);
        System.out.printf("End of testing FileTools.getRestriction...%n");
    }

    /**
     * Test of readLine method, of class FileTools.
     */
    @Test
    public void testFillLine() {
        System.out.printf("%nTesting FileTools.fillLine...%n");
        String line = "3X1 + 2X2 <= 3";
        float[][] matrix = new float[3][4];
        int matrixLine = 2;
        int expResult = 3;
        int nrVariables = 2;
        int result = FileTools.fillLine(line, matrix, matrixLine, nrVariables);
        assertEquals(expResult, result);

        line = "3X1 + 2X2 <= 3";
        float[][] matrix2 = new float[3][5];
        matrixLine = 0;
        expResult = 1;
        result = FileTools.fillLine(line, matrix2, matrixLine, nrVariables);
        assertEquals(expResult, result);
        System.out.printf("End of testing FileTools.fillLine...%n");
    }

    /**
     * Test of isValid method, of class FileTools.
     */
    @Test
    public void testIsValid() {
        System.out.printf("%nTesting FileTools.isValid...%n");
        String lineSeparator = Lapr1_2015.LINE_SEPARATOR;
        String errorLog = "errorstest.txt";
        String fileData = "Z = 8X1 + 8X2 + X3" + lineSeparator
                + "2X1 + 2X2 + X3 <= 12" + lineSeparator
                + "2X1 + X2 + X3 <= 9" + lineSeparator
                + "X1 + 3X2 + 2X3 <= 16";
        boolean expResult = true;
        boolean result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        
        fileData = "Z = 8.X1 + 8X2 + X3" + lineSeparator;
        expResult = false;
        result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        
        fileData = "2X1 + 2X2 + X3 <= 12" + lineSeparator
                + "Z = 8X1 + 8X2 + X3" + lineSeparator;
        expResult = false;
        result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        
        fileData = "2X1 + 2X2 + X3 <= 12" + lineSeparator
                + "Z = 8X1 + 8X2 +   X3" + lineSeparator;
        expResult = false;
        result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        
        fileData = "Z = 8X1 + 8X2 + X3" + lineSeparator
                + "2X1 + 2X2 + X3 <= 12" + lineSeparator
                + "2X1 + X2 + X3 >= 9" + lineSeparator
                + "X1 + 3X2 + 2X3 >= 16";
        expResult = false;
        result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        
        fileData = "Z = 8x1 + 8x2 + x3" + lineSeparator
                + "2x1 + 2x2 + x3 <= 12" + lineSeparator
                + "2x1 + x2 + x3 <= 9" + lineSeparator
                + "x1 + 3x2 + 2x3 <= 16";
        expResult = true;
        result = FileTools.isValid(fileData,errorLog);
        assertEquals(expResult, result);
        System.out.printf("End of testing FileTools.isValid...%n");
    }

    /**
     * Test of saveToFile method, of class FileTools.
     */
    @Test
    public void testSaveToFile() {
        System.out.printf("%nTesting FileTools.saveToFile...%n");
        String fileName = "outputfiletest.txt";
        String data = "Test data.\r\nTest data 2.";
        boolean expResult = true;
        boolean result = FileTools.saveToFile(fileName, data);
        assertEquals(expResult, result);
        System.out.printf("End of testing FileTools.saveToFile...%n");
    }

    /**
     * Test of getNumberOfVariables method, of class FileTools.
     */
    @Test
    public void testGetNumberOfVariables() {
        System.out.printf("%nTesting FileTools.getNumberOfVariables...%n");
        String lineSeparator = Lapr1_2015.LINE_SEPARATOR;

        String problem = "Z=X1-2X2" + lineSeparator + "3X2<=1";
        int expResult = 2;
        int result = FileTools.getNumberOfVariables(problem);
        assertEquals(expResult, result);

        problem = "Z=X1" + lineSeparator + "3X2<=1";
        expResult = 1;
        result = FileTools.getNumberOfVariables(problem);
        assertEquals(expResult, result);

        problem = "Z=" + lineSeparator + "3X2<=1";
        expResult = 0;
        result = FileTools.getNumberOfVariables(problem);
        assertEquals(expResult, result);

        problem = "Z=3X1 + 2X1" + lineSeparator + "3X2<=1";
        expResult = 1;
        result = FileTools.getNumberOfVariables(problem);
        assertEquals(expResult, result);
        System.out.printf("End of testing FileTools.getNumberOfVariables...%n");
    }

    /**
     * Test of addBasicVariables method, of class FileTools.
     */
    @Test
    public void testAddBasicVariables() {
        System.out.printf("%nTesting FileTools.addBasicVariables...%n");
        float[][] matrix = {{1, 2, 4}, {4, 5, 7}, {8, 9, 11}};
        float[][] expResult = {{1, 2, 1, 0, 4}, {4, 5, 0, 1, 7}, {8, 9, 0, 0, 11}};
        float[][] result = FileTools.addBasicVariables(matrix);
        assertArrayEquals(expResult, result);

        float[][] matrix2 = {{1, 2, 4, 10, 2.5F}, {4, 5, 7, 1, 5.4F}, {8, 9, 11, 3, 2.1F}, {4, 24, 1, 3, 1.1F}};
        float[][] expResult2 = {
            {1, 2, 4, 10, 1, 0, 0, 2.5F},
            {4, 5, 7, 1, 0, 1, 0, 5.4F},
            {8, 9, 11, 3, 0, 0, 1, 2.1F},
            {4, 24, 1, 3, 0, 0, 0, 1.1F}
        };
        float[][] result2 = FileTools.addBasicVariables(matrix2);
        assertArrayEquals(expResult2, result2);
        System.out.printf("End of testing FileTools.addBasicVariables...%n");
    }

    /**
     * Test of fillMatrixWithNonBasicVariables method, of class FileTools.
     */
    @Test
    public void testFillMatrixWithNonBasicVariables() {
        System.out.printf("%nTesting FileTools.fillMatrixWithNonBasicVariables...%n");
        String lineSeparator = Lapr1_2015.LINE_SEPARATOR;
        String problem = "Z=3X1 + 4/2X2" + lineSeparator
                + "3X1 >= 2" + lineSeparator
                + "2X2 >= 10" + lineSeparator
                + "3.25X1 - 4/2X2 >= 1.50";
        int nrVariables = 2;
        float[][] expResult = {{3, 0, 2}, {0, 2, 10}, {3.25F, -2, 1.50F}, {3, 2, 0}};
        float[][] result = FileTools.fillMatrixWithNonBasicVariables(problem, nrVariables);
        assertArrayEquals(expResult, result);

        problem = "Z=3X1 + 4/2X2" + lineSeparator
                + "3X1 +X1>= 2" + lineSeparator
                + "2X2 -X2>= 10";

        float[][] expResult2 = {{4, 0, 2}, {0, 1, 10}, {3, 2, 0}};
        float[][] result2 = FileTools.fillMatrixWithNonBasicVariables(problem, nrVariables);
        assertArrayEquals(expResult2, result2);
        System.out.printf("End of testing FileTools.fillMatrixWithNonBasicVariables...%n");
    }

}
