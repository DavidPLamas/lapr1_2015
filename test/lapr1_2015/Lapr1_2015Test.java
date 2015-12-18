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
     * Test of findZValue method, of class Lapr1_2015.
     */
    @Test
    public void testFindZValue() {
        System.out.printf("%nTesting Lapr1_2015.findZValue...%n");
        float[][] matrix = null;
        String expResult = "";
        String result = Lapr1_2015.findZValue(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        System.out.printf("End of testing Lapr1_2015.findZValue...%n");
    }
    
    /**
     * Test of main method, of class Lapr1_2015.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Lapr1_2015.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of simplexMethod method, of class Lapr1_2015.
     */
    @Test
    public void testSimplexMethod() {
        System.out.println("simplexMethod");
        float[][] matrix = null;
        File inputFile = null;
        String outputFileName = "";
        int nrVar = 0;
        String inputFileData = "";
        Lapr1_2015.simplexMethod(matrix, inputFile, outputFileName, nrVar, inputFileData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillMatrix method, of class Lapr1_2015.
     */
    @Test
    public void testFillMatrix() {
        System.out.println("fillMatrix");
        float[][] matrix = null;
        File file = null;
        Lapr1_2015.fillMatrix(matrix, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormattedMatrix method, of class Lapr1_2015.
     */
    @Test
    public void testGetFormattedMatrix() {
        System.out.println("getFormattedMatrix");
        float[][] matrix = null;
        int nrVar = 0;
        String expResult = "";
        String result = Lapr1_2015.getFormattedMatrix(matrix, nrVar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutputLineFormat method, of class Lapr1_2015.
     */
    @Test
    public void testGetOutputLineFormat() {
        System.out.println("getOutputLineFormat");
        int nrColumns = 0;
        String expResult = "";
        String result = Lapr1_2015.getOutputLineFormat(nrColumns);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutputMatrixHeader method, of class Lapr1_2015.
     */
    @Test
    public void testGetOutputMatrixHeader() {
        System.out.println("getOutputMatrixHeader");
        int nrColumns = 0;
        int nrVar = 0;
        String lineFormat = "";
        String[] lineArgs = null;
        String expResult = "";
        String result = Lapr1_2015.getOutputMatrixHeader(nrColumns, nrVar, lineFormat, lineArgs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVariableValues method, of class Lapr1_2015.
     */
    @Test
    public void testFindVariableValues() {
        System.out.println("findVariableValues");
        float[][] matrix = null;
        int nrVar = 0;
        String expResult = "";
        String result = Lapr1_2015.findVariableValues(matrix, nrVar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
