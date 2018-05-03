package com.etm.sdk;

import com.etm.sdk.dto.query.TransactionQueryParameters;

/**
 * Etm交易接口
 * @author will
 */
public interface Transaction extends EtmInterface{

    //接口地址：/api/transactions
    //请求方式：get
    //支持格式：urlencoded
    //接口备注：如果请求不加参数则会获取全网所有交易
    //请求参数说明：
    //blockId	string	N	区块id
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //type	integer	N	交易类型,0:普通转账，1:设置二级密码，2:注册受托人，3:投票，4:多重签名，5:DAPP，6:IN_TRANSFER，7:OUT_TRANSFER
    //orderBy	string	N	根据表中字段排序，senderPublicKey:desc
    //offset	integer	N	偏移量，最小值0
    //senderPublicKey	string	N	发送者公钥
    //ownerPublicKey	string	N
    //ownerAddress	string	N
    //senderId	string	N	发送者地址
    //recipientId	string	N	接收者地址,最小长度：1
    //amount	integer	N	金额
    //fee	integer	N	手续费
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactions	列表	多个交易详情json构成的列表
    //count	int	获取到的交易总个数
    EtmResult queryTransactions(TransactionQueryParameters parameters);

    //接口地址：/api/transactions/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //Id	string	Y	交易id
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactions	json	交易详情
    EtmResult getTransaction(String transactionId);

    //接口地址：/api/transactions/unconfirmed/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //id	string	Y	未确认交易id
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transaction	json	未确认交易详情
    EtmResult getUnconfirmedTransaction(String unconfirmedTransactionId);


    //接口地址：/api/transactions/unconfirmed
    //请求方式：get
    //支持格式：urlencoded
    //接口说明：如果不加参数，则会获取全网所有未确认交易 请求参数说明：
    //senderPublicKey	string	N	发送者公钥
    //address	string	N	地址
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactions	Array	未确认交易列表
    EtmResult getUnconfirmedTransactions(String senderPublicKey, String address);

    //接口地址：/api/transactions
    //请求方式：PUT
    //支持格式：json
    //接口备注：接收者账户需在web端钱包登陆过
    //请求参数说明：
    //secret	string	Y	etm账户密码
    //amount	integer	Y	金额，最小值：1，最大值：10000000000000000
    //recipientId	string	Y	接收者地址,最小长度：1
    //publicKey	string	N	发送者公钥
    //secondSecret	string	N	发送者二级密码，最小长度1，最大长度：100
    //multiSigAccountPublicKey	string	N	多重签名账户公钥
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactionId	string	交易id
    EtmResult addTransaction(String secret, int amount, String recipientId,
                              String senderPublicKey, String secondSecret, String multiSignAccountPublicKey);




}
