public class RsaEncryption {
    public RsaEncryption() {        
        
    }

    protected static void makeRsaEncryption() {
        try {
            System.out.println("Press 'Enter' to generate RSA key for receiver");
            Project.scan.nextLine();
            Project.receiver.generateKey();

            System.out.println("Press 'Enter' to send receiver public key to sender");
            Project.scan.nextLine();
            Project.sender.setPeerPublicKey(Project.receiver.getPublicKey());

            if(Project.mode.equals("file")) {
                System.out.println("Press 'Enter' to encrypt the file");
                Project.scan.nextLine();
                Project.sender.encryptFileUsingRsa();

                System.out.println("Press 'Enter' to send the encrypted file");
                Project.scan.nextLine();
                Project.receiver.setEncryptedMessage(Project.sender.getEncryptedMessage());

                System.out.println("Press 'Enter' to decrypt the file");
                Project.scan.nextLine();
                Project.receiver.decryptFileUsingRsa();
            }

            else if(Project.mode.equals("key")) {
                System.out.println("Press 'Enter' to encrypt the AES key using RSA");
                Project.scan.nextLine();
                Project.sender.encryptAesKey();

                System.out.println("Press 'Enter' to send the encrypted key to receiver");
                Project.scan.nextLine();
                Project.receiver.setEncryptedAesKey(Project.sender.getEncryptedAesKey());

                System.out.println("Press 'Enter' to decrypt the AES key");
                Project.scan.nextLine();
                Project.receiver.decryptAesKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}