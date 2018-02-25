package sh.spinlock.higgins.host.agent;

import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants;
import sh.spinlock.higgins.connection.protocol.ProtocolMessages.HelloMessage;
import sh.spinlock.higgins.connection.protocol.ProtocolRootMessage.RootMessage;
import sh.spinlock.higgins.host.connection.AgentConnection;

public class Agent {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private AgentConnection connection;

    private boolean ready;

    public void disconnect() {
        getConnection().close();
    }

    public void setReady() {
        if (ready) return;
        ready = true;

        HelloMessage.Builder helloMessage = HelloMessage.newBuilder();
        helloMessage.setNeedsAuth(true);
        helloMessage.setProtocolVersion(ProtocolConstants.VERSION);

        RootMessage.Builder rootMessage = RootMessage.newBuilder();
        rootMessage.setId(1);
        rootMessage.setType(1);
        rootMessage.setMessage(helloMessage.build().toByteString());

        getConnection().send(rootMessage.build().toByteArray());
    }
}
