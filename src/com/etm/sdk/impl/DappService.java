package com.etm.sdk.impl;

import java.text.MessageFormat;

import com.etm.sdk.Dapp;
import com.etm.sdk.EtmResult;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dbc.ContractException;
import com.etm.sdk.dto.query.DappQureyParameters;
import com.etm.sdk.transaction.TransactionInfo;

public class DappService extends EtmRESTService implements Dapp {
    @Override
    public EtmResult getBlocksHeight(String dappId) {
        try {
            Argument.notNull(dappId, "dappId is null");
            return get(MessageFormat.format(EtmServiceUrls.Dapp.GET_BLOCKS_HEIGHT, dappId));
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public EtmResult getBlocks(String dappId, DappQureyParameters parameters) {
        try {
            Argument.notNull(dappId, "dappId is null");
            ParameterMap query = parametersFromObject(parameters);
            return get(MessageFormat.format(EtmServiceUrls.Dapp.GET_BLOCKS, dappId),  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAccount(String dappId,String address) {
        try {
            Argument.notNull(dappId, "dappId is null");
            return get(MessageFormat.format(EtmServiceUrls.Dapp.GET_ACCOUNT,dappId,address));
        } catch (ContractException ex) {
            return fail(ex);
        }
    }

    @Override
    public EtmResult dappDeposit(String dappId, String currency, long amount, String secret, String secondSecret) {
        try {
            Argument.notNull(dappId, "dappId is null");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");
            TransactionInfo transaction = getTransactionBuilder()
                    .buildInTransfer(dappId, currency,amount, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

}
