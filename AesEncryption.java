import java.nio.file.Files;
import java.nio.file.Paths;

public class AesEncryption {
    public AesEncryption() {

    }

    protected static void makeAesEncryption() {
        try {
            // Enter The Key
            System.out.println("Enter Key: ");
            Project.sender.setAesKey(Project.scan.nextLine());

            System.out.println("Firstly sender and receiver will exchange the key");
            RsaEncryption.makeRsaEncryption();

            //System.out.println("Secondly sender append his signature to the message");
            //RsaSigning.makeRsaSigning();

            // Read data from the file
            //byte[] fileData = Files.readAllBytes(Paths.get(Project.rsaSignedFile));
            //Project.messageBytes = Files.readAllBytes(Paths.get(Project.rsaSignedFile));
            
            System.out.println("Now the file is ready to be encrypted");
            System.out.println("Choose AES mode:\n1) ECB\n2) CBC");
            String aesMode = Project.scan.nextLine();
            
            System.out.println("Press 'Enter' to encrypt the file");
            Project.scan.nextLine();
            
            if(aesMode.equals("1")) {
                Project.sender.encryptFileUsingEcb(Project.messageBytes);
            }

            else if(aesMode.equals("2")) {
                System.out.println("Enter iv value: ");
                Peer.setIvValue(Project.scan.nextLine());
                Project.sender.encryptFileUsingCbc(Project.messageBytes);
            }

            System.out.println("Press 'Enter' to send the encrypted file");
            Project.scan.nextLine();
            Project.receiver.setEncryptedMessage(Project.sender.getEncryptedMessage());


            System.out.println("Press 'Enter' to decrypt the file");
            Project.scan.nextLine();

            if(aesMode.equals("1")) {
                Project.receiver.decryptFileUsingEcb();
            }

            else if(aesMode.equals("2")) {
                Project.receiver.decryptFileUsingCbc();
            }

            //RsaSigning.verifyFunction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
