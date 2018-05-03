package com.etm.sdk.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDK;
import com.etm.sdk.TestData;
import com.etm.sdk.dto.query.QueryParameters;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * AccountService Tester.
 *
 * @author eagle
 * @since <pre>07/16/2017</pre>
 * @version 1.0
 */
public class AccountServiceTest {
    @BeforeSuite
    public void SetUp(){
        EtmSDK.Config.setEtmServer(TestData.root);
    }

    @Test
    public void testLogin() throws Exception {
        EtmResult result= EtmSDK.Account.login(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testSecureLogin() throws Exception {
        EtmResult result= EtmSDK.Account.secureLogin(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetAccount() throws Exception {
        EtmResult result= EtmSDK.Account.getAccount(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBalance() throws Exception {
        EtmResult result= EtmSDK.Account.getBalance(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetPublicKey() throws Exception {
        EtmResult result= EtmSDK.Account.getPublicKey(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGeneratePublicKey() throws Exception {
        EtmResult result= EtmSDK.Account.generatePublicKey(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testVote() throws Exception {
        EtmResult result= EtmSDK.Account.vote(
                TestData.voted,
                TestData.canceled,
                TestData.secret,
                TestData.secondSecret
        );

        Assert.assertTrue(result.isSuccessful() ||
                "account has already voted for this delegate".equals(result.getError())||
                "Failed to remove vote, account has not voted for this delegate".equals(result.getError()));
    }

    @Test
    public void testTransfer() throws Exception {
        EtmResult result= EtmSDK.Account.transfer(
                TestData.targetAddress,
                EtmSDK.Helper.amountForCoins(1),
                "Transfer by Test",
                TestData.secret,
                TestData.secondSecret);

        Assert.assertTrue(result.isSuccessful() && result.getRawJson().contains("transactionId"));
    }

    @Test
    public void testGetDelegatesFee() throws Exception {
        EtmResult result= EtmSDK.Account.getDelegatesFee();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetVotedDelegates() throws Exception {
        EtmResult result= EtmSDK.Account.getVotedDelegates(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetTopAccounts() throws Exception {
        QueryParameters parameters = new QueryParameters()
                .setLimit(50)
                .setOffset(0);

        EtmResult result= EtmSDK.Account.getTopAccounts(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void main() throws IOException {
        File a = FileUtils.getFile("D:/a.txt");
        String str = FileUtils.readFileToString(a,"utf-8");
        JSONObject json = JSON.parseObject(str);
        JSONArray arry = JSONArray.parseArray(json.get("delegates").toString());
        for(int i = 0;i<arry.size();i++){
            JSONObject obj= (JSONObject) arry.get(i);
            String username =  obj.getString("username");
            String productivity =  obj.getString("productivity");
            String producedblocks = obj.getString("producedblocks");
            String balance = obj.getString("balance");
            String approval = obj.getString("approval");
            System.out.println(username +"||" + productivity +"||" + producedblocks + "||" + balance + "||" + approval);
        }
    }
}
