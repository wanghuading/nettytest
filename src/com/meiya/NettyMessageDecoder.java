package com.meiya;

import com.meiya.spidertext.netty.test.util.MarshallingCodeCFactory;
import com.meiya.spidertext.netty.test.util.NettyMarshallingDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.HashMap;
import java.util.Map;


public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{

	private NettyMarshallingDecoder decoder;
	
	/**
     * Creates a new instance.
     *
     * @param maxFrameLength
     *        the maximum length of the frame.  If the length of the frame is
     *        greater than this value, {@link TooLongFrameException} will be
     *        thrown.
     * @param lengthFieldOffset
     *        the offset of the length field
     * @param lengthFieldLength
     *        the length of the length field
     * @param lengthAdjustment
     *        the compensation value to add to the value of the length field
     * @param initialBytesToStrip
     *        the number of first bytes to strip out from the decoded frame
     */
	
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		decoder = MarshallingCodeCFactory.buildMarshallingDecoder();
	}


	public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
		ByteBuf frame = (ByteBuf)super.decode(ctx, in);
		if(frame == null){
			return null;
		}

		NettyFileTransferDTO nettyFileTransferDTO = new NettyFileTransferDTO();
        int fileByteSize = frame.readInt();
        if (fileByteSize > 0) {
            nettyFileTransferDTO.setFileBytes(getBytes(frame, fileByteSize));
        }


        nettyFileTransferDTO.setBody(decoder.decode(ctx, frame));
		return nettyFileTransferDTO;
	}

    private byte[] getBytes(ByteBuf frame, int length) {
        byte[] bytes = new byte[length];
        frame.readBytes(bytes);
        return bytes;
    }
}
