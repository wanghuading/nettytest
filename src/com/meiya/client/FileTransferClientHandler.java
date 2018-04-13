package com.meiya.client;

import com.alibaba.fastjson.JSONObject;
import com.meiya.spidertext.netty.test.NettyConfig;
import com.meiya.spidertext.netty.test.NettyFileInfo;
import com.meiya.spidertext.netty.test.NettyFileTransferDTO;
import com.meiya.spidertext.util.Md5Util;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileTransferClientHandler extends ChannelHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(FileTransferClientHandler.class);

    private NettyFileDTO nettyFileDTO;
    public FileTransferClientHandler() {};
    public FileTransferClientHandler(NettyFileDTO nettyFileDTO) {
        this.nettyFileDTO = nettyFileDTO;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 先查询语音文件是否已发送到管理平台
        ctx.writeAndFlush(buildMessage());
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        NettyFileTransferDTO nettyFileTransferDTO = (NettyFileTransferDTO) msg;
        NettyFileInfo nettyFileInfo = JSONObject.parseObject(nettyFileTransferDTO.getBody().toString(), NettyFileInfo.class);
        if (!nettyFileInfo.isFileSend()) { // 查询得知文件未发送时，发送文件
            try {
                FileInputStream fis = new FileInputStream(nettyFileDTO.getFile());
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                fis.close();
                // 读取文件字节数
                nettyFileInfo.setRequestType(NettyConfig.REQUEST_UPLOAD);
                nettyFileInfo.setByteSize(bytes.length);
                nettyFileTransferDTO.setFileBytes(bytes);
                nettyFileTransferDTO.setBody(JSONObject.toJSONString(nettyFileInfo));
            } catch (Exception e) {
                logger.error("读取文件" + nettyFileDTO.getFile().getName() + "发生错误");
            }
            ctx.writeAndFlush(nettyFileTransferDTO);
        } else {
            logger.info("文件已上传");
            ctx.close();
        }
    }



    private NettyFileTransferDTO buildMessage() throws Exception{
        NettyFileTransferDTO nettyFileTransferDTO = new NettyFileTransferDTO();
        NettyFileInfo nettyFileInfo = new NettyFileInfo();
        nettyFileInfo.setDeviceType(nettyFileDTO.getDeviceType());
        nettyFileInfo.setDeviceSerialNum(nettyFileDTO.getDeviceSerialNum());
        nettyFileInfo.setDeviceLocationCode(nettyFileDTO.getDeviceLocationCode());
        nettyFileInfo.setByteSize(0);
        nettyFileInfo.setName(nettyFileDTO.getFile().getName());
        nettyFileInfo.setMd5(Md5Util.fullFileMd5(nettyFileDTO.getFile()));

        String body = JSONObject.toJSONString(nettyFileInfo);
        nettyFileTransferDTO.setBody(body);
        return nettyFileTransferDTO;
    }
}
