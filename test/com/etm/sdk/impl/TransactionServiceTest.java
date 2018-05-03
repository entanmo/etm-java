package com.etm.sdk.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDK;
import com.etm.sdk.TestData;
import com.etm.sdk.TransactionType;
import com.etm.sdk.dto.query.QueryParameters;
import com.etm.sdk.dto.query.TransactionQueryParameters;

/**
 * @author fisher
 * @version $Id: TransactionServiceTest.java, v 0.1 2017/8/7 15:16 fisher Exp $
 */
public class TransactionServiceTest {
    @BeforeSuite
    public void SetUp(){
        EtmSDK.Config.setEtmServer(TestData.root);
    }

    @Test
    public void testQueryTransactionsEmpty() throws Exception {
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByBlockId() throws Exception {
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setBlockId("0dc51ce12b5a58f206f15cb546a29375c30e7cfce31f95fa9244133fc894ea33");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByLimit()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setLimit(10);
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByType()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setTransactionType(TransactionType.Transfer);
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }
    @Test
    // TODO: 2017/8/8 orderBy condition error
    public void testQueryTransactionsByOffset()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setOffset(0);
        parameters.setOrderBy("amount", QueryParameters.SortOrder.DESC);
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsBySendPublicKey()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setSenderPublicKey("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByOwnPublicKey()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setOwnerPublicKey("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    // TODO: 2017/8/8 test error 
    @Test
    public void testQueryTransactionsByOwnerAddress()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setOwnerAddress("4516770862894053894");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsBySenderId()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setSenderId("16502644983291819723");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByRecipientId()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setRecipientId("14762548536863074694");
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    // TODO: 2017/8/9 ambiguous condition (amount,fee)
    @Test
    public void testQueryTransactionsByAmount()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setAmount(100L);
        EtmResult result = EtmSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetTransactions()throws Exception{
        EtmResult result = EtmSDK.Transaction.getTransaction(TestData.transactionId);
        Assert.assertTrue(result.isSuccessful());
    }

    // TODO: 2017/8/9 The time is too short for testing unconfirm transaction.
    @Test
    public void testGetUnconfirmedTransaction()throws Exception{
        EtmResult result =EtmSDK.Transaction.getUnconfirmedTransaction(TestData.transactionId);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetUnconfirmedTransactions()throws Exception{
        EtmResult result =EtmSDK.Transaction.getUnconfirmedTransactions(null, null);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetUnconfirmedTransactionsByConditions()throws Exception{
        EtmResult result = EtmSDK.Transaction.getUnconfirmedTransactions("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced","ACsJqgmPRjoHm6fb3EswuWoaxu4KRAjv4R");
        Assert.assertTrue(result.isSuccessful());
    }

    // TODO: 2017/8/14 interface not found 
    @Test
    public void testAddTransaction()throws Exception{
        EtmResult result =  EtmSDK.Transaction.addTransaction(TestData.secret,
                1,"APS3jUBQZCaeB5qQ5TzEiCdXzerWtkZGr7",TestData.senderPublicKey,
                TestData.secondSecret,null);
        Assert.assertTrue(result.isSuccessful());
    }
}
