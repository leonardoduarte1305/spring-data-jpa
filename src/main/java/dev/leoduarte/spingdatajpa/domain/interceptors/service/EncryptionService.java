package dev.leoduarte.spingdatajpa.domain.interceptors.service;

public interface EncryptionService {

    String encrypt(String creditCardNumber);

    String decrypt(String creditCardNumber);
}
