# wso2-user-operations-event-listener
user operation event listener to delete all organization related admin roles assigned to a user, after user deletion.

Add the following config to deployment.toml
```
[[event_listener]]
id = "user_delete_operation_listener"
type = "org.wso2.carbon.user.core.listener.UserOperationEventListener"
name= "org.wso2.carbon.custom.user.deletion.event.listener.impl.CustomUserDeletionEventListener"
order = 91
enable = true
```
