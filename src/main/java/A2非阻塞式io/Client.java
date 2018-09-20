package A2非阻塞式io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: cpb
 * @Date: 2018/9/19 18:55
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        try {

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost",8081));

//            发送请求
            ByteBuffer buffer = ByteBuffer.wrap("你好吗".getBytes());
            socketChannel.write(buffer);

//            获取server响应
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int num ;
            if((num = socketChannel.read(readBuffer))> 0 ){
                readBuffer.flip();

                byte[] result = new byte[num];
                readBuffer.get(result);

                String  demo = new String(result,"UTF-8");
                System.out.println("返回值："+demo);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
