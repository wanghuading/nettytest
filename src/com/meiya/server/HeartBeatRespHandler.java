package com.meiya.server;


import com.meiya.spidertext.netty.test.MessageType;
import com.meiya.spidertext.netty.test.NettyMessage;
import com.meiya.spidertext.netty.test.NettyMessageHeader;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatRespHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage nettyMsg = (NettyMessage) msg;
		if(nettyMsg!=null&&nettyMsg.getHeader()!=null){
			if(nettyMsg.getHeader().getType()== MessageType.HEARTBEAT_REQ){
				System.out.println("server recevied client heart beat msg :"+nettyMsg);
				NettyMessage respMsg = buildHeartBeatRespMsg();
				ctx.writeAndFlush(respMsg);
			}else{
				ctx.fireChannelRead(msg);
			}
		}
	}
	
	private NettyMessage buildHeartBeatRespMsg(){
		NettyMessage msg = new NettyMessage();
		msg.setHeader(new NettyMessageHeader());
		msg.getHeader().setType(MessageType.HEARBEAR_RESP);
		return msg;
	}
}
