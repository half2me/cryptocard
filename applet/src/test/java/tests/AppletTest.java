package tests;

import applet.CryptoCard;
import applet.ECCryptoCard;
import com.licel.jcardsim.base.Simulator;
import javacard.framework.AID;
import org.junit.Assert;
import org.testng.annotations.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;

/**
 * Example test class for the applet
 * Note: If simulator cannot be started try adding "-noverify" JVM parameter
 *
 * @author xsvenda, Dusan Klinec (ph4r05)
 */
public class AppletTest {

    private Simulator simulator;
    private byte[] appletAIDBytes;
    private AID appletAID;

    public AppletTest() {
        this.simulator = new Simulator();
        this.appletAIDBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        this.appletAID = new AID(this.appletAIDBytes, (short) 0, (byte) this.appletAIDBytes.length);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {

    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        this.simulator.installApplet(appletAID, ECCryptoCard.class);
        simulator.selectApplet(appletAID);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        simulator.reset();
    }

    // Example test
    @Test
    public void testGetPubKey() {
        byte[] resp = simulator.transmitCommand((new CommandAPDU(0x00, 0x00, 0x00, 0x00)).getBytes());
        byte[] resp2 = simulator.transmitCommand((new CommandAPDU(0x00, 0x00, 0x00, 0x00)).getBytes());

        assert Arrays.equals(resp, resp2);
    }
}
