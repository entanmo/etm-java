package com.etm.sdk;

public interface EtmSecret {

    enum SecondSecretState{
        Required,
        Needless,
        Unknow
    }

    String getPublicKey();
    String getSecondPublicKey();
    boolean isSecretPresented();
    boolean isSecondSecretPresented();
    SecondSecretState getSecondSecretState();
    byte[] sign(byte[] content);
    byte[] secondSign(byte[] content);
}
