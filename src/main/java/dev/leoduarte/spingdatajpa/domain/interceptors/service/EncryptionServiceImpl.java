package dev.leoduarte.spingdatajpa.domain.interceptors.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String creditCardNumber) {
        return Base64
                .getEncoder()
                .encodeToString(creditCardNumber.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(String creditCardNumber) {
        return new String(Base64
                .getDecoder()
                .decode(creditCardNumber.getBytes(StandardCharsets.UTF_8)));
    }
}
