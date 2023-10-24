package com.mingyisoft.javademo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 这个例子演示了如何使用 Java 的 AIO API 异步地读取和写入文件。
 * 需要注意的是，AIO API 的用法和传统的阻塞 I/O（BIO）和非阻塞 I/O（NIO）有很大的区别，
 * 因为 AIO 是基于回调（CompletionHandler）的方式进行操作的。
 *
 * 在这个例子中，我们使用 AsynchronousFileChannel 类来进行异步文件的读取和写入，
 * 并通过 CompletionHandler 来处理异步操作完成时的回调。
 * 在异步操作完成后，会调用 completed 方法来处理成功的情况，
 * 或者调用 failed 方法来处理失败的情况。
 */
public class AIODemo1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("test.txt");

        // 异步读取文件
        readFromFile(filePath);

        // 异步写入文件
        writeToFile(filePath, "Hello, AIO!");

        // 等待异步操作完成
        try {
            Thread.sleep(2000); // 等待异步操作完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 异步读取文件
    public static void readFromFile(Path filePath) {
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    System.out.println("Read from file: " + new String(data));
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 异步写入文件
    public static void writeToFile(Path filePath, String data) {
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath)) {
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());

            fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    System.out.println("Write to file completed.");
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
