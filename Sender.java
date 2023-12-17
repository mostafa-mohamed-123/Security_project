// for handling files
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sender extends Peer {
    public void encryptFileUsingRsa() {
        try {            
            // encrypt message using RSA
            setEncryptedMessage(RSA.encrypt(getPeerPublicKey(), Project.messageBytes));
                
            // Write the encrypted data to a file
            Files.write(Paths.get(Project.rsaEncryptedFile), getEncryptedMessage());
            System.out.println("File is encrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptFileUsingEcb(byte[] fileData) {
        try {
            // Create an instance of AesFixedCipher
            AesCipherECB aesCipher = new AesCipherECB(getAesKey(), 128);

            // Encrypt the data
            setEncryptedMessage(aesCipher.encrypt(fileData).getBytes());

            // Write the encrypted data to a file
            Files.write(Paths.get(Project.aesEncryptedFile), getEncryptedMessage());
            System.out.println("File is encrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptFileUsingCbc(byte[] fileData) {
        try {
            // Create an instance of AesFixedCipher
            AesCipherCBC aesCipher = new AesCipherCBC(getAesKey(), getIvValue(), 128);

            // Encrypt the data
            setEncryptedMessage(aesCipher.encrypt(fileData).getBytes());

            // Write the encrypted data to a file
            Files.write(Paths.get(Project.aesDecryptedFile), getEncryptedMessage());
            System.out.println("File is encrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptAesKey() {
        try {
            setEncryptedAesKey(RSA.encrypt(getPeerPublicKey(), getAesKey().getBytes()));
            Files.write(Paths.get(Project.rsaEncryptedFile), getEncryptedAesKey());
            System.out.println("Key is encrypted successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signFile() {
        try {
            // hash the message
            byte[] hashedValue = RSA.makeHashing(Project.messageBytes);

            // sign hashed value
            setSignature(RSA.makeSigning(getPrivateKey(), hashedValue));

            // concatenate signature to the message
            setSignedMessage(RSA.makeSignedMessage(getSignature(), Project.messageBytes));
            
            // Write the signed data to a file
            Files.write(Paths.get(Project.rsaSignedFile), getSignedMessage());
            System.out.println("File is signed successfuly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}