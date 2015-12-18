/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_2015;

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
        System.out.printf("%nTesting MathTools.findZValue...%n");
        float[][] matrix = null;
        String expResult = "";
        String result = Lapr1_2015.findZValue(matrix);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        System.out.printf("End of testing MathTools.findZValue...%n");
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
    
}
