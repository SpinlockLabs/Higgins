package sh.spinlock.higgins.host.connection;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.host.agent.Agent;
import sh.spinlock.higgins.host.connection.protocol.ProtocolHandler;

public abstract class AgentConnection {
    private static final Logger LOG = LogManager.getLogger(AgentConnection.class);

    @Getter
    private final Agent agent;

    @Getter
    private final ProtocolHandler protocolHandler;

    public AgentConnection(Agent agent) {
        this.agent = agent;
        protocolHandler = new ProtocolHandler();
        protocolHandler.setConnection(this);
    }

    public void send(byte[] bytes) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending {} bytes: {}", bytes.length, bytes);
        }
    }

    public void receive(byte[] bytes) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} bytes: {}", bytes.length, bytes);
        }
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
