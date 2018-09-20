package A5异步IO;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @Auther: cpb
 * @Date: 2018/9/20 15:22
 * @Description:
 */
public class Attachment {
    private AsynchronousServerSocketChannel server;
    private AsynchronousSocketChannel client;
    private boolean isReadMode;
    private ByteBuffer buffer;

    public AsynchronousServerSocketChannel getServer() {
        return server;
    }

    public void setServer(AsynchronousServerSocketChannel server) {
        this.server = server;
    }

    public AsynchronousSocketChannel getClient() {
        return client;
    }

    public void setClient(AsynchronousSocketChannel client) {
        this.client = client;
    }

    public boolean isReadMode() {
        return isReadMode;
    }

    public void setReadMode(boolean readMode) {
        isReadMode = readMode;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
