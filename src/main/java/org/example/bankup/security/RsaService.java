package org.example.bankup.security;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class RsaService {

    public RsaService() {

    }

    public static void loadKeys(){

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            try(FileOutputStream fos = new FileOutputStream("public.key");
                FileOutputStream fos2 = new FileOutputStream("private.key");){

                fos.write(publicKey.getEncoded());
                fos2.write(privateKey.getEncoded());
            }
        } catch (NoSuchAlgorithmException |IOException er){
            throw new RuntimeException("Error : " + er);
        }

    }

    public static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        File publicKeyFile = new File("public.key");
        byte[] keyBytes = Files.readAllBytes(publicKeyFile.toPath());


        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        File privateKeyFile = new File("private.key");
        byte[] keyBytes = Files.readAllBytes(privateKeyFile.toPath());

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    //InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException
    public  String encryptData(String data) {
        try{
            Cipher encryptCipher = Cipher.getInstance("RSA");
            PublicKey publicKey = getPublicKey();
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] dataBytes = data.getBytes();
            byte[] encryptedDataBytes = encryptCipher.doFinal(dataBytes);

            return Base64.getEncoder().encodeToString(encryptedDataBytes);
        }catch(Exception er){
            throw new RuntimeException("Error : " + er);
        }

    }

    //InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException
    public String decryptData(String encryptedData)  {
        try{
            Cipher decryptCipher = Cipher.getInstance("RSA");
            PrivateKey privateKey = getPrivateKey();
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = decryptCipher.doFinal(encryptedDataBytes);

            return new String(decryptedData);
        }catch(Exception er){
            throw new RuntimeException("Error : " + er);
        }


    }


}
