# Protocol Documentation

## Brief Message Table
|Index|Direction|Title|Description|
|---|---|---|---|
|0|server -> client|ack|Acknowledges specified request id|
|1|server -> client|hello|Notifies client upon connect whether or not they need to authenticate.|
|2|client -> server|auth|Client sends reply to hello message containing password to authenticate.|

## Root Message
This is the root of every message sent using the protocol.
It defines what message type is being used, as well as giving
bytes to deserialize a message that is defined elsewhere.

```proto
message RootMessage {
    uint32 rid = 1;
    uint32 type = 2;
    bytes message = 3;
}
```

## Messages
### ack
```proto
message AckMessage {
    uint32 rid = 1;
}
```

### hello
```proto
message HelloMessage {
    bool needsAuth;
}
```

### auth
```proto
message AuthMessage {
    string password;
}
```
