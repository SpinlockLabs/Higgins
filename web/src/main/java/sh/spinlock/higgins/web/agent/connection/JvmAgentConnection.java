package sh.spinlock.higgins.web.agent.connection;

import sh.spinlock.higgins.agent.Agent;
import sh.spinlock.higgins.agent.connection.JvmHost;

public class JvmAgentConnection extends RemoteAgentConnection {
    private final Agent agent;

    public JvmAgentConnection() {
        agent = new Agent();
        agent.setConnection(new JvmHost(this));
        Agent.initializeInstance(agent);
    }

    @Override
    public void receive(byte[] bytes) {
    }

    @Override
    public void send(byte[] bytes) {
        agent.getConnection().receive(bytes);
    }
}
