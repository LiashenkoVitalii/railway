package com.liashenko.app.service.utils;

import com.liashenko.app.persistance.domain.Password;
import com.liashenko.app.utils.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.liashenko.app.utils.Asserts.assertIsNull;

/*
 * Class designed to perform all needed operations with passwords
 * by default used PBKDF2WithHmacSHA1 - algorithm to encrypt password,
 * 1000 - iterations,
 * SHA1PRNG - algorithm to generate salt
 */
public abstract class PasswordProcessor {
    private static final Logger classLogger = LogManager.getLogger(PasswordProcessor.class);

    public static byte[] generateSalt(int passwordLength) {
        try {
            SecureRandom sr = SecureRandom.getInstance(AppProperties.getSaltGenerationAlg());
            byte[] salt = new byte[passwordLength * 2];
            sr.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException ex) {
            throw new PasswordProcessorException(ex.getMessage());
        }
    }

    public static byte[] getEncryptedPass(char[] password, byte[] salt) {
        if (salt == null || password == null) throw new PasswordProcessorException("Wrong parameters");
        int iterations = AppProperties.getPassAlgIterationValue();
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(AppProperties.getPassGenerationAlg());
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            classLogger.error(ex);
            throw new PasswordProcessorException(ex.getMessage());
        }
    }

    public static boolean checkPassword(char[] originalPass, Password storedPass) {
        if (assertIsNull(originalPass) || assertIsNull(storedPass)) {
            throw new PasswordProcessorException("Wrong parameters");
        }
        try {
            PBEKeySpec spec = new PBEKeySpec(originalPass, storedPass.getSalt(), storedPass.getIterations(),
                    storedPass.getPassword().length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(storedPass.getAlgorithm());
            byte[] testHash = skf.generateSecret(spec).getEncoded();
            return Arrays.equals(storedPass.getPassword(), testHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            classLogger.error(ex);
            throw new PasswordProcessorException(ex.getMessage());
        }
    }
}
