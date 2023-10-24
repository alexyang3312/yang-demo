package com.mingyisoft.javademo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIODemo1 {

    public static void main(String[] args) throws IOException {
        // 创建一个 Selector
        Selector selector = Selector.open();

        // 创建一个 ServerSocketChannel，并绑定到指定端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);

        // 将 ServerSocketChannel 注册到 Selector，并指定感兴趣的事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port 8080...");

        while (true) {
            // 调用 Selector 的 select() 方法进行事件轮询
            selector.select();

            // 获取发生事件的 SelectionKey 集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            // 遍历处理发生的事件
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    // 如果是 OP_ACCEPT 事件，则说明有新的连接进来
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("Accepted connection from: " + clientChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    // 如果是 OP_READ 事件，则说明有通道可读
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead == -1) {
                        // 如果返回 -1，则说明通道已经关闭，需要关闭通道并取消 SelectionKey 的注册
                        clientChannel.close();
                        key.cancel();
                        System.out.println("Connection closed by client: " + clientChannel.getRemoteAddress());
                    } else if (bytesRead > 0) {
                        // 如果读取到数据，则处理数据
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String receivedData = new String(bytes);
                        System.out.println("Received data from " + clientChannel.getRemoteAddress() + ": " + receivedData);
                    }
                }
            }
        }
    }
}
