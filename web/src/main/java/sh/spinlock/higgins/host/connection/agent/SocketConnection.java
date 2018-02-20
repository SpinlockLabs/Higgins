package sh.spinlock.higgins.host.connection.agent;

import sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolMessages;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage;
import sh.spinlock.higgins.host.HigginsHost;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketConnection extends AgentConnection {
    private Socket socket;
    private SocketThread socketThread;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        socketThread = new SocketThread();
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        socketThread.start();

        ProtocolMessages.HelloMessage.Builder helloMessage = ProtocolMessages.HelloMessage.newBuilder();
        helloMessage.setNeedsAuth(true);
        helloMessage.setProtocolVersion(ProtocolConstants.VERSION);

        ProtocolRootMessage.RootMessage.Builder rootMessage = ProtocolRootMessage.RootMessage.newBuilder();
        rootMessage.setId(1);
        rootMessage.setType(1);
        rootMessage.setMessage(helloMessage.build().toByteString());

        byte[] messageBytes = rootMessage.build().toByteArray();
        send(messageBytes);
    }

    @Override
    public void send(byte[] bytes) {
        try {
            outputStream.writeInt(bytes.length);
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (Thread.currentThread() != socketThread) {
                socketThread.join();
            }
            socket.close();
            HigginsHost.getInstance().getAgentManager().removeAgent(getAgent());
        } catch (InterruptedException | IOException e) {
            // TODO
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
