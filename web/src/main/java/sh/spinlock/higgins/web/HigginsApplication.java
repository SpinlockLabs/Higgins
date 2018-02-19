package sh.spinlock.higgins.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolMessages;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolRootMessage;
import sh.spinlock.higgins.agent.connection.protocol.ProtocolVersion;
import sh.spinlock.higgins.web.agent.Agent;
import sh.spinlock.higgins.web.agent.connection.JvmAgentConnection;

@SpringBootApplication
public class HigginsApplication {
    public static void main(String[] args) {
        ProtocolMessages.HelloMessage.Builder helloMessage = ProtocolMessages.HelloMessage.newBuilder();
        helloMessage.setNeedsAuth(true);
        helloMessage.setProtocolVersion(ProtocolVersion.VERSION);

        ProtocolRootMessage.RootMessage.Builder rootMessage = ProtocolRootMessage.RootMessage.newBuilder();
        rootMessage.setId(1);
        rootMessage.setType(1);
        rootMessage.setMessage(helloMessage.build().toByteString());

        byte[] messageBytes = rootMessage.build().toByteArray();

        Agent agent = new Agent("local", new JvmAgentConnection());
        agent.getConnection().send(messageBytes);

        /*Job job = null;
        try {
            job = Job.createJob("Minetweak");
        } catch (IOException e) {
            System.err.println("Failed to create job!");
            e.printStackTrace();
        }

        if (job != null) {
            job.addTask(new UnixShellTask(job));

            job.run();
        }*/

        //SpringApplication.run(HigginsApplication.class, args);
    }
}
