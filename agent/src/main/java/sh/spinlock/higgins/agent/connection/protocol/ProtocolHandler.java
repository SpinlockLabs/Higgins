package sh.spinlock.higgins.agent.connection.protocol;

import com.google.protobuf.InvalidProtocolBufferException;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage.RootMessage;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolMessages.*;

import static sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants.MessageIndex.*;

public class ProtocolHandler {
    public ProtocolHandler() {}

    public final void handleIncoming(byte[] bytes) throws InvalidProtocolBufferException {
        RootMessage rootMessage = RootMessage.parseFrom(bytes);

        try {
            handleRootMessage(rootMessage);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Could not parse protobuf message");
            e.printStackTrace();
        }
    }

    private void handleRootMessage(RootMessage rootMessage)  throws InvalidProtocolBufferException {
        switch (rootMessage.getType()) {
            case ACK:
                handleAck(rootMessage);
                break;
            case HELLO:
                handleHello(rootMessage);
                break;
            case AUTH:
                // TODO: Client should never receive this.
                break;
            case AGENT_INFO:
                // TODO: Client should never receive this.
                break;
        }
    }

    private void handleAck(RootMessage rootMessage) throws InvalidProtocolBufferException {
    }

    private void handleHello(RootMessage rootMessage) throws InvalidProtocolBufferException {
        HelloMessage message = HelloMessage.parseFrom(rootMessage.getMessage());

        System.out.println("Server sent Hello!");
        System.out.println("Needs Auth: " + message.getNeedsAuth());
        System.out.println("Protocol Version: " + message.getProtocolVersion());
    }
}
