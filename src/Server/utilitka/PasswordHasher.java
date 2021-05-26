package Server.utilitka;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    public static String hasher(String password){
        try{
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-1");
            byte[] bytes= messageDigest.digest(password.getBytes());
            BigInteger bigInteger=new BigInteger(1,bytes);
            String newPassword = bigInteger.toString(16);
            while (newPassword.length() < 32) {
                newPassword = "0" + newPassword;
            }
            return newPassword;
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("Не найден алгоритм хэширования пароля!");
            throw new IllegalStateException(exception);
        }
    }
}
