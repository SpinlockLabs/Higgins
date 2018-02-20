package sh.spinlock.higgins.agent.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHostConnection extends HostConnection {
    private static final Logger LOG = LogManager.getLogger(SocketHostConnection.class);

    private final String host;
    private final int port;
    private Socket socket;
    private SocketThread socketThread;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public SocketHostConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() throws IOException {
        LOG.debug("Connecting to {}:{}", host, port);
        socket = new Socket(host, port);
        socketThread = new SocketThread();
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        LOG.debug("Starting socket thread");
        socketThread.start();
    }

    @Override
    public void close() {
        try {
            if (Thread.currentThread() != socketThread) {
                socketThread.join();
            }
            socket.close();
        } catch (InterruptedException e) {
            LOG.warn(e);
        } catch (IOException e) {
            LOG.warn("Socket was not closed properly.", e);
        }
    }

    @Override
    public void send(byte[] bytes) {
        super.send(bytes);
        try {
            outputStream.writeInt(bytes.length);
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Replace with Runnable
    // TODO: Name Thread
    private class SocketThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    int len = inputStream.readInt();
                    byte[] bytes = new byte[len];
                    inputStream.read(bytes, 0, len);
                    receive(bytes);
                } catch (IOException e) {
                    break;
                }
            }

            close();
        }
    }
}
