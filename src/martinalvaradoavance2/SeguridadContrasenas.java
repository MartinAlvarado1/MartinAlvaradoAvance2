
package martinalvaradoavance2;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SeguridadContrasenas {

    // Configuración para proteger las contraseñas
    private static final int ITERACIONES = 210000;
    private static final int LONGITUD_SAL = 16;
    private static final int LONGITUD_HASH = 256;

    // Genera un hash protegido de la contraseña
    public static String generarHash(String contrasena) {

        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException(
                    "La contraseña no puede estar vacía.");
        }

        byte[] sal = new byte[LONGITUD_SAL];
        new SecureRandom().nextBytes(sal);

        char[] caracteres = contrasena.toCharArray();

        try {

            byte[] hash = calcularHash(caracteres, sal);

            return Base64.getEncoder().encodeToString(sal)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } finally {
            Arrays.fill(caracteres, '\0');
        }
    }

    // Comprueba una contraseña contra el hash almacenado
    public static boolean verificarContrasena(
            String contrasena, String valorGuardado) {

        if (contrasena == null || valorGuardado == null) {
            return false;
        }

        try {

            String[] partes = valorGuardado.split(":", -1);

            if (partes.length != 2) {
                return false;
            }

            byte[] sal = Base64.getDecoder().decode(partes[0]);
            byte[] hashGuardado =
                    Base64.getDecoder().decode(partes[1]);

            if (sal.length != LONGITUD_SAL
                    || hashGuardado.length != LONGITUD_HASH / 8) {
                return false;
            }

            char[] caracteres = contrasena.toCharArray();

            try {

                byte[] hashCalculado =
                        calcularHash(caracteres, sal);

                return MessageDigest.isEqual(
                        hashCalculado, hashGuardado
                );

            } finally {
                Arrays.fill(caracteres, '\0');
            }

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Aplica PBKDF2 con SHA-256
    private static byte[] calcularHash(
            char[] contrasena, byte[] sal) {

        PBEKeySpec especificacion = new PBEKeySpec(
                contrasena,
                sal,
                ITERACIONES,
                LONGITUD_HASH
        );

        try {

            SecretKeyFactory fabrica =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            return fabrica.generateSecret(
                    especificacion
            ).getEncoded();

        } catch (NoSuchAlgorithmException
                | java.security.spec.InvalidKeySpecException e) {

            throw new IllegalStateException(
                    "No se pudo proteger la contraseña.", e
            );

        } finally {
            especificacion.clearPassword();
        }
    }
}

