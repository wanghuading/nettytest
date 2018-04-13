package com.meiya;

import com.meiya.spidertext.netty.test.util.MarshallingCodeCFactory;
import com.meiya.spidertext.netty.test.util.NettyMarshallingEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class NettyMessageEncoder extends MessageToMessageEncoder<NettyFileTransferDTO>{

	private NettyMarshallingEncoder encoder;
	public NettyMessageEncoder(){
		encoder = MarshallingCodeCFactory.buildMarshallingEncoder();
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyFileTransferDTO msg,
			List<Object> out) throws Exception {
		if(msg == null){
			throw new Exception("The encode message is null");
		}

		ByteBuf sendBuf = Unpooled.buffer();

		if (msg.getFileBytes() != null && msg.getFileBytes().length > 1) {
			sendBuf.writeInt(msg.getFileBytes().length);
			sendBuf.writeBytes(msg.getFileBytes());
			int wb = sendBuf.writableBytes();
			System.out.println(wb);
		} else {
			sendBuf.writeInt(0);
		}
		if (StringUtils.isNotEmpty(msg.getBody().toString())) {
			encoder.encode(ctx, msg.getBody(), sendBuf);
		}


		int readableBytes = sendBuf.readableBytes();
		//sendBuf.setInt(0, readableBytes);
		
		out.add(sendBuf);
	}
}
