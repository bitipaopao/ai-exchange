package com.shinner.data.aiexchange.test;

import com.shinner.data.aiexchange.common.utils.StringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AmationTest {
    private final static Logger logger = LoggerFactory.getLogger(AmationTest.class);

    @Test
    public void send() throws InterruptedException, IOException, URISyntaxException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());
            Channel channel = b.bind(8081).sync().channel();
            URL baseUrl = this.getClass().getClassLoader().getResource("bsdata/");
            if (Objects.nonNull(baseUrl)) {
                URLConnection urlConnection = baseUrl.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                String files = IOUtils.toString(inputStream, StandardCharsets.UTF_8.displayName());
                String[] fileNames = files.split("\\n");
                Arrays.stream(fileNames)
                        .filter(fileName -> !StringUtil.isEmpty(fileName))
                        .forEach(
                                fileName -> {
                                    InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("bsdata/" + fileName);
                                    try {
                                        String jsonData = IOUtils.toString(jsonInputStream, StandardCharsets.UTF_8.displayName());
                                        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(jsonData, CharsetUtil.UTF_8),
                                                new InetSocketAddress("10.110.133.251", 54321)));
                                        logger.info(jsonData);
                                        Thread.sleep(20L);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                        );

            }
            channel.close();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String receiveMessage = msg.content().toString(CharsetUtil.UTF_8);
            System.out.println(receiveMessage);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
