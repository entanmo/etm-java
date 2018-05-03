package com.etm.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import com.etm.sdk.EtmInterface;
import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDKConfig;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.security.SecurityStrategy;
import com.etm.sdk.transaction.TransactionBuilder;
import com.etm.sdk.transaction.TransactionInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Etm服务基类
 * @author eagle
 */
public abstract class EtmRESTService implements EtmInterface{

    protected static final  Logger logger = LoggerFactory.getLogger(EtmRESTService.class);
    private static final String CHAR_SET = "UTF-8";

    protected static final EtmSDKConfig config = EtmSDKConfig.getInstance();
    private static final Map<String, String> customeHeaders =  new HashMap<>();

    static {
        customeHeaders.put("magic", config.getMagic());
        customeHeaders.put("version", "");
    }

    protected TransactionBuilder getTransactionBuilder(){
        return new TransactionBuilder();
    }

    protected SecurityStrategy getSecurity(){
        return EtmFactory.getInstance().getSecurity();
    }

    protected Map<String, String> getCustomeHeaders(){
        if (! config.getMagic().equals(customeHeaders.get("magic"))){
            customeHeaders.put("magic", config.getMagic());
        }
        return customeHeaders;
    }

    protected String getFullUrl(String relativeUrl){
        return config.getEtmServer() + relativeUrl;
    }

    protected EtmResult get(String relativeUrl){
        try{
            String jsonString = REST.get(getFullUrl(relativeUrl), null);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult get(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.get(getFullUrl(relativeUrl), parameters);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult post(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, null, CHAR_SET);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult post(String relativeUrl, String parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, null, CHAR_SET);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult postMagic(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, getCustomeHeaders(), CHAR_SET);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult postMagic(String relativeUrl, String parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, getCustomeHeaders(), CHAR_SET);
            return EtmResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult broadcastTransaction(TransactionInfo transaction){
        ParameterMap transactionParameter = new ParameterMap()
                .put("transaction", transaction);
        return postMagic(EtmServiceUrls.Peer.BROADCAST_TRANSACTION, transactionParameter );
    }

    protected EtmResult fail(Exception ex){
        logger.error("rest call failed", ex);
        return EtmResult.Failed(ex);
    }

    protected ParameterMap parametersWithPublicKeyField(String publicKey){
        return new ParameterMap().put("publicKey", publicKey);
    }

    protected EtmResult getByPublicKey(String relativeUrl, String publicKey){
        try {
            Argument.require(Validation.isValidPublicKey(publicKey), "invalid public key");

            ParameterMap parameters = parametersWithPublicKeyField(publicKey);
            return get(relativeUrl, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected EtmResult getByAddress(String relativeUrl, String address){
        try {
            Argument.require(Validation.isValidAddress(address), "invalid public address");

            ParameterMap parameters = new ParameterMap().put("address", address);
            return get(relativeUrl, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected ParameterMap parametersFromObject(Object object){
        ParameterMap map = new ParameterMap();
        map.putAll(JSONObject.parseObject( JSONObject.toJSONString(object) ));

        return map;
    }
}
