package com.runninghi;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTests {

    @Test
    void jasypt(){

        String username = "9a41e071dfbe8d7a22335b8e260dc8e8";
        String password = "NG0BUacgojEc914cdgCA5DdIlX3BQMSZ";
        String jwt = "NiOeyFbN1Gqo10bPgUyTFsRMkJpGLXSvGP04eFqj5B30r5TcrtlSXfQ7TndvYjNvfkEKLqILn0j1SmKODO1Yw3JpBBgI3nVPEahqxeY8qbPSFGyzyEVxnl4AQcrnVneI";

        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);
        String encryptJwt = jasyptEncrypt(jwt);

        System.out.println("encryptUsername : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);
        System.out.println("encryptPassword : " + encryptJwt);

        System.out.println("decryptUsername : " +  jasyptDecryt(encryptUsername));
        System.out.println("decryptPassword : " +  jasyptDecryt(encryptPassword));
        System.out.println("decryptPassword : " +  jasyptDecryt(encryptJwt));

    }

    private String jasyptEncrypt(String input) {
        String key = "home";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        String key = "home";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }

}
