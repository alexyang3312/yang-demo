package com.mingyisoft.javademo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 先启动NioServer，再启动NioClient，
 * 在NioClient的控制台上可以输入字符，回车后，
 * 在NioServer的控制台上可以看到。
 */
public class NioClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) throws IOException {
        // 创建 SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

        while (!socketChannel.finishConnect()) {
            // 等待连接完成
        }

        System.out.println("Connected to server: " + SERVER_HOST + ":" + SERVER_PORT);

        Scanner scanner = new Scanner(System.in);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            System.out.print("Enter a message to send (type 'exit' to quit): ");
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }

            buffer.clear();
            buffer.put(message.getBytes());
            buffer.flip();
            while (buffer.hasRemaining()) {
                // 发送消息
                socketChannel.write(buffer);
            }
        }

        socketChannel.close();
        scanner.close();
    }
}

