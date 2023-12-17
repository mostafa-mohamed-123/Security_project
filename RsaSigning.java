import java.nio.file.Files;
import java.nio.file.Paths;

public class RsaSigning {
    public RsaSigning() {
        
    }

    protected static void makeRsaSigning() {
        try {
            System.out.println("Press 'Enter' to generate RSA key for sender");
            Project.scan.nextLine();
            Project.sender.generateKey();

            System.out.println("Press 'Enter' to send sender public key to receiver");
            Project.scan.nextLine();
            Project.receiver.setPeerPublicKey(Project.sender.getPublicKey());

            System.out.println("Press 'Enter' to sign the file");
            Project.scan.nextLine();
            Project.sender.signFile();
            
            if(Project.mode.equals("file")) {
                System.out.println("Press 'Enter' to send the signed file");
                Project.scan.nextLine();
                Project.receiver.setSignedMessage(Project.sender.getSignedMessage());

                verifyFunction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void verifyFunction() {
        try {
            System.out.println("Press 'Enter' to verify the file");
            Project.scan.nextLine();
            if(Project.receiver.verifyFile()) {
                Files.write(Paths.get(Project.rsaVerifiedFile), Project.receiver.getMessage());
                System.out.println("File is verified successfuly");
            } else {
                System.out.println("Verification is failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
