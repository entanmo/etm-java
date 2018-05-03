package com.etm.sdk.transaction.asset;

import java.nio.ByteBuffer;

import com.etm.sdk.codec.Decoding;
import com.etm.sdk.codec.Encoding;

public class StorageAssetInfo extends AssetInfo {
    public static class StorageInfo {
        public String getContent() {
            return content;
        }

        private String content = null;

        private StorageInfo(String content) {
            this.content = content;
        }
    }

    public StorageInfo getStorage() {
        return storage;
    }

    public StorageAssetInfo(byte[] contentBuffer){
        this.storage = new StorageInfo(Encoding.hex(contentBuffer));
    }

    private StorageInfo storage = null;

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(Decoding.unsafeDecodeHex(storage.getContent()));
    }

}
