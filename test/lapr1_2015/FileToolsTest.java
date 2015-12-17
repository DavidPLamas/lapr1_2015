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
        File file = null;
        int expResult = 0;
        int result = FileTools.getNumberOfLines(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getObjectiveFunction method, of class FileTools.
     */
    @Test
    public void testGetObjectiveFunction() {
        System.out.println("getObjectiveFunction");
        String line = "";
        int nrColumns = 0;
        float[] expResult = null;
        float[] result = FileTools.getObjectiveFunction(line, nrColumns);
        assertArrayEquals(expResult, result, 0.0F);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRestriction method, of class FileTools.
     */
    @Test
    public void testGetRestriction() {
        System.out.println("getRestriction");
        String line = "";
        int matrixLine = 0;
        int nrColumns = 0;
        float[] expResult = null;
        float[] result = FileTools.getRestriction(line, matrixLine, nrColumns);
        assertArrayEquals(expResult, result, 0.0F);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readLine method, of class FileTools.
     */
    @Test
    public void testReadLine() {
        System.out.println("readLine");
        String line = "";
        float[][] matrix = null;
        int matrixLine = 0;
        int expResult = 0;
        int result = FileTools.readLine(line, matrix, matrixLine);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class FileTools.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        File file = null;
        boolean expResult = false;
        boolean result = FileTools.isValid(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
