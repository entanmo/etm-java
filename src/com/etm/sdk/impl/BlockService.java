package com.etm.sdk.impl;


import com.etm.sdk.Block;
import com.etm.sdk.EtmResult;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dto.query.BlockQueryParameters;

public class BlockService extends EtmRESTService implements Block {
    @Override
    public EtmResult getBlockById(String id, boolean fullBlockInfo) {
        try {
            Argument.notNull(id, "id is null");

            ParameterMap parameters = new ParameterMap().put("id", id);
            String url = fullBlockInfo ?
                    EtmServiceUrls.Block.GET_FULL_BLOCK_INFO:
                    EtmServiceUrls.Block.GET_BLOCK_INFO ;
            return get(url, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public EtmResult getBlockByHeight(long height, boolean fullBlockInfo) {
        ParameterMap parameters = new ParameterMap().put("height", height);
        String url = fullBlockInfo ?
                EtmServiceUrls.Block.GET_FULL_BLOCK_INFO:
                EtmServiceUrls.Block.GET_BLOCK_INFO ;
        return get(url, parameters);
    }

    @Override
    public EtmResult getBlockByHash(String hash, boolean fullBlockInfo) {
        try {
            Argument.notNull(hash, "hash is null");

            ParameterMap parameters = new ParameterMap().put("hash", hash);
            String url = fullBlockInfo ?
                    EtmServiceUrls.Block.GET_FULL_BLOCK_INFO :
                    EtmServiceUrls.Block.GET_BLOCK_INFO;
            return get(url, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }


    @Override
    public EtmResult queryBlocks(BlockQueryParameters parameters) {
        try {
            //Argument.require(Validation.isValidBlockQueryParameters(parameters), "invalid parameters");

            ParameterMap query = parametersFromObject(parameters);
            return get(EtmServiceUrls.Block.QUERY_BLOCKS,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getHeight() {
        return get(EtmServiceUrls.Block.GET_HEIGHT);
    }

    @Override
    public EtmResult getFree() {
        return get(EtmServiceUrls.Block.GET_FREE);
    }

    @Override
    public EtmResult getMilestone() {
        return get(EtmServiceUrls.Block.GET_MILESTONE);
    }

    @Override
    public EtmResult getReward() {
        return get(EtmServiceUrls.Block.GET_REWARD);
    }

    @Override
    public EtmResult getSupply() {
        return get(EtmServiceUrls.Block.GET_SUPPLY);
    }


    @Override
    public EtmResult getStauts() {
        return get(EtmServiceUrls.Block.GET_STATUS);
    }
}
