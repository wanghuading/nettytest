package com.meiya;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;

/**
 * Created by wanghd on 2017/8/10.
 */
public class NettyChunkedWriteHandler extends ChunkedWriteHandler {
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(new ChunkedFile(new File("O:\\FS-3000\\trunk\\spider_29378_3.1.11301_npi.zip")));
        ctx.fireChannelActive();
    }
}
