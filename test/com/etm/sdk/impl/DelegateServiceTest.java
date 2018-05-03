package com.etm.sdk.impl;

import org.testng.Assert;

import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDK;
import com.etm.sdk.TestData;
import com.etm.sdk.dto.query.DelegateQueryParameters;

public  class DelegateServiceTest {

    @org.testng.annotations.Test
    public void testGetCount() throws Exception {
        Assert.assertNotNull(EtmSDK.Delegate);
        EtmResult result= EtmSDK.Delegate.getDelegatesCount();
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetVoters() throws Exception {
        EtmResult result= EtmSDK.Delegate.getVoters(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateByPublicKey() throws Exception {
        EtmResult result= EtmSDK.Delegate.getDelegateByPublicKey(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateByName() throws Exception {
        EtmResult result= EtmSDK.Delegate.getDelegateByName(TestData.userName);
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegates() throws Exception {
        DelegateQueryParameters query = new DelegateQueryParameters()
                .setLimit(10);
        EtmResult result= EtmSDK.Delegate.queryDelegates(query);
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateFee() throws Exception {
        EtmResult result= EtmSDK.Delegate.getDelegateFee(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetForging() throws Exception {
    }

    @org.testng.annotations.Test
    public void testRegisterDelegate() throws Exception {
        EtmResult result= EtmSDK.Delegate.registerDelegate(TestData.userName, TestData.secret, TestData.secondSecret);
        Assert.assertTrue( result.isSuccessful()||
                "Account is already a delegate".equals(result.getError()));
    }

    @org.testng.annotations.Test
    public void testEnableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testDisableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testGetForgingStatus() throws Exception {
    }
}