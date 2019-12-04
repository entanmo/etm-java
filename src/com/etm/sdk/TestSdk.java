package com.etm.sdk;

import com.alibaba.fastjson.JSONObject;
import com.etm.sdk.dto.query.TransactionQueryParameters;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class TestSdk {
    public static void main(String[] args) {
        try {
            String url = "http://127.0.0.1:4096";
            String secondSecret = null;
//            champion hold bike sniff endorse smooth quiz inspire either garage step system ABwNzY2m1ryHG4xQWKY1aZxQKPubXVhz7Q
            String secret = "race forget pause shoe trick first abuse insane hope budget river enough";
            //设置etm服务地址
            EtmSDK.Config.setEtmServer(url);
            //设置成对应的网络Magic值
            EtmSDK.Config.setMagic("test1111");
            String publicKey = EtmSDK.Helper.getPublicKey(secret);
            System.out.println(publicKey);

            //生成账号
//            for( int i=0; i<10; i++) {
//                String newSecret = EtmSDK.Helper.generateSecret();
//                System.out.println(newSecret);
//            }


            //登录
            EtmResult result = EtmSDK.Account.secureLogin(secret);
            System.out.println(result.getRawJson());

            if(result.isSuccessful()){
                Map<String,Object> accountMap = (Map<String,Object>)result.parseMap().get("account");
                String accountPublicKey = EtmSDK.Helper.getPublicKey(secret);
                String accountAddress = accountMap.get("address").toString();
                BigDecimal balance = BigDecimal.valueOf(Long.parseLong(accountMap.get("balance").toString()), 8);

                System.out.println(String.format("登录成功, publicKey:%s, address:%s, ETM余额：%s", accountPublicKey, accountAddress, balance.toPlainString()));
            }

           // 转账(金额为1ETM)
//            result = EtmSDK.Account.transferSigned("ABwNzY2m1ryHG4xQWKY1aZxQKPubXVhz7Q",
//                    EtmSDK.Helper.amountForETM(BigDecimal.valueOf(1)),
//                    //"Transfer by Test"
//                    "  ", secret, secondSecret);
            result = EtmSDK.Account.transfer("ABwNzY2m1ryHG4xQWKY1aZxQKPubXVhz7Q",
                    EtmSDK.Helper.amountForETM(BigDecimal.valueOf(1.234)),
                    //"Transfer by Test"
                    "  ", secret, secondSecret);
            System.out.println(result.getRawJson());
            if(result.isSuccessful()){
                String transactionId = result.parseMap().get("transactionId").toString();
                System.out.println("交易成功,transaction id：" + transactionId);
                System.out.println("交易成功,transaction id：" + transactionId);

                int retry = 10 ;
                do {
                    //查询单条交易，注意：刚完成的交易查不到，正常情况需要1s-10s不等。建议多等一段时间。
                    result = EtmSDK.Transaction.getTransaction(transactionId);
                    if (!result.isSuccessful())
                    {
                        System.out.println("查询交易失败或未查询到交易信息，3秒后重试...");
                        Thread.sleep(3000);
                    }
                    else
                    {
                        Map<String,Object> transactionMap = (Map<String,Object>)result.parseMap().get("transaction");
                        long blockHeight = Integer.parseInt(transactionMap.get("height").toString());
                        String blockId = transactionMap.get("blockId").toString();
                        TransactionType type = TransactionType.fromCode(Integer.parseInt(transactionMap.get("type").toString()));
                        Date time = EtmSDK.Helper.dateFromEtmTimestamp(Integer.parseInt(transactionMap.get("type").toString()));
                        String senderPublicKey = transactionMap.get("senderPublicKey").toString();
                        String senderId = transactionMap.get("senderId").toString();
                        String recipientId = transactionMap.get("recipientId").toString();
                        BigDecimal etmAmount = BigDecimal.valueOf(Long.parseLong(transactionMap.get("amount").toString()), 8);
                        BigDecimal fee = BigDecimal.valueOf(Long.parseLong(transactionMap.get("fee").toString()), 8);
                        int confirmations = Integer.parseInt(transactionMap.get("confirmations").toString());

                        String transactionInfo = String.format("block height:%d,\n block id:%s,\n transaction type:%s,\n " +
                                "time:%s,\n sender public key:%s,\n sender id:%s,\n recipient id:%s,\n ETM amount:%s,\n fee:%s,\n confirmations:%d ",
                                blockHeight, blockId, type.getCode(), time.toString(), senderPublicKey, senderId, recipientId,
                                etmAmount.toString(), fee.toString(), confirmations);
                        System.out.println("查询交易成功，交易信息：\n" +transactionInfo);
                    }

                }while(!result.isSuccessful() && retry-- > 0);
            //查询交易transaction
            TransactionQueryParameters parameters = new TransactionQueryParameters()
                    .setRecipientId("ABwNzY2m1ryHG4xQWKY1aZxQKPubXVhz7Q")
                    .setLimit(10)
                    .orderByAscending("t_timestamp");
            result = EtmSDK.Transaction.queryTransactions(parameters);
            System.out.println(result.getRawJson());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }
}