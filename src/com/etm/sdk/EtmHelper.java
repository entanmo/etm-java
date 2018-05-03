package com.etm.sdk;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.etm.sdk.impl.EtmConst;
import com.etm.sdk.impl.EtmFactory;
import com.etm.sdk.impl.Validation;
import com.etm.sdk.security.SecurityStrategy;

public class EtmHelper {
    private static final EtmFactory factory = EtmFactory.getInstance();

    public String generateSecret(){
        return factory.getSecurity().generateSecret();
    }

    public long amountForETM(BigDecimal etm){
        return etm.multiply(BigDecimal.valueOf(EtmConst.COIN)).longValue();
    }

    public long amountForCoins(int coins){
        return BigDecimal.valueOf(coins).multiply(BigDecimal.valueOf(EtmConst.COIN)).longValue();
    }

    public String getPublicKey(String secret){
        SecurityStrategy security = factory.getSecurity();
        if (!security.isValidSecret(secret))
            return null;

        try {
            return security.encodePublicKey(security.generateKeyPair(secret).getPublic());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public  Date dateFromEtmTimestamp(int timestamp){
        Date beginEpoch =  EtmConst.ETM_BEGIN_EPOCH ;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginEpoch);
        calendar.add(Calendar.SECOND, timestamp);
        return calendar.getTime();
    }

    public Boolean isValidAddress(String address){
        return Validation.isValidAddress(address);
    }

    public boolean isValidBase58Address(String address){
        return  (null != address) && factory.getSecurity().isValidBase58Address(address);
    }
}
