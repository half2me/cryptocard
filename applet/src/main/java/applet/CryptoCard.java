package applet;

import javacard.framework.*;
import javacard.security.*;

public abstract class CryptoCard extends Applet implements ISO7816 {

    // Constants
    static final int SCRATCHPAD_SIZE = 256;

	// PIN
    private int adminPin;
    private int pin;

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
				// Send pubkey
				return;
			case 0x02:
				// Sign data
				return;
			case 0x03:
				// Gen new key
				return;
			default:
				ISOException.throwIt (ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

	private void signData(APDU apdu) {
        signature.init(kp.getPrivate(), Signature.MODE_SIGN);
		byte[] buffer = apdu.getBuffer();
		//short dataLen = apdu.setIncomingAndReceive();

		short signLen = signature.sign(buffer, apdu.getOffsetCdata(), apdu.getIncomingLength(), scratchpad, (short) 0);

		Util.arrayCopyNonAtomic(scratchpad, (short) 0, buffer, (short) 0, signLen);
		apdu.setOutgoingAndSend((short) 0, signLen);
	}

	private void sendHello(APDU apdu) {
		byte buffer[] = apdu.getBuffer();
		Util.arrayCopyNonAtomic(hello, (short)0, buffer, (short)0, (short)hello.length);
		apdu.setOutgoingAndSend((short)0, (short)hello.length);
	}

	private void sendPubKeyExp(APDU apdu) {
		byte buffer[] = apdu.getBuffer();
		RSAPublicKey pub = (RSAPublicKey) kp.getPublic();
		short len = pub.getExponent(buffer, (short) 0);
		apdu.setOutgoingAndSend((short)0, len);
	}

	private void sendPubKeyMod(APDU apdu) {
		byte buffer[] = apdu.getBuffer();
		RSAPublicKey pub = (RSAPublicKey) kp.getPublic();
		short len = pub.getModulus(buffer, (short) 0);
		apdu.setOutgoingAndSend((short)0, len);
	}

	private void echo(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short len = apdu.getIncomingLength();
		Util.arrayFillNonAtomic(buffer, (short) 0, (short) 1, (byte) len);
		apdu.setOutgoingAndSend((short) 0, (short)1);
	}
}
