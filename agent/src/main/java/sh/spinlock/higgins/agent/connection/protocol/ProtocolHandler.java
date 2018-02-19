package sh.spinlock.higgins.agent.connection.protocol;

import com.google.protobuf.InvalidProtocolBufferException;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage.RootMessage;

import static sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants.MessageIndex.*;

public class ProtocolHandler {
    public ProtocolHandler() {}

    public final void handleIncoming(byte[] bytes) throws InvalidProtocolBufferException {
        RootMessage rootMessage = RootMessage.parseFrom(bytes);

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

    private void handleAck(RootMessage rootMessage) {
    }

    private void handleHello(RootMessage rootMessage) {
    }
}
