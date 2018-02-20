package sh.spinlock.higgins.agent.connection;

import com.google.protobuf.InvalidProtocolBufferException;
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
        try {
            getProtocolHandler().handleIncoming(bytes);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Invalid protocol!");
            e.printStackTrace();
        }
    }

    public void close() {
    }
}
