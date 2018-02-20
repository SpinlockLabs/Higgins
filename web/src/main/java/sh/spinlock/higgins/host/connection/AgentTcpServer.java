package sh.spinlock.higgins.host.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.host.HigginsHost;
import sh.spinlock.higgins.host.agent.Agent;
import sh.spinlock.higgins.host.connection.agent.SocketAgentConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class AgentTcpServer {
    private static final Logger LOG = LogManager.getLogger(AgentTcpServer.class);

    private final int port;
    private ServerSocket serverSocket;
    private Thread serverThread;

    public AgentTcpServer(int port) {
        this.port = port;
        serverThread = new Thread(new ServerThread(), "TCP Server");
    }

    public void start() throws IOException {
        LOG.info("Starting Agent TCP Server on port {}", port);
        serverSocket = new ServerSocket(port);
        serverThread.start();
    }

    public void stop() {
        LOG.info("Stopping Agent TCP Server");
        try {
            serverSocket.close();
            serverThread.join();
        } catch (InterruptedException | IOException e) {
            LOG.error("Failed to properly stop Agent TCP Server", e);
        }
    }

    private class ServerThread implements Runnable {
        public void run() {
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();

                    String host = socket.getInetAddress().getCanonicalHostName();
                    int port = socket.getPort();
                    String hostPort = String.format("%s:%s", host, port);

                    LOG.info("Accepting new Agent connection ({})", hostPort);

                    // Initialize new Agent and set temporary name
                    Agent agent = new Agent();
                    agent.setName(hostPort);

                    // Set up Connection, and register with Agent
                    SocketAgentConnection connection = new SocketAgentConnection(agent);
                    agent.setConnection(connection);
                    connection.setSocket(socket);

                    // Register Agent
                    HigginsHost.getInstance().getAgentManager().addAgent(agent);
                } catch (SocketException e) {
                    LOG.warn("Socket closed");
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }
    }
}
