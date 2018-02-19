package sh.spinlock.higgins.agent.connection;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolHandler;

public abstract class BaseHost {
    @Getter
    private ProtocolHandler protocolHandler;

    public BaseHost() {
        protocolHandler = new ProtocolHandler();
    }

    public abstract boolean connect();
    public abstract void send(byte[] bytes);

    public void receive(byte[] bytes) {
        try {
            getProtocolHandler().handleIncoming(bytes);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Invalid protocol!");
            e.printStackTrace();
        }
    }
}
