package com.meiya.server;

import com.alibaba.fastjson.JSONObject;
import com.meiya.spidertext.netty.test.NettyConfig;
import com.meiya.spidertext.netty.test.NettyFileInfo;
import com.meiya.spidertext.netty.test.NettyFileTransferDTO;
import com.meiya.spidertext.netty.test.NettyMessage;
import com.meiya.spidertext.util.FreeMarkerUtils;
import com.meiya.spidertext.util.XmlUtil;
import com.meiya.spidertext.util.config.Config;
import com.meiya.spidertext.util.constant.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileTransferServerHandler extends ChannelHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        FileOutputStream fos = null;
        NettyFileTransferDTO nettyFileTransferDTO = null;
        try {
            nettyFileTransferDTO = (NettyFileTransferDTO)msg;
            String bodyContent = (String) nettyFileTransferDTO.getBody();
            NettyFileInfo nettyFileInfo = JSONObject.parseObject(bodyContent, NettyFileInfo.class);
            if (nettyFileInfo.getRequestType() == NettyConfig.REQUEST_QUERY) {
                // TO-DO 后面修改为从数据库读取md5值，判断该文件是否已传输
                nettyFileInfo.setFileSend(false);
            } else if (nettyFileInfo.getRequestType() == NettyConfig.REQUEST_UPLOAD) {
                fos = new FileOutputStream("O:\\FS-3000\\trunk\\spiderX_1.zip");
                fos.write(nettyFileTransferDTO.getFileBytes());
                fos.close();

                nettyFileTransferDTO.setFileBytes(null);
                nettyFileInfo.setFileSend(true);
                nettyFileTransferDTO.setBody(JSONObject.toJSONString(nettyFileInfo));
            }
        } finally {
            ctx.writeAndFlush(nettyFileTransferDTO);
        }
    }

    private NettyFileTransferDTO buildRepo(int state) {
        byte[]  bytes = new byte[]{1};
        NettyFileTransferDTO resp = new NettyFileTransferDTO();
        resp.setFileBytes(bytes);
        return resp;
    }


}
