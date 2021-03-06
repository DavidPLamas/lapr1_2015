package lapr1_2015;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Group 2
 */
public class MathToolsTest {

    /**
     * Test of getVariableCoeficient method, of class MathTools.
     */
    @Test
    public void testGetVariableCoefficient() {
        System.out.printf("%nTesting MathTools.getVariableCoeficient...%n");
        String variable = "-3X1";
        float expResult = -3;
        float result = MathTools.getVariableCoefficient(variable);
        assertEquals(expResult, result, 0.0F);

        variable = "X1";
        expResult = 1;
        result = MathTools.getVariableCoefficient(variable);
        assertEquals(expResult, result, 0.0F);

        variable = "-X2";
        expResult = -1;
        result = MathTools.getVariableCoefficient(variable);
        assertEquals(expResult, result, 0.0F);

        variable = "100X5";
        expResult = 100;
        result = MathTools.getVariableCoefficient(variable);
        assertEquals(expResult, result, 0.0F);
        System.out.printf("End of testing MathTools.getVariableCoeficient...%n");
    }

    /**
     * Test of calculateSimetric method, of class MathTools.
     */
    @Test
    public void testCalculateSymmetric() {
        System.out.printf("%nTesting MathTools.calculateSimetric...%n");
        float num = -3;
        float expResult = 3;
        float result = MathTools.calculateSymmetric(num);
        assertEquals(expResult, result, 0.0F);

        num = -6;
        expResult = 6;
        result = MathTools.calculateSymmetric(num);
        assertEquals(expResult, result, 0.0F);

        num = 10;
        expResult = -10;
        result = MathTools.calculateSymmetric(num);
        assertEquals(expResult, result, 0.0F);

        num = 1;
        expResult = -1;
        result = MathTools.calculateSymmetric(num);
        assertEquals(expResult, result, 0.0F);
        System.out.printf("End of testing MathTools.calculateSimetric...%n");
    }

    /**
     * Test of getXIndex method, of class MathTools.
     */
    @Test
    public void testGetXIndex() {
        System.out.printf("%nTesting MathTools.getXIndex...%n");
        String variable = "-3X1";
        int expResult = 1;
        int result = MathTools.getXIndex(variable);
        assertEquals(expResult, result);

        variable = "X2";
        expResult = 2;
        result = MathTools.getXIndex(variable);
        assertEquals(expResult, result);

        variable = "X10";
        expResult = 10;
        result = MathTools.getXIndex(variable);
        assertEquals(expResult, result);

        variable = "2";
        expResult = -1;
        result = MathTools.getXIndex(variable);
        assertEquals(expResult, result);
        System.out.printf("End of testing MathTools.getXIndex...%n");
    }

