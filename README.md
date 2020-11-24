# wso2-organization-mgt-user-operations-event-listener
user operation event listener to delete all organization related admin roles assigned to a user, after user deletion.

Add the following config to deployment.toml
```
[[event_listener]]
id = "user_delete_operation_listener"
type = "org.wso2.carbon.user.core.listener.UserOperationEventListener"
name= "org.wso2.carbon.organization.mgt.user.deletion.event.listener.impl.OrganizationMgtUserDeletionEventListener"
order = 91
enable = true
```
