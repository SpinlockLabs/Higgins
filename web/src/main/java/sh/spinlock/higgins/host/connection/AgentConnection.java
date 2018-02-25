package sh.spinlock.higgins.host.connection;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.host.agent.Agent;
import sh.spinlock.higgins.host.connection.protocol.ProtocolHandler;

public abstract class AgentConnection {
    @Getter
    private final Agent agent;

    @Getter
    private final ProtocolHandler protocolHandler;

    public AgentConnection(Agent agent) {
        this.agent = agent;
        protocolHandler = new ProtocolHandler();
        protocolHandler.setConnection(this);
    }

    public abstract void send(byte[] bytes);

    public void receive(byte[] bytes) {
        protocolHandler.handleIncoming(bytes);
    }

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
