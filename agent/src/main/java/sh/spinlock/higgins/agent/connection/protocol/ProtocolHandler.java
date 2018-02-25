package sh.spinlock.higgins.agent.connection.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.HostConnection;
import sh.spinlock.higgins.agent.connection.info.AgentInfo;
import sh.spinlock.higgins.agent.connection.info.InfoCollector;
import sh.spinlock.higgins.connection.protocol.MessageBuilder;
import sh.spinlock.higgins.connection.protocol.ProtocolMessages;
import sh.spinlock.higgins.connection.protocol.ProtocolRootMessage;
import sh.spinlock.higgins.connection.protocol.ProtocolRootMessage.RootMessage;
import sh.spinlock.higgins.connection.protocol.ProtocolMessages.*;
import sh.spinlock.higgins.util.MessageId;

import java.util.HashMap;
import java.util.Map;

import static sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants.MessageIndex.*;

public class ProtocolHandler {
    private static final Logger LOG = LogManager.getLogger(ProtocolHandler.class);

    @Getter
    @Setter
    private HostConnection connection;

    @Getter
    private MessageId id;

    private final Map<Long, Integer> awaitingAck;

    public ProtocolHandler() {
        id = new MessageId();
        awaitingAck = new HashMap<>();
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

        switch (awaitingAck.get(message.getId())) {
            case ProtocolConstants.MessageIndex.AGENT_INFO:
                sendAgentInfo();
                break;
            default:
                LOG.error("Unexpected ack on message {}", message.getId());
                break;
        }
    }

    private void handleHello(RootMessage rootMessage) throws InvalidProtocolBufferException {
        HelloMessage message = HelloMessage.parseFrom(rootMessage.getMessage());

        if (LOG.isDebugEnabled()) {
            LOG.debug("HelloMessage(needs_auth={},protocol_version={})",
                    message.getNeedsAuth(),
                    message.getProtocolVersion());
        }

        AuthMessage.Builder authMessage = AuthMessage.newBuilder();
        authMessage.setPassword("somePassword"); // TODO
        authMessage.setUuidLeast(0); // TODO
        authMessage.setUuidMost(0); // TODO

        long msgId = id.increment();
        RootMessage replyMessage = MessageBuilder.buildRootMessage(msgId,
                ProtocolConstants.MessageIndex.AUTH,
                authMessage.build().toByteArray());
        getConnection().send(replyMessage.toByteArray());
        awaitingAck.put(msgId, ProtocolConstants.MessageIndex.AGENT_INFO);
    }

    private void sendAgentInfo() {
        AgentInfo agentInfo = InfoCollector.collectAgentInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(agentInfo);
        } catch (JsonProcessingException e) {
            LOG.error("Could not serialize AgentInfo", e);
        }

        if (json != null) {
            AgentInfoMessage.Builder infoMessage = AgentInfoMessage.newBuilder();
            infoMessage.setJson(json);

            RootMessage replyMessage = MessageBuilder.buildRootMessage(id.increment(),
                    ProtocolConstants.MessageIndex.AGENT_INFO,
                    infoMessage.build().toByteArray());
            getConnection().send(replyMessage.toByteArray());
        }
    }
}
