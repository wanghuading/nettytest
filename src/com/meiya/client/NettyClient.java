package com.meiya.client;

import com.meiya.NettyConfig;
import com.meiya.NettyMessageDecoder;
import com.meiya.NettyMessageEncoder;
import com.meiya.spidertext.netty.test.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class NettyClient {
	private EventLoopGroup group = new NioEventLoopGroup();
	private Executor executor = Executors.newScheduledThreadPool(1);
	public void connect(String host, int port, final NettyFileDTO nettyFileDTO) throws Exception{
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch)
						throws Exception {
					ch.pipeline()
							.addLast(new NettyMessageDecoder(100*1024*1024, 0, 4, 0, 4))
							.addLast(new StringDecoder())
							.addLast(new LengthFieldPrepender(4))
							.addLast(new StringEncoder())
						.addLast(new NettyMessageEncoder())
						//.addLast(new ReadTimeoutHandler(50))
//						.addLast(new LoginAuthReqHandler())
//						.addLast(new HeartBeatReqHandler());
						.addLast(new FileTransferClientHandler(nettyFileDTO));
				}
			});
			System.out.println("client start");
			ChannelFuture future = bootstrap.connect(NettyConfig.HOST, NettyConfig.PORT).sync();
			future.channel().closeFuture().sync();
		}finally{
			/*executor.execute(new Runnable(){
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						try {
							connect(NettyConfig.HOST, NettyConfig.PORT);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			})*/;
		}
	}
	public static void main(String[] args) {
		try {
			NettyFileDTO nettyFileDTO = new NettyFileDTO();
			nettyFileDTO.setDeviceType("type");
			nettyFileDTO.setDeviceSerialNum("serialNum");
			nettyFileDTO.setDeviceLocationCode("locationCode");
			nettyFileDTO.setFile(new File("O:\\FS-3000\\trunk\\spiderX.zip"));
			new NettyClient().connect(NettyConfig.HOST, NettyConfig.PORT, nettyFileDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
