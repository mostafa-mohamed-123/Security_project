// for handling files
import java.nio.file.Files;
import java.nio.file.Paths;

public class Receiver extends Peer {
    private byte[] decryptedMessage;
    private byte[] message;

    public byte[] getDecryptedMessage() {
        return this.decryptedMessage;
    }

    public byte[] getMessage() {
        return this.message;
    }

    public void decryptFileUsingRsa() {
        try {
            // decrypt message using RSA
            this.decryptedMessage = RSA.decrypt(getPrivateKey(), getEncryptedMessage());

            // Write the encrypted data to a file
            Files.write(Paths.get(Project.rsaDecryptedFile), decryptedMessage);
            System.out.println("File is decrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptFileUsingEcb() {
        try {
            // Create an instance of AesFixedCipher
            AesCipherECB aesCipher = new AesCipherECB(getAesKey(), 128);

            // Decrypt the data
            this.decryptedMessage = aesCipher.decrypt(getEncryptedMessage()).getBytes();
            setSignedMessage(this.decryptedMessage);

            // Write the decrypted data to a file
            Files.write(Paths.get(Project.aesDecryptedFile), this.decryptedMessage);
            System.out.println("File is decrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public void decryptFileUsingCbc() {
        try {
            AesCipherCBC aesCipher = new AesCipherCBC(getAesKey(), Peer.getIvValue(), 128);

            // Decrypt the data
            this.decryptedMessage = aesCipher.decrypt(getEncryptedMessage()).getBytes();
            setSignedMessage(this.decryptedMessage);

            // Write the decrypted data to a file
            Files.write(Paths.get(Project.aesDecryptedFile), this.decryptedMessage);
            System.out.println("File is decrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public void decryptAesKey() {
        try {
            setAesKey(new String(RSA.decrypt(getPrivateKey(), getEncryptedAesKey())));
            Files.write(Paths.get(Project.rsaDecryptedFile), getAesKey().getBytes());
            System.out.println("Key is decrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyFile() {
        boolean isVerified = false;
        try {
            setSignature(RSA.extractSignature(getSignedMessage()));
            this.message = RSA.extractMessage(getSignedMessage());
            byte[] hashedValue = RSA.makeHashing(this.message);
            isVerified = RSA.makeVerification(hashedValue, getSignature(), getPeerPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVerified;
    }
}