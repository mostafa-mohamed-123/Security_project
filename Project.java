import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Project {
    static Sender sender = new Sender();
    static Receiver receiver = new Receiver();

    static Scanner scan = new Scanner(System.in);

    static String inputFilePath;
    static String rsaSignedFile = "RSA_signed_file.txt";
    static String rsaVerifiedFile = "RSA_verified_file.txt";
    static String rsaEncryptedFile = "RSA_encrypted_file.txt";
    static String rsaDecryptedFile = "RSA_decrypted_file.txt";
    static String aesDecryptedFile = "AES_decrypted_file.txt";
    static String aesEncryptedFile = "AES_encrypted_file.txt";
    
    static String mode;

    static byte[] messageBytes;

    public static void main(String[] args) {
        try {
            System.out.println("Enter path of the file to be encrypted: ");
            inputFilePath = scan.nextLine();
            
            // Read data from the file
            messageBytes = Files.readAllBytes(Paths.get(inputFilePath));

            System.out.println("Choose encryption technique: \n1) RSA encryption (max message size = 245 bytes)\n2) RSA signing\n3) AES encryption");
            String choice = scan.nextLine();
            
            if(choice.equals("1")) {
                mode = "file";
                RsaEncryption.makeRsaEncryption();
            }
            
            else if(choice.equals("2")) {
                mode = "file";
                RsaSigning.makeRsaSigning();
            }
            
            else if(choice.equals("3")) {
                mode = "key";
                AesEncryption.makeAesEncryption();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
