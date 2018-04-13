package com.meiya.server;

import com.meiya.spidertext.netty.test.NettyConfig;
import com.meiya.spidertext.netty.test.NettyMessageDecoder;
import com.meiya.spidertext.netty.test.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;


public class NettyServer {

	public void bind(String host, int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.option(ChannelOption.SO_BACKLOG, 100);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.handler(new LoggingHandler(LogLevel.INFO));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline()
							.addLast(new NettyMessageDecoder(100*1024*1024, 0, 4, 0, 4))
							.addLast(new StringDecoder())
							.addLast(new LengthFieldPrepender(4))
							.addLast(new StringEncoder())
							.addLast(new NettyMessageEncoder())
//					.addLast(new ReadTimeoutHandler(50))
//					.addLast(new LoginAuthRespHandler())
//					.addLast(new HeartBeatRespHandler());
					.addLast(new FileTransferServerHandler());
				}
			});
			System.out.println("server start!");
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		new NettyServer().bind(NettyConfig.HOST, NettyConfig.PORT);
	}
}
