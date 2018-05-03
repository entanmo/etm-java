package com.etm.sdk.impl;

import com.etm.sdk.Delegate;
import com.etm.sdk.EtmResult;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dto.query.DelegateQueryParameters;
import com.etm.sdk.transaction.TransactionInfo;

public class DelegateService extends EtmRESTService implements Delegate {

    @Override
    public EtmResult getDelegatesCount() {
        return get(EtmServiceUrls.Delegate.GET_DELEGATES_COUNT);
    }

    @Override
    public EtmResult getVoters(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Delegate.GET_VOTES, publicKey);
    }

    @Override
    public EtmResult getDelegateByPublicKey(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Delegate.GET_DELEGATE, publicKey);
    }

    @Override
    public EtmResult getDelegateByName(String userName){
        try {
            Argument.notNull(userName, "invalid user name");

            ParameterMap parameters = new ParameterMap().put("username", userName);
            return get(EtmServiceUrls.Delegate.GET_DELEGATE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult queryDelegates( DelegateQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidDelegateQueryParameters(parameters), "invalid parameters");

            ParameterMap query = parametersFromObject(parameters);
            return get(EtmServiceUrls.Delegate.QUERY_DELEGATES,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getDelegateFee(String publicKey){
        return getByPublicKey(EtmServiceUrls.Delegate.GET_DELEGATE_FEE, publicKey);
    }

    @Override
    public EtmResult getForging(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Delegate.GET_FORGING, publicKey);
    }

    @Override
    public EtmResult registerDelegate(String userName, String secret, String secondSecret ) {
        try {
            Argument.notNull(userName, "invalid username");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");

            TransactionInfo transaction = getTransactionBuilder().buildDelegate(userName, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    /**
    *    enableForge和disableForge接口不知接口地址，待确认
    * */
    @Override
    public EtmResult enableForge( String publicKey, String secret ) {

        return null;
    }

    @Override
    public EtmResult disableForge(String publicKey, String secret ) {
        return null;
    }

    @Override
    public EtmResult getForgingStatus(String publicKey) {
        return getByPublicKey(EtmServiceUrls.Delegate.GET_FORGING_STATUS, publicKey);
    }
}
