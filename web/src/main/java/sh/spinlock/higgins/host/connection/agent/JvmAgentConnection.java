package sh.spinlock.higgins.host.connection.agent;

import sh.spinlock.higgins.agent.HigginsAgent;
import sh.spinlock.higgins.agent.connection.JvmHostConnection;

public class JvmAgentConnection extends AgentConnection {
    private final HigginsAgent agent;

    public JvmAgentConnection() {
        agent = new HigginsAgent();
        agent.setConnection(new JvmHostConnection(this));
        HigginsAgent.initializeInstance(agent);
    }

    @Override
    public void receive(byte[] bytes) {
    }

    @Override
    public void send(byte[] bytes) {
        agent.getConnection().receive(bytes);
    }
}
