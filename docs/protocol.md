# Protocol Documentation

## Brief Message Table
|Index|Direction|Title|Description|
|---|---|---|---|
|0|server <-> client|ack|Acknowledges specified message id.|
|1|server -> client|hello|Notifies client upon connect whether or not they need to authenticate.|
|2|client -> server|auth|Client sends reply to hello message containing password to authenticate.|
|3|client -> server|agentinfo|Agent sends to let the server know information about it.|

## Root Message
This is the root of every message sent using the protocol.
It defines what message type is being used, as well as giving
bytes to deserialize the message that is defined by the type field.

```proto
message RootMessage {
    uint64 id = 1;
    uint32 type = 2;
    bytes message = 3;
}
```

## Messages
### ack
Used as a common message to acknowledge another message. For example, upon
sending the auth message, the agent should store its auth message ID,
and look for any ack messages incoming that contain that id, as the
server has accepted the auth message and the agent may continue on.

```proto
message AckMessage {
    uint64 id = 1;
}
```

### hello
Server sends hello message upon connection to the agent endpoint.
This message specifies whether or not the agent needs to authenticate
with a password, as well as letting the agent know what protocol version
the server is using, and if they are different, the agent should disconnect.

```proto
message HelloMessage {
    bool needs_auth;
    uint32 protocol_version;
}
```

### auth
The agent sends this in response to the initial hello message.
It contains the password, and is reserved for other vital
connection information in future protocol versions.

```proto
message AuthMessage {
    string password;
}
```

### agentinfo
If the auth message was successful (received ack message for it), this
message is sent to inform the server any information about this agent
that it may need to know. It should be allowed to be sent again in the
future, in case any mutable information is changed during runtime.

**Warning**: This message uses JSON to store information, as there will
be differences between data on different systems.

```proto
message AgentInfoMessage {
    string json; // See warning above
}
```

#### Example JSON structure
```json
{
  "system.properties": {
    "os.name": "Linux",
    "os.version": "4.14.16-300.fc27.x86_64",
    "os.arch": "amd64",
    "java.vm.name": "OpenJDK 64-Bit Server VM",
    "java.version": "1.8.0_161",
    "user.name": "logan",
    "user.home": "/home/logan",
    "user.dir": "/home/logan/Dev/Java",
    "user.language": "en"
  }
}
```
