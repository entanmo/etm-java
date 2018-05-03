package com.etm.sdk.impl;


import java.text.MessageFormat;

import com.etm.sdk.EtmResult;
import com.etm.sdk.UIA;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.transaction.TransactionInfo;

public class  UIAService extends EtmRESTService implements UIA {
    @Override
    public EtmResult getIssuers(int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");

            return get(EtmServiceUrls.UIA.GET_ISSUERS ,createLimitAndOffsetParameters(limit, offset));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getIssuer(String nameOrAddress) {
        try{
            Argument.notNullOrEmpty(nameOrAddress, "invalid nameOrAddress");

            return get(MessageFormat.format(EtmServiceUrls.UIA.GET_ISSUER,nameOrAddress));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult queryIssuerAssets(String nameOrAddress, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(nameOrAddress, "invalid nameOrAddress");

            ParameterMap parameters = createLimitAndOffsetParameters(limit, offset);

            return get(MessageFormat.format(EtmServiceUrls.UIA.QUERY_ISSUER_ASSETS,nameOrAddress), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAssets(int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");

            return get(EtmServiceUrls.UIA.GET_ASSETS, createLimitAndOffsetParameters(limit, offset));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAsset(String assetName) {
        try {
            Argument.notNullOrEmpty(assetName, "assetName");

            return get(MessageFormat.format(EtmServiceUrls.UIA.GET_ASSET, assetName));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAssetACL(String assetName, boolean whiteOrBlack, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(assetName, "invalid assetName");

            ParameterMap parameters = createLimitAndOffsetParameters(limit, offset)
                    .put("name", assetName)
                    .put("flag", whiteOrBlack);

            return get(MessageFormat.format(EtmServiceUrls.UIA.GET＿ASSET＿ACL,assetName), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAddressBalances(String address, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.require(Validation.isValidAddress(address), "invalid address");

            ParameterMap parameters = createLimitAndOffsetParameters(limit, offset);

            return get(EtmServiceUrls.UIA.GET_ADDRESS_BALANCES + address, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getTransactions(String ownerPublicKey, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(ownerPublicKey, "invalid ownerPublicKey");

            ParameterMap parameters = createLimitAndOffsetParameters(limit, offset)
                    .put("publicKey", ownerPublicKey);

            return get(EtmServiceUrls.UIA.GET_TRANSACTIONS, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult createIssuer(String name, String desc, String secret, String secondSecret) {
        return null;
    }

    @Override
    public EtmResult createAsset(String currency, String desc, long maximum, byte precision, String strategy, String secret, String secondSecret) {
        return null;
    }

    @Override
    public EtmResult setAssetACL(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret) {
        return null;
    }

    @Override
    public EtmResult issue(String currency, long amount, String secret, String secondSecret) {
        return null;
    }

    @Override
    public EtmResult transfer(String currency, String recipientId, long amount, String message, String secret, String secondSecret) {
        try {
            Argument.notNullOrEmpty(currency, "invalid currency");
            Argument.require(Validation.isValidAddress(recipientId), "invalid recipientId");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildUIATransfer(currency, amount, recipientId, message, secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult setAssetStatus(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret) {
        return null;
    }

    private ParameterMap createLimitAndOffsetParameters(int limit, int offset){
        return new ParameterMap()
                .put("limit", limit)
                .put("offset", offset);
    }

}
