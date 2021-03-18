package it.unical.dimes.reti.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTMain {

    //Metodo per costruire un JWT
    public static String createJWT(String id, String issuer, String subject, long ttlMillis, PrivateKey privateKey) {

        //Algortimo usato per generare la firma del JWT
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, privateKey);

        //Si aggiunge ache una data di scadenza del JWT
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Crea il JWT e lo serializza in una stringa URl-safe
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt, PublicKey publicKey) {

        //Viene sollevata un'eccezione se il JWT non è correttamente firmato
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    // Get RSA keys. Uses key size of 2048.
    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Generazione coppia chiavi RSA...");
        Map<String, Object> rsaKeys = getRSAKeys();
        PublicKey publicKey = (PublicKey) rsaKeys.get("public");
        PrivateKey privateKey = (PrivateKey) rsaKeys.get("private");

        Map<String, Object> rsaKeysUtente2 = getRSAKeys();
        PublicKey publicKey2 = (PublicKey) rsaKeysUtente2.get("public");
        PrivateKey privateKey2 = (PrivateKey) rsaKeysUtente2.get("private");

        String jwtId = "1234567890";
        String jwtIssuer = "Reti di Calcolatori - Demo JWT";
        String jwtSubject = "Pippo Rossi";
        int jwtTimeToLive = 600000;
        System.out.println("Generazione JWT Token...");
        String jwt = JWTMain.createJWT(
                jwtId, jwtIssuer, jwtSubject, jwtTimeToLive, privateKey
        );
        System.out.println(jwt);

        System.out.println("Decodifica JWT Token con Public Key valida...");
        Claims claims = JWTMain.decodeJWT(jwt, publicKey);
        System.out.println(claims);

        System.out.println("Decodifica JWT Token con Public Key Fake...");
        claims = JWTMain.decodeJWT(jwt, publicKey2);
        System.out.println(claims);
    }


}
