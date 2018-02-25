package sh.spinlock.higgins.host.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.host.HigginsHost;
import sh.spinlock.higgins.host.agent.Agent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketAgentConnection extends AgentConnection {
    private static final Logger LOG = LogManager.getLogger(SocketAgentConnection.class);

    private Socket socket;
    private SocketThread socketThread;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public SocketAgentConnection(Agent agent) {
        super(agent);
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        socketThread = new SocketThread();
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        socketThread.start();

        ready();
    }

    @Override
    public void send(byte[] bytes) {
        try {
            outputStream.writeInt(bytes.length);
            outputStream.write(bytes);
        } catch (IOException e) {
            LOG.error("Socket IOException", e);
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
            LOG.error("Could not properly close socket");
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
