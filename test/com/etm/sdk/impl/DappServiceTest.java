package com.etm.sdk.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDK;
import com.etm.sdk.TestData;
import com.etm.sdk.dto.query.DappQureyParameters;

/**
 * @author fisher
 * @version $Id: DappServiceTest.java, v 0.1 2017/12/1 18:19 fisher Exp $
 */
public class DappServiceTest {
    @BeforeSuite
    public void SetUp(){
        EtmSDK.Config.setEtmServer(TestData.root);
    }

    @Test
    public void testGetBlocksHeight() throws Exception {
        EtmResult result= EtmSDK.Dapp.getBlocksHeight(TestData.dappId);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlocks() throws Exception {
        DappQureyParameters parameters = new DappQureyParameters();
        parameters.setLimit(10);
        EtmResult result= EtmSDK.Dapp.getBlocks(TestData.dappId,parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetAccount() throws Exception {
        EtmResult result= EtmSDK.Dapp.getAccount(TestData.dappId,TestData.dappAddress);
        Assert.assertTrue(result.isSuccessful());
    }


    @Test
    public void testDappDeposit() throws Exception {
        EtmResult result= EtmSDK.Dapp.dappDeposit(TestData.dappId,"ETM",2000000000,TestData.secret,null);
        Assert.assertTrue(result.isSuccessful());
    }



}
