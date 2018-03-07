package applet;

import javacard.framework.*;
import javacard.security.*;

public abstract class CryptoCard extends Applet implements ISO7816 {

    // Constants
    static final int SCRATCHPAD_SIZE = 256;

	// Key
	protected KeyPair kp;

	// Signature scratchpad
	private byte[] scratchpad;

	protected CryptoCard() {
		scratchpad = new byte[SCRATCHPAD_SIZE];
		kp = newKey();
        kp.genKeyPair();
		register();
	}

	abstract KeyPair newKey();

	/**
	 * Processes an incoming APDU.
	 * @see APDU
	 * @param apdu the incoming APDU
	 * @exception ISOException with the response bytes per ISO 7816-4
	 */
	public void process(APDU apdu) {
		byte buffer[] = apdu.getBuffer();

		if (this.selectingApplet()) { return; }  // APDU was just selecting our application

		switch (buffer[ISO7816.OFFSET_INS]) {
			case 0x00:
				sendPublicKey(apdu);
				return;
			case 0x01:
				// Sign data
				return;
			default:
				ISOException.throwIt (ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

	private void sendPublicKey(APDU apdu) {
        byte buffer[] = apdu.getBuffer();
        PublicKey pk = kp.getPublic();
        byte type = pk.getType();
        short size = pk.getSize();
    }
}
