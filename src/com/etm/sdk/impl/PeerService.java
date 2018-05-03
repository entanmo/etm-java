package com.etm.sdk.impl;

import com.etm.sdk.EtmResult;
import com.etm.sdk.Peer;
import com.etm.sdk.dbc.Argument;
import com.etm.sdk.dto.query.PeerQueryParameters;

public class PeerService extends EtmRESTService implements Peer {
    @Override
    public EtmResult queryPeers(PeerQueryParameters parameters){
        try {
            Argument.require(Validation.isValidPeerQueryParameters(parameters), "invalid parameters");

            return get(EtmServiceUrls.Peer.QUERY_PEERS, parametersFromObject(parameters));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public EtmResult getVersion() {
        return get(EtmServiceUrls.Peer.GET_VERSION);
    }

    @Override
    public EtmResult getPeer(String ip, int port) {
        try {
            Argument.require(Validation.isValidIP(ip), "invalid ip");
            Argument.require(Validation.isValidPort(port), "invalid port");

            ParameterMap parameters = new ParameterMap()
                    .put("ip", ip)
                    .put("port", port);

            return get(EtmServiceUrls.Peer.GET_PEER, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
