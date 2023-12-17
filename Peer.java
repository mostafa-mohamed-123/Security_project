import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Peer {
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PublicKey peerPublicKey;
    
    private String aesKey;
    private static String ivValue;

    private byte[] encryptedAesKey;
    private byte[] encryptedMessage;
    private byte[] signature;
    private byte[] signedMessage;

    public void generateKey() {
        try {
            this.keyPair = RSA.generateKeys();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPeerPublicKey(PublicKey peerPublicKey) {
        this.peerPublicKey = peerPublicKey;
    }

    public PublicKey getPeerPublicKey() {
        return this.peerPublicKey;
    }

    public void setEncryptedAesKey(byte[] encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
    }

    public byte[] getEncryptedAesKey() {
        return this.encryptedAesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public void setSignedMessage(byte[] signedMessage) {
        this.signedMessage = signedMessage;
    }

    public byte[] getSignedMessage() {
        return this.signedMessage;
    }

    public void setEncryptedMessage(byte[] encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }
    
    public byte[] getEncryptedMessage() {
        return this.encryptedMessage;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
    
    public byte[] getSignature() {
        return this.signature;
    }

    public static void setIvValue(String ivValue1) {
        ivValue = ivValue1;
    }
    
    public static String getIvValue() {
        return ivValue;
    }
}
