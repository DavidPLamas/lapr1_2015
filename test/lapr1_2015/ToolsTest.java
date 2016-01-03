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
        System.out.printf("%nTesting Tools.removeSpaces...%n");
        String line = "Z = 3X1 + 5X2 ";
        String expResult = "Z=3X1+5X2";
        String result = Tools.removeSpaces(line);
        assertEquals(expResult, result);

        line = "2X2   <=  12  ";
        expResult = "2X2<=12";
        result = Tools.removeSpaces(line);
        assertEquals(expResult, result);
        System.out.printf("End of testing Tools.removeSpaces...%n");
    }

    /**
     * Test of printError method, of class Tools.
     */
    @Test
    public void testPrintError() {
        System.out.printf("%nTesting Tools.printError...%n");
        String message = "Testing print";
        boolean expResult = true;
        boolean result = Tools.printError(message);
        assertEquals(expResult, result);
        System.out.printf("End of testing Tools.printError...%n");
    }

    /**
     * Test of getNumberOfLines method, of class Tools.
     */
    @Test
    public void testGetNumberOfLines() {
        System.out.printf("%nTesting Tools.getNumberOfLines...%n");
        String separator = System.getProperty("line.separator");
        String text = "Test";
        int expResult = 1;
        int result = Tools.getNumberOfLines(text);
        assertEquals(expResult, result);

        text = "Test" + separator;
        expResult = 1;
        result = Tools.getNumberOfLines(text);
        assertEquals(expResult, result);

        text = "Test" + separator + "test2";
        expResult = 2;
        result = Tools.getNumberOfLines(text);
        assertEquals(expResult, result);

        text = "";
        expResult = 0;
        result = Tools.getNumberOfLines(text);
        assertEquals(expResult, result);
    }

}
