package com.etm.sdk.impl;

import java.security.KeyPair;

import com.etm.sdk.Account;
import com.etm.sdk.EtmResult;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dto.query.QueryParameters;
import com.etm.sdk.transaction.TransactionInfo;

/**
 * {@link Account}服务实现
 * @author eagle
 */
public class AccountService extends com.etm.sdk.impl.EtmRESTService implements Account  {

    @Override
    public EtmResult login(String secret){
        ParameterMap parameters = new ParameterMap().put("secret", secret);
        return post(EtmServiceUrls.Account.LOGIN, parameters);
    }

    @Override
    public EtmResult secureLogin(String secret){
        try{
            Argument.require(Validation.isValidSecret(secret), "invalid secret");

            KeyPair keyPair = getSecurity().generateKeyPair(secret);
            String publicKey = getSecurity().encodePublicKey(keyPair.getPublic());
            ParameterMap parameters = parametersWithPublicKeyField(publicKey);

            return post(EtmServiceUrls.Account.SECURE_LOGIN, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getAccount(String address){
            return getByAddress(EtmServiceUrls.Account.GET_ACCOUNT, address);
    }

    @Override
    public EtmResult getBalance(String address){
        return getByAddress(EtmServiceUrls.Account.GET_BALANCE, address);
    }

    @Override
    public EtmResult getPublicKey(String address){
        return  getByAddress(EtmServiceUrls.Account.GET_PUBLIC_KEY, address);
    }

    @Override
    public EtmResult generatePublicKey(String secret){
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");

            ParameterMap parameters = new ParameterMap().put("secret", secret);
            return post(EtmServiceUrls.Account.GENERATE_PUBLIC_KEY, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getVotedDelegates(String address){
        return getByAddress(EtmServiceUrls.Account.GET_VOTED_DELEGATES, address);
    }

    @Override
    public EtmResult getDelegatesFee(){
        return get(EtmServiceUrls.Account.GET_DELEGATE_FEE);
    }

    //todo:验证投票和取消投票的数组都符合规则
    @Override
    public EtmResult vote(String[] upvotePublicKeys, String[] downvotePublicKeys, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");
            Argument.require(Validation.isValidVoteKeys(upvotePublicKeys, downvotePublicKeys), "invalid upvoteKeys or downvoteKeys");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildVote( upvotePublicKeys, downvotePublicKeys,secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult transfer(String targetAddress, long amount, String message, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidAddress(targetAddress), "invalid target address");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildTransfer(targetAddress, amount, message, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getTopAccounts(QueryParameters parameters){
        try {
            Argument.require(Validation.isValidAccountQueryParameters(parameters), "invalid parameters");

            ParameterMap getParameters = parametersFromObject(parameters);
            return get(EtmServiceUrls.Account.GET_TOP_ACCOUNTS, getParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }
}
