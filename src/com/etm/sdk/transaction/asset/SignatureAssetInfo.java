package com.etm.sdk.transaction.asset;

import java.nio.ByteBuffer;

import com.etm.sdk.codec.Decoding;

public class SignatureAssetInfo extends AssetInfo {

    public static class SignatureInfo {
        public String getPublicKey() {
            return publicKey;
        }

        private SignatureInfo(String publicKey) {
            this.publicKey = publicKey;
        }

        private String publicKey;
    }

    public SignatureInfo getSignature() {
        return signature;
    }

    public SignatureAssetInfo(String publicKey){
        signature = new SignatureInfo(publicKey);
    }

    private SignatureInfo signature = null;

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(Decoding.unsafeDecodeHex(signature.getPublicKey()));
    }
}
