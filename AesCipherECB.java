import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AesCipherECB {

    private static final String CIPHER_INSTANCE_TYPE = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private Cipher cipherEncryptMode = null;
    private Cipher cipherDecryptMode = null;

    public AesCipherECB(String key, int keyBlockSizeBit)
            throws
            NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        this();
        cipherEncryptMode = getCipher(Cipher.ENCRYPT_MODE, key, keyBlockSizeBit);
        cipherDecryptMode = getCipher(Cipher.DECRYPT_MODE, key, keyBlockSizeBit);
    }

    public AesCipherECB() {

    }

    private Cipher getCipher(int opMode, String key, int keyBlockSizeBit)
            throws
            NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_TYPE);
        byte[] keyBytes = string2SizedBytes(key, keyBlockSizeBit / 8);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);

        cipher.init(opMode, keySpec);

        return cipher;
    }

    public String encrypt(byte[] data)
            throws
            IllegalBlockSizeException, BadPaddingException {
        try {
            byte[] encrypted = null;
            synchronized (cipherEncryptMode) {
                encrypted = cipherEncryptMode.doFinal(data);
            }
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (IllegalBlockSizeException
                 | BadPaddingException e) {
            throw e;
        }
    }

    public String decrypt(byte [] input)
            throws
            IllegalBlockSizeException, BadPaddingException {
        try {
            byte[] data = Base64.getDecoder().decode(input);
            byte[] decrypted = null;
            synchronized (cipherDecryptMode) {
                decrypted = cipherDecryptMode.doFinal(data);
            }
            return new String(decrypted);
        } catch (IllegalBlockSizeException
                 | BadPaddingException e) {
            throw e;
        }
    }

    private static byte[] string2SizedBytes(String in, int size) throws UnsupportedEncodingException {
        byte[] bytesIn = in.getBytes(DEFAULT_ENCODING);
        byte[] out = new byte[size];
        int maxLen = bytesIn.length > size ? size : bytesIn.length;
        System.arraycopy(bytesIn, 0, out, 0, maxLen);
        return out;
    }

    public static void main(String[] args) {
        try {
            // Specify the complete file path
            String inputFilePath = "data.txt";
            String encryptedFile = "test_enc.txt";
            String decryptedFile = "test_dec.txt";

            // Enter The Key
            Scanner scanner_key = new Scanner(System.in);
            System.out.println("Enter Key :");
            String key = scanner_key.nextLine();


            // Read data from the file
            byte[] fileData = Files.readAllBytes(Paths.get(inputFilePath));

            // Create an instance of AesFixedCipher
            AesCipherECB aesCipher = new AesCipherECB(key, 128);

            // Encrypt the data
            String encryptedData = aesCipher.encrypt(fileData);

            // Write the encrypted data to a file
            Files.write(Paths.get(encryptedFile), encryptedData.getBytes());

            // Decrypt the data
            String decryptedData = aesCipher.decrypt(encryptedData.getBytes());
            System.out.println("file len after dec "+decryptedData.getBytes().length);

            // Write the decrypted data to a file
            Files.write(Paths.get(decryptedFile), decryptedData.getBytes());

            System.out.println("File encryption and decryption completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
