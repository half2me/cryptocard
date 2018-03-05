package applet;

import javacard.security.KeyBuilder;
import javacard.security.KeyPair;

public class RSACryptoCard extends CryptoCard {

    KeyPair newKey() {
        return new KeyPair(KeyPair.ALG_RSA, KeyBuilder.LENGTH_RSA_1024);
        //signature = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
    }

    /**
     * Installs this applet.
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    public static void install(byte[] bArray, short bOffset, byte bLength){
        new RSACryptoCard();
    }
}
