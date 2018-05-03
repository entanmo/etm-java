package com.etm.sdk.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.etm.sdk.*;
import com.etm.sdk.security.DefaultSecurityStrategy;
import com.etm.sdk.security.SecurityStrategy;

/**
 * 简单的Etm服务对象工厂，可替换成DI工具管理
 * @author eagle
  */
public final class EtmFactory {
    private final static EtmFactory instance = new EtmFactory();

    private EtmFactory(){ }

    public static EtmFactory getInstance(){
        return instance;
    }

    private final SecurityStrategy securityStrategy = new DefaultSecurityStrategy();

    private static Map<Class<? extends EtmInterface> , EtmInterface> serviceContainer = new ConcurrentHashMap<>();

    /**
     * 注册服务类
     * @param interfaceType Etm接口类
     * @param serviceType Etm接口实现类
     * @return 工厂对象本身，实现链式访问
     * */
    protected EtmFactory register(Class<? extends EtmInterface> interfaceType, Class<? extends EtmInterface> serviceType){
        try{
            serviceContainer.put(interfaceType, serviceType.newInstance());
        }
        catch (Exception ex){
            //do nothing;
        }

        return this;
    }

    /**
     * 获取Etm接口服务实例
     * @param interfaceType 接口类型
     * @return 服务对象实例
     * */
    public <AI> AI getService(Class<? extends EtmInterface> interfaceType){
        try {
            return (AI) serviceContainer.get(interfaceType);
        }
        catch (Exception ex){
            return null;
        }
    }

    public SecurityStrategy getSecurity(){ return securityStrategy; }

    static {
        getInstance()
                .register(Account.class, AccountService.class)
                .register(Delegate.class, DelegateService.class)
                .register(Block.class, BlockService.class)
                .register(Transaction.class, TransactionService.class)
                .register(Signature.class, SignatureService.class)
                .register(Dapp.class, DappService.class)
                .register(UIA.class, UIAService.class)
                .register(Peer.class, PeerService.class)
                .register(Misc.class, MiscService.class);
    }

}
