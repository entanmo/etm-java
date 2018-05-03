package com.etm.sdk.impl;

import com.etm.sdk.ContentEncoding;
import com.etm.sdk.EtmResult;
import com.etm.sdk.Misc;
import com.etm.sdk.codec.Decoding;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.transaction.TransactionInfo;

public class MiscService extends EtmRESTService implements Misc {
    @Override
    public EtmResult getLoadStatus() {
        return get(EtmServiceUrls.Misc.GET_LOAD_STATUS);
    }

    @Override
    public EtmResult getSyncStatus() {
        return get(EtmServiceUrls.Misc.GET_SYNC_STATUS);
    }

    @Override
    public EtmResult storeData(String content, ContentEncoding encoding, int wait, String secret, String secondSecret) {
        try {
            Argument.notNullOrEmpty(content, "content");
            Argument.notNull(encoding, "encoding");
            Argument.require(Validation.isValidContent(content, encoding), "invalid content");
            Argument.require(Validation.isValidWait(wait), "invalid wait");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildStore(getContentBuffer(content, encoding), wait, secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    private byte[] getContentBuffer(String content, ContentEncoding encoding){
        try {
            switch (encoding) {
                case Base64:
                    return Decoding.base64(content);
                case Hex:
                    return Decoding.hex(content);
                case Raw:
                    return content.getBytes();
                default:
                    return new byte[0];
            }
        }catch (Exception ex){
            return new byte[0];
        }
    }

    @Override
    public EtmResult getStoredData(String transactionId) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transactionId");

            ParameterMap parameters = new ParameterMap().put("id", transactionId);

            return get(EtmServiceUrls.Misc.GET_STORED_DATA, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }
}
