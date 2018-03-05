package applet;

import javacard.security.KeyBuilder;
import javacard.security.KeyPair;

public class ECCryptoCard extends CryptoCard {

    KeyPair newKey() {
        return new KeyPair(KeyPair.ALG_EC_FP, KeyBuilder.LENGTH_EC_F2M_163);
        //signature = Signature.getInstance(Signature.ALG_ECDSA_SHA_256, false);
    }

    /**
     * Installs this applet.
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    public static void install(byte[] bArray, short bOffset, byte bLength){
        new ECCryptoCard();
    }
}
