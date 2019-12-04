package com.etm.sdk.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by eagle on 17-7-18.
 */
public final class EtmConst {
    public static final int CLIENT_DRIFT_SECONDS = 2;
    public static final int COIN = 100000000;
    public static final char BASE58_ADDRESS_PREFIX = 'A';
    public static final int MIN_BASE58_ADDRESS_LEN = 30;
    public static final Date ETM_BEGIN_EPOCH;

    static {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
           date = df.parse("2018-10-12T12:00:00Z");
        }
        catch (Exception ex){
            //never
        }
        ETM_BEGIN_EPOCH = date;
    }

    public static class Fees{
        public static final long TRANSFER = 10000000;
        public static final long UIA_TRANSFER = 10000000;
        public static final long VOTE = 10000000;
        public static final long SECOND_SIGNATURE =500000000;
        public static final long MULTI_SIGNATURE =500000000;
        public static final long DELEGATE = 10000000000L;
        public static final long DAPP = 10000000000L;
    }



    public class AccountFields{
        public static final String NAME = "name";

    }
}
