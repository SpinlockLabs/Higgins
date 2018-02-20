package sh.spinlock.higgins.agent.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHostConnection extends HostConnection {
    private Socket socket;
    private SocketThread socketThread;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    @Override
    public void connect() throws IOException {
        socket = new Socket("localhost", 6081);
        socketThread = new SocketThread();
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        socketThread.start();


    }

    @Override
    public void send(byte[] bytes) {
        try {
            outputStream.writeInt(bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Replace with Runnable
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
