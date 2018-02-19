package sh.spinlock.higgins.agent.connection;

import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

public class JvmHost extends BaseHost {
    private final JvmAgentConnection agentConnection;

    public JvmHost(JvmAgentConnection agentConnection) {
        this.agentConnection = agentConnection;
    }

    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public void receive(byte[] bytes) {
    }

    @Override
    public void send(byte[] bytes) {
        agentConnection.receive(bytes);
    }
}
