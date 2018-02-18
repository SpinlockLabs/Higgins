package sh.spinlock.higgins.agent.connection;

import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

public class JvmHost extends BaseHost {
    private JvmAgentConnection agentConnection;

    public JvmHost(JvmAgentConnection agentConnection) {
        this.agentConnection = agentConnection;
    }

    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public void receiveString(String str) {
        System.out.println("Received from host: " + str);
        sendString(str + " | P.S. Hi! This is from the agent!");
    }

    @Override
    public void sendString(String str) {
        agentConnection.receiveString(str);
    }
}
