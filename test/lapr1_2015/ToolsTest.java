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
public class ToolsTest {
    
    public ToolsTest() {
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
     * Test of removeSpaces method, of class Tools.
     */
    @Test
    public void testRemoveSpaces() {
        System.out.println("removeSpaces");
        String line = "Z = 3X1 + 5X2 ";
        String expResult = "Z=3X1+5X2";
        String result = Tools.removeSpaces(line);
        assertEquals(expResult, result);
        
        line = "2X2   <=  12  ";
        expResult = "2X2<=12";
        result = Tools.removeSpaces(line);
        assertEquals(expResult, result);
    }

    /**
     * Test of printError method, of class Tools.
     */
    @Test
    public void testPrintError() {
        System.out.println("printError");
        String message = "Erro ao ler o ficheiro.";
        boolean expResult = true;
        boolean result = Tools.printError(message);
        assertEquals(expResult, result);
    }
    
}
