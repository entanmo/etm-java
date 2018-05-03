package com.etm.sdk;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle
 */
public final class EtmSDKConfig {

    private final static EtmSDKConfig instance = new EtmSDKConfig();
    public static EtmSDKConfig getInstance(){ return instance; }
    private EtmSDKConfig(){}


    private final static String sdkVersion = "1.3";

    public String getSDKVersion(){ return sdkVersion; }

    public String getEtmServer() {
        return etmServer;
    }

    public void setEtmServer(String etmServer) {
        this.etmServer = etmServer;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public boolean isLongTransactionIdEnabled() {
        return longTransactionIdEnabled;
    }

    public void setLongTransactionIdEnabled(boolean longTransactionIdEnabled) {
        this.longTransactionIdEnabled = longTransactionIdEnabled;
    }

    private String etmServer ="http://127.0.0.1:4096";
    private String magic = "aabbccdd"; //localnet
    private boolean longTransactionIdEnabled = true;
    private boolean debugMode = true;

    public boolean tryLoadFromJson(String jsonString){
        try{
            loadFromJson(jsonString);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public void loadFromJson(String jsonString) throws JSONException{
        EtmSDKConfig config = JSONObject.parseObject(jsonString, EtmSDKConfig.class);
        config.copyTo(instance);
    }

    protected void copyTo(EtmSDKConfig another){
        another.magic = this.magic;
        another.debugMode = this.debugMode;
        another.etmServer = this.etmServer;
        another.longTransactionIdEnabled = this.longTransactionIdEnabled;
    }

}
