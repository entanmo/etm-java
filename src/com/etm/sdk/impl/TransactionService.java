package com.etm.sdk.impl;

import com.etm.sdk.EtmResult;
import com.etm.sdk.Transaction;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dto.query.TransactionQueryParameters;

public class TransactionService extends EtmRESTService implements Transaction {
    @Override
    public EtmResult queryTransactions(TransactionQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidTransactionQueryParameters(parameters), "invalid parameters");

            ParameterMap getParameters = parametersFromObject(parameters);
            return get(EtmServiceUrls.Transaction.QUERY_TRANSACTIONS, getParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getTransaction(String transactionId) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transaction id");

            ParameterMap parameters = new ParameterMap().put("id", transactionId);
            return get(EtmServiceUrls.Transaction.GET_TRANSACTION, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getUnconfirmedTransaction(String unconfirmedTransactionId){
        try {
            Argument.require(Validation.isValidTransactionId(unconfirmedTransactionId), "invalid transaction id");

            ParameterMap parameters = new ParameterMap()
                    .put("id", unconfirmedTransactionId);
            return get(EtmServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTION, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getUnconfirmedTransactions(String senderPublicKey, String address) {
        try {
            Argument.optional(senderPublicKey, Validation::isValidPublicKey, "invalid senderPublicKey");
            Argument.optional(address, Validation::isValidAddress, "invalid address");

            ParameterMap parameters = new ParameterMap()
                    .put("senderPublicKey", senderPublicKey)
                    .put("address", address);

            return get(EtmServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTIONS, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public EtmResult addTransaction(String secret, int amount, String recipientId, String senderPublicKey, String secondSecret, String multiSignAccountPublicKey) {
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.require(Validation.isValidSecondSecret(secondSecret), "invalid secondSecret");
            Argument.require(Validation.isValidAmount(amount), "invalid amount");
            Argument.require(Validation.isValidPublicKey(senderPublicKey), "invalid senderPublicKey");
            Argument.optional(multiSignAccountPublicKey, Validation::isValidPublicKey, "invalid multiSignAccountPublicKey");

            ParameterMap parameters = new ParameterMap()
                    .put("secret", secret)
                    .put("amount", amount)
                    .put("recipientId", recipientId)
                    .put("secondSecret", secondSecret)
                    .put("senderPublicKey", senderPublicKey)
                    .put("multisigAccountPublicKey", multiSignAccountPublicKey);

            return post(EtmServiceUrls.Transaction.CREATE_TRANSACTION, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
