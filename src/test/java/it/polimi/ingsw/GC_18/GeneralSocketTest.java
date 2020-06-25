package it.polimi.ingsw.GC_18;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests application functionalities using socket connections
 * and sending commands of the file /resources/test/commands_log_socket0_socket1.txt
 */
public class GeneralSocketTest {

    private static final String[] clientsUnderTestNames = new String[] { "socket0", "socket1" };

    @Test
    public void testSocketClient() {
        assertTrue(GeneralMocking.doGeneralMocking(clientsUnderTestNames));
    }

}
