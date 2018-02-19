package sh.spinlock.higgins.agent.connection.protocol;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtocolHandler {
    public ProtocolHandler() {}

    public void handleIncoming(byte[] bytes) throws InvalidProtocolBufferException {
        ProtocolRootMessage.RootMessage rootMessage = ProtocolRootMessage.RootMessage.parseFrom(bytes);
    }
}
