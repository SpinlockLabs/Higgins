package sh.spinlock.higgins.agent.connection.protocol;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage.RootMessage;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolMessages.*;

import static sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants.MessageIndex.*;

public class ProtocolHandler {
    private static final Logger LOG = LogManager.getLogger(ProtocolHandler.class);

    public ProtocolHandler() {}

    public final void handleIncoming(byte[] bytes) {
        try {
            RootMessage rootMessage = RootMessage.parseFrom(bytes);

            if (LOG.isDebugEnabled()) {
                LOG.debug("RootMessage(id={},type={})", rootMessage.getId(), rootMessage.getMessage());
            }

            handleRootMessage(rootMessage);
        } catch (InvalidProtocolBufferException e) {
            LOG.error("Could not parse root message from Protobuf", e);
            // TODO: Disconnect agent from server.
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
            default:
                LOG.error("Agent received unknown message type (id={}).", rootMessage.getType());
                // TODO: Disconnect agent from server.
                break;
        }
    }

    private void handleAck(RootMessage rootMessage) throws InvalidProtocolBufferException {
        AckMessage message = AckMessage.parseFrom(rootMessage.getMessage());

        if (LOG.isDebugEnabled()) {
            LOG.debug("AckMessage(id={})", message.getId());
        }
    }

    private void handleHello(RootMessage rootMessage) throws InvalidProtocolBufferException {
        HelloMessage message = HelloMessage.parseFrom(rootMessage.getMessage());

        if (LOG.isDebugEnabled()) {
            LOG.debug("HelloMessage(needs_auth={},protocol_version={})",
                    message.getNeedsAuth(),
                    message.getProtocolVersion());
        }
    }
}
