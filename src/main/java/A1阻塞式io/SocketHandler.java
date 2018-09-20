package A1阻塞式io;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: cpb
 * @Date: 2018/9/19 18:41
 * @Description:
 */
public class SocketHandler implements Runnable{

    private SocketChannel socketChannel;

    public SocketHandler(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    public void run() {
//      堆空间中分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
//            将请求数据读入Buffer中
            int num ;
//            如果有数据
            while ((num = socketChannel.read(buffer))>0){
//                读取Buffer内容之前先flip一下
                // Buffer 切换为读模式
                buffer.flip();

//               加快读取速度
                byte[] bytes = new byte[num];

//                读取数据
                buffer.get(bytes);

                String result = new String (bytes,"UTF-8");
                System.out.println("收到请求："+result);

//                回复客户端
//                warp用于存放在byte数组
                ByteBuffer writeBuffer = ByteBuffer.wrap(("我已经收到你的请求，你的请求内容是：" + result).getBytes());
                socketChannel.write(writeBuffer);

                buffer.clear();


            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
