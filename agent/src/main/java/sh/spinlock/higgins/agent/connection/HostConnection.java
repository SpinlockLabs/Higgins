package sh.spinlock.higgins.agent.connection;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolHandler;

import java.io.IOException;

public abstract class HostConnection {
    private static final Logger LOG = LogManager.getLogger(HostConnection.class);

    @Getter
    private ProtocolHandler protocolHandler;

    public HostConnection() {
        protocolHandler = new ProtocolHandler();
    }

    public void connect() throws IOException {}
    public void close() {}

    public void send(byte[] bytes) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending {} bytes: {}", bytes.length, bytes);
        }
    }

    public void receive(byte[] bytes) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} bytes: {}", bytes.length, bytes);
        }
        getProtocolHandler().handleIncoming(bytes);
    }
}
