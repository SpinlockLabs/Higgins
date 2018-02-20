package sh.spinlock.higgins.host.connection;

import sh.spinlock.higgins.host.HigginsHost;
import sh.spinlock.higgins.host.agent.Agent;
import sh.spinlock.higgins.host.connection.agent.SocketConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AgentTcpServer {

    private ServerSocket serverSocket;
    private ServerThread serverThread;

    public AgentTcpServer() {
        serverThread = new ServerThread();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(6081);
        serverThread.start();
    }

    public void stop() throws InterruptedException, IOException {
        // TODO
    }

    // TODO: Replace with Runnable
    private class ServerThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();

                    if (socket != null) {
                        SocketConnection socketConnection = new SocketConnection();
                        socketConnection.setSocket(socket);
                        Agent agent = new Agent(socketConnection);
                        HigginsHost.getInstance().getAgentManager().addAgent(agent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
