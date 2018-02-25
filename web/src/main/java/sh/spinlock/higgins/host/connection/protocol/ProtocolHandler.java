package sh.spinlock.higgins.host.connection.protocol;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants;
import sh.spinlock.higgins.connection.protocol.MessageBuilder;
import sh.spinlock.higgins.host.connection.AgentConnection;
import sh.spinlock.higgins.util.MessageId;
import sh.spinlock.higgins.connection.protocol.ProtocolRootMessage.RootMessage;
import sh.spinlock.higgins.connection.protocol.ProtocolMessages.*;

import static sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants.MessageIndex.*;

public class ProtocolHandler {
    private static final Logger LOG = LogManager.getLogger(ProtocolHandler.class);

    @Getter
    @Setter
    private AgentConnection connection;

    @Getter
    private MessageId id;

    public ProtocolHandler() {
        id = new MessageId();
    }

    public final void handleIncoming(byte[] bytes) {
        try {
            RootMessage rootMessage = RootMessage.parseFrom(bytes);

            if (LOG.isDebugEnabled()) {
                LOG.debug("RootMessage(id={},type={})", rootMessage.getId(), rootMessage.getType());
            }

            handleRootMessage(rootMessage);
        } catch (InvalidProtocolBufferException e) {
            LOG.error("Could not parse root message from Protobuf", e);
            // TODO
        }
    }

    private void handleRootMessage(RootMessage rootMessage)  throws InvalidProtocolBufferException {
        switch (rootMessage.getType()) {
            case AUTH:
                handleAuth(rootMessage);
                break;
            case AGENT_INFO:
                handleAgentInfo(rootMessage);
                break;
            default:
                LOG.error("Host received unknown message type (id={}).", rootMessage.getType());
                // TODO
                break;
        }
    }

    private void handleAuth(RootMessage rootMessage) throws InvalidProtocolBufferException {
        AuthMessage authMessage = AuthMessage.parseFrom(rootMessage.getMessage());

        LOG.debug("AuthMessage(password={},uuid_least={},uuid_most={})", authMessage.getPassword(),
                authMessage.getUuidLeast(), authMessage.getUuidMost());

        AckMessage ackMessage = AckMessage.newBuilder()
                .setId(rootMessage.getId())
                .build();

        RootMessage ackMessageRoot = MessageBuilder.buildRootMessage(id.increment(),
                ProtocolConstants.MessageIndex.ACK,
                ackMessage.toByteArray());
        getConnection().send(ackMessageRoot.toByteArray());
    }

    private void handleAgentInfo(RootMessage rootMessage) throws InvalidProtocolBufferException {
        AgentInfoMessage infoMessage = AgentInfoMessage.parseFrom(rootMessage.getMessage());

        LOG.debug("AgentInfoMessage(json={})", infoMessage.getJson());
    }
}

