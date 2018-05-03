package com.etm.sdk.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.etm.sdk.EtmResult;
import com.etm.sdk.EtmSDK;
import com.etm.sdk.TestData;
import com.etm.sdk.dto.query.BlockQueryParameters;

/**
 * @author fisher
 * @version $Id: BlockServiceTest.java, v 0.1 2017/8/1 18:49 fisher Exp $
 */
public class BlockServiceTest {

    @Test
    public void testGetBlockById() throws Exception {
        EtmResult result= EtmSDK.Block.getBlockById(TestData.blockId,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlockByHeight()throws Exception{
        EtmResult result= EtmSDK.Block.getBlockByHeight(TestData.blockHeight,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlockByHash()throws Exception{
        EtmResult result= EtmSDK.Block.getBlockByHash(TestData.blockHash,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryBlocks()throws Exception{
        BlockQueryParameters parameters = new BlockQueryParameters();
        //parameters.setGeneratorPublicKey(TestData.publicKey());
        //parameters.setHeight(6890);
        parameters.setLimit(2);
        //parameters.setOffset(0);
        EtmResult result= EtmSDK.Block.queryBlocks(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetHeight()throws Exception{
        EtmResult result= EtmSDK.Block.getHeight();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetFree()throws Exception{
        EtmResult result= EtmSDK.Block.getFree();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetMilestone()throws Exception{
        EtmResult result= EtmSDK.Block.getMilestone();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetReward()throws Exception{
        EtmResult result= EtmSDK.Block.getReward();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetSupply()throws Exception{
        EtmResult result= EtmSDK.Block.getSupply();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetStauts()throws Exception{
        EtmResult result= EtmSDK.Block.getStauts();
        Assert.assertTrue(result.isSuccessful());
    }
}
