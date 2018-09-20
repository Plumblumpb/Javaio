package A1阻塞式io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * @Auther: cpb
 * @Date: 2018/9/19 18:32
 * @Description:
 */
public class Server {
    public static void main(String[] args) throws IOException {

//        开启Socket
        ServerSocketChannel serversocketchannel = ServerSocketChannel.open();

//        开启端口
        SocketAddress  socketAddress = new InetSocketAddress(8080);
//        监听8080 端口进来的tcp连接
        serversocketchannel.socket().bind(socketAddress);

//        开启循环监听
        while(true){
//            这里会阻塞，直到有请求连接
            SocketChannel socketChannel = serversocketchannel.accept();

//            开启新的线程来处理这个请求，然后while循环监听8080端口
            SocketHandler handler = new SocketHandler(socketChannel);
            new Thread(handler).start();
        }

    }
}
