package com.etm.sdk;

import com.etm.sdk.dto.query.PeerQueryParameters;

/**
 * Etm节点接口
 * @author will
 */
public interface Peer extends EtmInterface {
    //接口地址：/api/peers
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //state	integer	N	节点状态,0: ,1:,2:,3:
    //os	string	N	内核版本
    //version	string	N	etm版本号
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N
    //offset	integer	N	偏移量，最小值0
    //port	integer	N	端口，1~65535
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //peers	Array	节点信息json构成的数组
    //totalCount	integer	当前正在运行的节点个数
    EtmResult queryPeers(PeerQueryParameters parameters);

    //接口地址：/api/peers/version
    //请求方式：get
    //支持格式：无
    //请求参数说明：无参数
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //version	string	版本号
    //build	timestamp	构建时间
    //net	string	主链或者测试链
    EtmResult getVersion();

    //接口地址：/api/peers/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //ip	string	Y	待查询节点ip
    //port	integer	Y	待查询节点端口，1~65535
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //peer	json
    EtmResult getPeer(String ip, int port);

}
