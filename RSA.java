// for key generation
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
// for encryption and decryption
import javax.crypto.Cipher;
// for hashing
import java.security.MessageDigest;
// for signing
import java.security.Signature;

public class RSA {
    protected static KeyPair generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    protected static byte[] makeHashing(byte[] message) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        return messageDigest.digest(message); // 64 bytes
    }

    protected static byte[] makeSigning(PrivateKey privateKey, byte[] hashedValue) throws Exception {
        Signature signature = Signature.getInstance("SHA512withRSA");
        signature.initSign(privateKey);
        signature.update(hashedValue);
        byte[] signatureBytes = signature.sign();
        return signatureBytes; // 256 bytes
    }

    protected static byte[] encrypt(PublicKey publicKey, byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message);
    }

    protected static byte[] decrypt(PrivateKey privateKey, byte[] encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedMessage);
    }
    
    protected static byte[] makeSignedMessage(byte[] signature, byte[] message) {
        // Create signedMessage array
        byte[] signedMessage = new byte[signature.length + message.length];
        // Copy the content of the signature into the signedMessage
        System.arraycopy(signature, 0, signedMessage, 0, signature.length);
        // Copy the content of the message into the signedMessage
        System.arraycopy(message, 0, signedMessage, signature.length, message.length);
        return signedMessage;
    }

    protected static boolean makeVerification(byte[] hashedValue, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA512withRSA");
        verifier.initVerify(publicKey);
        verifier.update(hashedValue);
        return verifier.verify(signature);
    }

    protected static byte[] extractSignature(byte[] signedMessage) {
        byte[] signature = new byte[256];
        for(int i = 0; i < 256; i++) {
            signature[i] = signedMessage[i];
        }
        return signature;
    }

    protected static byte[] extractMessage(byte[] signedMessage) {
        byte[] message = new byte[signedMessage.length - 256];
        for(int i = 256; i < signedMessage.length; i++) {
            message[i-256] = signedMessage[i];
        }
        return message;
    }
}