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
        float[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        String expResult = "Z = 4,00";
        String result = Lapr1_2015.findZValue(matrix);
        assertEquals(expResult, result);
        
        float[][] matrix2 = {{1,2,3,-32}};
        expResult = "Z = -32,00";
        result = Lapr1_2015.findZValue(matrix2);
        assertEquals(expResult, result);
        System.out.printf("End of testing Lapr1_2015.findZValue...%n");
    }

    /**
     * Test of getOutputLineFormat method, of class Lapr1_2015.
     */
    @Test
    public void testGetOutputLineFormat() {
        System.out.println("%nTesting Lapr1_2015.getOutputLineFormat...%n");
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
    
}
