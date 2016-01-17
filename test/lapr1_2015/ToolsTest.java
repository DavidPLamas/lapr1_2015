package lapr1_2015;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Group 2
 */
public class ToolsTest {

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
        String separator = Lapr1_2015.LINE_SEPARATOR;
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
        System.out.printf("End of testing Tools.getNumberOfLines...%n");
    }

    /**
     * Test of getPositionOf method, of class Tools.
     */
    @Test
    public void testGetPositionOf() {
        System.out.printf("%nTesting Tools.getPositionOf...%n");
        
        String[] array = {"X1","X2"};
        String search = "X1";
        int expResult = 0;
        int result = Tools.getPositionOf(array, search);
        assertEquals(expResult, result);
        
        search = "X2";
        expResult = 1;
        result = Tools.getPositionOf(array, search);
        assertEquals(expResult, result);
        
        search = "X";
        expResult = -1;
        result = Tools.getPositionOf(array, search);
        assertEquals(expResult, result);
        
        search = "x1";
        expResult = 0;
        result = Tools.getPositionOf(array, search);
        assertEquals(expResult, result);
        System.out.printf("End of testing Tools.getPositionOf...%n");
    }

    /**
     * Test of encodeString method, of class Tools.
     */
    @Test
    public void testEncodeString() {
        System.out.printf("%nTesting Tools.encodeString...%n");
        
        String text = "Test text.txt";
        String expResult = "Test_texttxt";
        String result = Tools.encodeString(text);
        assertEquals(expResult, result);
        
        text = "Test text txt";
        expResult = "Test_text_txt";
        result = Tools.encodeString(text);
        assertEquals(expResult, result);
        
        System.out.printf("End of testing Tools.encodeString...%n");
    }

}
