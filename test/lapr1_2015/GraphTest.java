package lapr1_2015;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class GraphTest {

    /**
     * Test of makeGraph method, of class Graph.
     */
    @Test
    public void testMakeGraph() {
        System.out.printf("%nTesting Graph.makeGraph...%n");
        String title = "GraphTest";
        String outputFileName = "graphtest.png";
        String terminal = "png";
        String equations = "0 = 2X1 + 3x2" + Lapr1_2015.LINE_SEPARATOR +
                "2x1 <= 3";
        float pointX = 3;
        float pointY = 2;
        boolean expResult = true;
        boolean result = Graph.makeGraph(title, outputFileName, terminal, equations, pointX, pointY);
        assertEquals(expResult, result);
        
        System.out.printf("End of testing Graph.makeGraph...%n");
    }
    
}
