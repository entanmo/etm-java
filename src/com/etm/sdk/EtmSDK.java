package com.etm.sdk;

import com.etm.sdk.impl.EtmFactory;

/**
 * Etm接口门面类，对外提供简单的访问模式
 * @author eagle
 *
 */
public final class EtmSDK {
    public final static Account Account;
    public final static Delegate Delegate;
    public final static Transaction Transaction;
    public final static Block Block;
    public final static Signature Signature;
    public final static Dapp Dapp;
    public final static UIA UIA;
    public final static Peer Peer;
    public final static Misc Misc;

    public final static EtmSDKConfig Config = EtmSDKConfig.getInstance();
    public final static EtmHelper Helper = new EtmHelper();

    static {
        EtmFactory factory = EtmFactory.getInstance();

        Account = factory.getService(com.etm.sdk.Account.class);
        Block = factory.getService(com.etm.sdk.Block.class);
        Delegate = factory.getService(com.etm.sdk.Delegate.class);
        Transaction = factory.getService(com.etm.sdk.Transaction.class);
        Signature = factory.getService(com.etm.sdk.Signature.class);
        Dapp = factory.getService(com.etm.sdk.Dapp.class);
        UIA = factory.getService(com.etm.sdk.UIA.class);
        Peer = factory.getService(com.etm.sdk.Peer.class);
        Misc = factory.getService(com.etm.sdk.Misc.class);
    }

}