    /**
     * Test of multiplyLineByScalar method, of class MathTools.
     */
    @Test
    public void testMultiplyLineByScalar() {
        System.out.printf("%nTesting MathTools.multiplyLineByScalar...%n");
        // 1 2 3
        // 4 5 6
        // 7 8 9
        float[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int lineIndex = 1;
        float scalar = 3;
        float[] expResult = {12, 15, 18};
        float[] result = MathTools.multiplyLineByScalar(matrix, lineIndex, scalar);
        assertArrayEquals(expResult, result, 0.0F);

        lineIndex = 2;
        scalar = 5;
        float[] expResult2 = {35, 40, 45};
        result = MathTools.multiplyLineByScalar(matrix, lineIndex, scalar);
        assertArrayEquals(expResult2, result, 0.0F);

        lineIndex = 0;
        scalar = 5.5F;
        float[] expResult3 = {5.5F, 11, 16.5F};
        result = MathTools.multiplyLineByScalar(matrix, lineIndex, scalar);
        assertArrayEquals(expResult3, result, 0.0F);
        System.out.printf("End of testing MathTools.multiplyLineByScalar...%n");
    }

    /**
     * Test of addTwoLinesWithScalar method, of class MathTools.
     */
    @Test
    public void testAddTwoLinesWithScalar() {
        System.out.printf("%nTesting MathTools.addTwoLinesWithScalar...%n");
        // 1  1 3
        // 3 -1 5
        // 2 -1 3
        float[][] matrix = {{1, 1, 3}, {3, -1, 5}, {2, -1, 3}};
        int lineIndex1 = 0;
        int lineIndex2 = 1;
        float scalar = 3;
        float[] expResult = {10, -2, 18};
        float[] result = MathTools.addTwoLinesWithScalar(matrix, lineIndex1, lineIndex2, scalar);
        assertArrayEquals(expResult, result, 0.0F);
        System.out.printf("End of testing MathTools.addTwoLinesWithScalar...%n");
    }

    /**
     * Test of findPivotColumn method, of class MathTools.
     */
    @Test
    public void testFindPivotColumn() {
        System.out.printf("%nTesting MathTools.findPivotColumn...%n");
        float[][] matrix = {{-1, -5, 0, 0, 3}, {3, -1, 5, 3, 2}, {2, -1, 3, 5, 8}};
        int expResult = 1;
        int result = MathTools.findPivotColumn(matrix);
        assertEquals(expResult, result);

        float[][] matrix2 = {{-1, 5, 0, 0, 3}, {3, -1, 5, 3, 2}, {-2, -1, 3, 5, 8}};
        expResult = 0;
        result = MathTools.findPivotColumn(matrix2);
        assertEquals(expResult, result);

        float[][] matrix3 = {{1, 5, 0, 0, 3}, {3, -1, 5, 3, 2}, {2, 1, 3, 5, -8}};
        expResult = -1;
        result = MathTools.findPivotColumn(matrix3);
        assertEquals(expResult, result);
        System.out.printf("End of testing MathTools.findPivotColumn...%n");
    }

    /**
     * Test of findPivotLine method, of class MathTools.
     */
    @Test
    public void testFindPivotLine() {
        System.out.printf("%nTesting MathTools.findPivotLine...%n");
        // -1 -5  0  0  3
        // 3  -1  5  3  2
        // 2  -1  3  5  8
        float[][] matrix = {{-1, -5, 0, 0, 3}, {3, -1, 5, 3, 2}, {2, -1, 3, 5, 8}};
        int column = 1;
        int expResult = -1;
        int result = MathTools.findPivotLine(matrix, column);
        assertEquals(expResult, result);

        column = 0;
        expResult = 1;
        result = MathTools.findPivotLine(matrix, column);
        assertEquals(expResult, result);

        column = 2;
        expResult = 1;
        result = MathTools.findPivotLine(matrix, column);
        assertEquals(expResult, result);

        column = 3;
        expResult = 1;
        result = MathTools.findPivotLine(matrix, column);
        assertEquals(expResult, result);
        System.out.printf("End of testing MathTools.findPivotLine...%n");
    }

    /**
     * Test of transposeMatrix method, of class MathTools.
     */
    @Test
    public void testTransposeMatrix() {
        System.out.printf("%nTesting MathTools.transposeMatrix...%n");
        float[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        float[][] expResult = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        float[][] result = MathTools.transposeMatrix(matrix);
        assertArrayEquals(expResult, result);
        System.out.printf("End of testing MathTools.transposeMatrix...%n");
    }

    /**
     * Test of parseToFloat method, of class MathTools.
     */
    @Test
    public void testParseToFloat() {
        System.out.printf("%nTesting MathTools.parseToFloat...%n");
        String number = "2";
        float expResult = 2F;
        float result = MathTools.parseToFloat(number);
        assertEquals(expResult, result, 0.0);

        number = "2.2";
        expResult = 2.2F;
        result = MathTools.parseToFloat(number);
        assertEquals(expResult, result, 0.0);

        number = "2/2";
        expResult = 1F;
        result = MathTools.parseToFloat(number);
        assertEquals(expResult, result, 0.0);

        number = "3/2";
        expResult = 1.5F;
        result = MathTools.parseToFloat(number);
        assertEquals(expResult, result, 0.0);

        number = "3/2";
        expResult = 1.5F;
        result = MathTools.parseToFloat(number);
        assertEquals(expResult, result, 0.0);
        System.out.printf("End of testing MathTools.parseToFloat...%n");
    }

}
