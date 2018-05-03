package com.etm.sdk.impl;

import com.etm.sdk.EtmResult;
import com.etm.sdk.Signature;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.transaction.TransactionInfo;

public class SignatureService extends EtmRESTService implements Signature {
    @Override
    public EtmResult setSignature(String secret, String secondSecret, String publicKey, String multiSignAccountPublicKey) {
        try{
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");
            Argument.require(Validation.isValidSecondSecret(secondSecret), "invalid secondSecret");
            Argument.optional(multiSignAccountPublicKey, Validation::isValidPublicKey, "invalid multiSignAccountPublicKey");

//            EtmResult parameters = new EtmResult()
//                    .fluentPut("secret", secret)
//                    .fluentPut("publicKey", publicKey)
//                    .fluentPut("secondSecret", secondSecret)
//                    .fluentPut("multisigAccountPublicKey", multiSignAccountPublicKey); //注意名称要与etm源码一致!!!

            TransactionInfo transaction = getTransactionBuilder()
                    .buildSignature(secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getSignatureFee() {
        return get(EtmServiceUrls.Signature.GET_SIGNATURE_FEE);
    }

    //TODO:此接口实现有疑问
    @Override
    public EtmResult setMultiSignature(int minAccount, int lifetime, String[] addKeys, String[] removeKeys,
                                        String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidMultiSignatureMinAccount(minAccount), "invalid minAccount");
            Argument.require(Validation.isValidMultiSignatureKeys(addKeys, removeKeys), "invalid addKeys or removeKeys");
            Argument.require(Validation.isValidMultiSignatureLifetime(lifetime), "invalid lifetime");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");

//            List<String> keysGroup = new ArrayList<>();
//            if (addKeys != null)
//                Arrays.asList(addKeys).forEach(key-> keysGroup.add("+"+key));
//            if (removeKeys != null)
//                Arrays.asList(removeKeys).forEach(key-> keysGroup.add("-"+key));
//
//            EtmResult parameters = new EtmResult()
//                    .fluentPut("secret", secret)
//                    .fluentPut("publicKey", publicKey)
//                    .fluentPut("secondSecret", secondSecret)
//                    .fluentPut("min", minAccount)
//                    .fluentPut("lifetime", lifetime)
//                    .fluentPut("keysgroup", keysGroup);
//            return post(EtmServiceUrls.Signature.SET_MULTI_SIGNATURE, parameters);
            TransactionInfo transaction = getTransactionBuilder()
                    .buildMultiSignature(minAccount, lifetime, addKeys, removeKeys, secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }

    }

    //TODO:此接口实现有疑问
    @Override
    public EtmResult multiSignature(String transactionId, String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transactionId");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");

            ParameterMap parameters = new ParameterMap()
                    .put("secret", secret)
                    .put("publicKey", publicKey)
                    .put("secondSecret", secondSecret)
                    .put("transactionId", transactionId);

            return post(EtmServiceUrls.Signature.MULTI_SIGNATURE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getPendingTransactions(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Signature.GET_PENDING_TRANSACTIONS, publicKey);
    }

    @Override
    public EtmResult getMultiSignatureAccounts(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Signature.GET_MULTI_SIGNATURE_ACCOUNTS, publicKey);
    }
}
