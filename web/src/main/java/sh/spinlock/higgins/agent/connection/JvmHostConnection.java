package sh.spinlock.higgins.agent.connection;

import sh.spinlock.higgins.host.connection.JvmAgentConnection;

public class JvmHostConnection extends HostConnection {
    private final JvmAgentConnection agentConnection;

    public JvmHostConnection(JvmAgentConnection agentConnection) {
        this.agentConnection = agentConnection;
    }

    @Override
    public void connect() {
    }

    @Override
    public void receive(byte[] bytes) {
    }

    @Override
    public void send(byte[] bytes) {
        agentConnection.receive(bytes);
    }
}
