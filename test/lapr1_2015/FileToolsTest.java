/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_2015;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
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
     * Test of getNumberOfLines method, of class FileTools.
     */
    @Test
    public void testGetNumberOfLines() {
        System.out.println("getNumberOfLines");
        File file = new File("filetest.txt");
        int expResult = 3;
        int result = FileTools.getNumberOfLines(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of getObjectiveFunction method, of class FileTools.
     */
    @Test
    public void testGetObjectiveFunction() {
        System.out.printf("%nTesting FileTools.getObjectiveFunction...%n");
        String line = "Z=3X1+2X2";
        int nrColumns = 4;
        float[] expResult = {-3,-2,0,0};
        float[] result = FileTools.getObjectiveFunction(line, nrColumns);
        assertArrayEquals(expResult, result, 0.0F);
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
        int nrColumns = 4;
        float[] expResult = {3,-2,1,4};
        float[] result = FileTools.getRestriction(line, matrixLine, nrColumns);
        assertArrayEquals(expResult, result, 0.0F);
        
        line = "-2X2<=5";
        matrixLine = 1;
        nrColumns = 4;
        float[] expResult2 = {0,-2,1,5};
        float[] result2 = FileTools.getRestriction(line, matrixLine, nrColumns);
        assertArrayEquals(expResult2, result2, 0.0F);
        
        line = "-2X1<=7";
        matrixLine = 1;
        nrColumns = 4;
        float[] expResult3 = {-2,0,1,7};
        float[] result3 = FileTools.getRestriction(line, matrixLine, nrColumns);
        assertArrayEquals(expResult3, result3, 0.0F);
        System.out.printf("End of testing FileTools.getRestriction...%n");
    }

    /**
     * Test of readLine method, of class FileTools.
     */
    @Test
    public void testReadLine() {
        System.out.printf("%nTesting FileTools.readLine...%n");
        String line = "3X1 + 2X2 <= 3";
        float[][] matrix = new float[3][4];
        int matrixLine = 2;
        int expResult = 3;
        int result = FileTools.readLine(line, matrix, matrixLine);
        assertEquals(expResult, result);
        
        line = "3X1 + 2X2 <= 3";
        float[][] matrix2 = new float[3][5];
        matrixLine = 0;
        expResult = 1;
        result = FileTools.readLine(line, matrix2, matrixLine);
        assertEquals(expResult, result);
        System.out.printf("End of testing FileTools.readLine...%n");
    }

    /**
     * Test of isValid method, of class FileTools.
     */
    @Test
    public void testIsValid() {
        System.out.printf("%nTesting FileTools.isValid...%n");
        File file = null;
        boolean expResult = false;
        boolean result = FileTools.isValid(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        System.out.printf("End of testing FileTools.isValid...%n");
    }

    /**
     * Test of saveToFile method, of class FileTools.
     */
    @Test
    public void testSaveToFile() {
        System.out.printf("%nTesting FileTools.saveToFile...%n");
        String fileName = "";
        String data = "";
        boolean expResult = false;
        boolean result = FileTools.saveToFile(fileName, data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        System.out.printf("End of testing FileTools.saveToFile...%n");
    }

    /**
     * Test of getFileData method, of class FileTools.
     */
    @Test
    public void testGetFileData() {
        System.out.println("getFileData");
        File file = null;
        String expResult = "";
        String result = FileTools.getFileData(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
