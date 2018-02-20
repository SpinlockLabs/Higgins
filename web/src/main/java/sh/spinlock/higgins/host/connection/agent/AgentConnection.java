package sh.spinlock.higgins.host.connection.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.host.agent.Agent;

public abstract class AgentConnection {
    @Getter
    private Agent agent;

    public AgentConnection(Agent agent) {
        this.agent = agent;
    }

    public abstract void send(byte[] bytes);
    public void receive(byte[] bytes) {}
    public void close() {}

    /**
     * Run once the socket/communication layer is
     * ready to send/receive data.
     */
    @SuppressWarnings("WeakerAccess")
    protected final void ready() {
        getAgent().setReady();
    }
}
