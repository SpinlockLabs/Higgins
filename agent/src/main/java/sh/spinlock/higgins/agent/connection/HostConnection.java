package sh.spinlock.higgins.agent.connection;

import lombok.Getter;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolHandler;

import java.io.IOException;

public abstract class HostConnection {
    @Getter
    private ProtocolHandler protocolHandler;

    public HostConnection() {
        protocolHandler = new ProtocolHandler();
    }

    public abstract void connect() throws IOException;
    public abstract void send(byte[] bytes);

    public void receive(byte[] bytes) {
        getProtocolHandler().handleIncoming(bytes);
    }

    public void close() {
    }
}
