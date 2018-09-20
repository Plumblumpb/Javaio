package A5异步IO;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @Auther: cpb
 * @Date: 2018/9/20 15:14
 * @Description:
 */


public class Client {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        // 来个 Future 形式的
        Future<?> future = client.connect(new InetSocketAddress(8082));
        // 阻塞一下，等待连接成功
        future.get();

        Attachment att = new Attachment();
        att.setClient(client);
        att.setReadMode(false);
        att.setBuffer(ByteBuffer.allocate(1024));
        byte[] data = "你好!".getBytes();
        att.getBuffer().put(data);
        att.getBuffer().flip();

        // 异步发送数据到服务端
        client.write(att.getBuffer(), att, new ClientChannelHandler());

        // 这里休息一下再退出，给出足够的时间处理数据
        Thread.sleep(2000);
    }
}
