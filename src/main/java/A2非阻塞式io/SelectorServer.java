package A2非阻塞式io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: cpb
 * @Date: 2018/9/20 13:49
 * @Description:
 */
public class SelectorServer {
    public static void main(String[] args) {
        try{
//            创建通道选择器。
            Selector selector = Selector.open();
//            创建通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8081));

//            将通道注册到selector上
//            如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            serverSocketChannel.configureBlocking(false);
//            可以使用数组组合
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            /**
            SelectionKey.OP_READ
            对应 00000001，通道中有数据可以进行读取
            SelectionKey.OP_WRITE
            对应 00000100，可以往通道中写入数据
            SelectionKey.OP_CONNECT
            对应 00001000，成功建立 TCP 连接
            SelectionKey.OP_ACCEPT
            对应 00010000，接受 TCP 连接
            **/

            while (true) {
//              获取通道数目
                int readyChannels = selector.select();
                if(readyChannels == 0){
                    continue;
                }
//                获取SelectionKey既对通道的操作
                Set<SelectionKey>  readyKey = selector.selectedKeys();
//                遍历
                Iterator<SelectionKey> iterator = readyKey.iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
//                    if(key.isAcceptable()) {
//                        // a connection was accepted by a ServerSocketChannel.
//                    } else if (key.isConnectable()) {
//                        // a connection was established with a remote server.
//                    } else if (key.isReadable()) {
//                        // a channel is ready for reading
//                    } else if (key.isWritable()) {
//                        // a channel is ready for writing
//                    }

                    if (key.isAcceptable()) {
                        // 有已经接受的新的到服务端的连接
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        // 有新的连接并不代表这个通道就有数据，
                        // 这里将这个新的 SocketChannel 注册到 Selector，监听 OP_READ 事件，等待数据
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 有数据可读
                        // 上面一个 if 分支中注册了监听 OP_READ 事件的 SocketChannel
                        SocketChannel socketChannel = (SocketChannel) key.channel();
//                        创建缓存空间
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int num = socketChannel.read(readBuffer);
                        if (num > 0) {
                            // 处理进来的数据...
                            System.out.println("收到数据：" + new String(readBuffer.array()).trim());
                            ByteBuffer buffer = ByteBuffer.wrap("返回给客户端的数据...".getBytes());
                            socketChannel.write(buffer);
                        } else if (num == -1) {
                            // -1 代表连接已经关闭
                            socketChannel.close();
                        }
                    }
                }
            }
        }catch(IOException e){
//            key.cancel();
//            channel.socket().close();
//            channel.close();
            return;
        }

    }
}
