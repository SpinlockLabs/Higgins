package sh.spinlock.higgins.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolConstants;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolMessages;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage;
import sh.spinlock.higgins.web.agent.Agent;
import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

@SpringBootApplication
public class HigginsApplication {
    public static void main(String[] args) {
        ProtocolMessages.HelloMessage.Builder helloMessage = ProtocolMessages.HelloMessage.newBuilder();
        helloMessage.setNeedsAuth(true);
        helloMessage.setProtocolVersion(ProtocolConstants.VERSION);

        ProtocolRootMessage.RootMessage.Builder rootMessage = ProtocolRootMessage.RootMessage.newBuilder();
        rootMessage.setId(1);
        rootMessage.setType(1);
        rootMessage.setMessage(helloMessage.build().toByteString());

        byte[] messageBytes = rootMessage.build().toByteArray();

        Agent agent = new Agent("local", new JvmAgentConnection());
        agent.getConnection().send(messageBytes);

        //SpringApplication.run(HigginsApplication.class, args);
    }
}
