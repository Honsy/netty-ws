import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.CharsetUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

class NioWebSocketServer {
    private final Logger logger=Logger.getLogger(this.getClass().getName());
    private void init(){
        logger.info("正在启动websocket服务器");
        NioEventLoopGroup boss=new NioEventLoopGroup();
        NioEventLoopGroup work=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new NioWebSocketChannelInitializer());
            Channel channel = bootstrap.bind(8081).sync().channel();
            logger.info("webSocket服务器启动成功："+channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("运行出错："+e);
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("websocket服务器已关闭");
        }
    }

    public static void main(String[] args) {
        new NioWebSocketServer().init();
    }
}

public class Test {
    public static void main(String args[]) throws Exception{

        try {
            GameStart.GameStartReq.Builder pb = GameStart.GameStartReq.newBuilder();
            pb.setGameId(1).setUserId(30501).setRoomId("-1");
            GameStart.GameStartReq msg = pb.build();
            //将数据写入流中
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            msg.writeTo(output);
            byte[] bytes = output.toByteArray();
            System.out.println("person size: "+msg.toByteArray()+msg.toByteString());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
