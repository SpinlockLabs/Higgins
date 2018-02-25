package sh.spinlock.higgins.connection.protocol;

import com.google.protobuf.ByteString;
import sh.spinlock.higgins.connection.protocol.ProtocolRootMessage.RootMessage;

public final class MessageBuilder {
    public static RootMessage buildRootMessage(long id, int type, byte[] content) {
        return RootMessage.newBuilder()
                .setId(id)
                .setType(type)
                .setMessage(ByteString.copyFrom(content))
                .build();
    }
}
