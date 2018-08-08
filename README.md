# Purpose
APS LimitedUser Synchronization from Keycloak.

Since Keycloak's API does not support querying its user database by modified timestamp, we're only 
able to load all users and iterate through them, as in a full sync scenario.

Additionally, Keycloak's database does not support duplicated user accounts when federatating users
from multiple ldaps. Duplicate accounts can lead to errors.


# Version
This version of aps-archetype-base-java has been developed and verified against Alfresco Process Services 1.9.0



# Usage
install jar with dependencies

# Testing
Integration Tests are available and can be executed against the docker container for keycloak.
A docker compose is provided with 3 ldaps and 1 keycloak that can be federated into keycloak for 
testing.
## Startup the containers
* Adjust the Ipaddress or Host to your docker host; as this is how activiti, your laptop, and keycloak will
communicate with each other.
This property is in two places: 
1.  `./src/test/resources/activiti-identity-service.properties:keycloak.auth-server-url`

2.  `docker-compose.yml:IDENTITY_SERVICE_AUTH`

3. Copy your enterprise license into `src/test/license/activiti.lic`
 
* Then startup the containers by running the following:
`./run.sh`

## Update Keycloak's admin account
Edit the keycloak' admin account, and ensure that the e-mail address is set to: admin@app.activiti.com

## Relevant Access
* Keycloak access
  - username: admin
  - password: admin

* openldap1 access
  - host: openldap1
  - username: cn=admin,dc=abc,dc=com
  - password: admin

* openldap2 access
  - host: openldap2
  - username: cn=admin,dc=efg,dc=com
  - password: admin

* openldap3 access
  - host: openldap3
  - username: cn=admin,dc=hij,dc=com
  - password: admin


## Update Keycloak's Admin User


## Add Federated users 
Using the information in the `Relevant Access` section, configure the federated users in your keycloak
repository.

Verify that users are synchronizing into Activiti.


## Note
* Keycloak does an ldap federated search whenever you browse users, or request users 
* Keycloak 4.x has an enable/disable federated user flag. After federated sync, you can disable this flag to avoid an ldap search whenever browsing users

# Directory Structure
```
    \aps-archetype-java-xml
    	\src
			\main
				\java
			\test
				\java
				\license
					\activiti.lic
				\process
					\HelloWorld.bpmn
				\resources
					\log4j.properties
```

# Usage
* Configure Maven to access the Alfresco Artifacts Repository at <https://artefacts.alfresco.com>
* Provide a valid Alfresco Process Services license in src/test/license e.g. src/test/license/activiti.lic
* Add APS extension beans to com.alfresco.consulting.conf.TestApplicationConfig as required
* Build the Java project using Maven and deploy to the Alfresco Process Services classpath in the usual way e.g. \activiti-app\WEB-INF\lib\aps-archetype-base-java-1.0.0.



#Duplicated LDAP entries will fail a batch lookup
When paging through users, if a ldap exception is thrown in keycloak, which fails the entire batch lookup, we shrink our 
batch to 1 item, and iterate for this batch to avoid the error

This error is also thrown when you try to lookup a duplicated user in keycloak

```
keycloak2_1  | 16:08:46,016 ERROR [org.keycloak.services.error.KeycloakErrorHandler] (default task-43) Uncaught server error: org.keycloak.models.ModelDuplicateException: Error - multiple LDAP objects found but expected just one
keycloak2_1  | 	at org.keycloak.storage.ldap.idm.query.internal.LDAPQuery.getFirstResult(LDAPQuery.java:182)
keycloak2_1  | 	at org.keycloak.storage.ldap.LDAPStorageProvider.loadLDAPUserByUsername(LDAPStorageProvider.java:760)
keycloak2_1  | 	at org.keycloak.storage.ldap.LDAPStorageProvider.loadAndValidateUser(LDAPStorageProvider.java:461)
keycloak2_1  | 	at org.keycloak.storage.ldap.LDAPStorageProvider.validate(LDAPStorageProvider.java:149)
keycloak2_1  | 	at org.keycloak.storage.UserStorageManager.importValidation(UserStorageManager.java:314)
keycloak2_1  | 	at org.keycloak.storage.UserStorageManager.importValidation(UserStorageManager.java:358)
keycloak2_1  | 	at org.keycloak.storage.UserStorageManager.getUsers(UserStorageManager.java:525)
keycloak2_1  | 	at org.keycloak.models.cache.infinispan.UserCacheSession.getUsers(UserCacheSession.java:553)
keycloak2_1  | 	at org.keycloak.services.resources.admin.UsersResource.getUsers(UsersResource.java:219)
keycloak2_1  | 	at sun.reflect.GeneratedMethodAccessor491.invoke(Unknown Source)
keycloak2_1  | 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
keycloak2_1  | 	at java.lang.reflect.Method.invoke(Method.java:498)
keycloak2_1  | 	at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:140)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTarget(ResourceMethodInvoker.java:295)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:249)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invokeOnTargetObject(ResourceLocatorInvoker.java:138)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invoke(ResourceLocatorInvoker.java:107)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invokeOnTargetObject(ResourceLocatorInvoker.java:133)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invoke(ResourceLocatorInvoker.java:107)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invokeOnTargetObject(ResourceLocatorInvoker.java:133)
keycloak2_1  | 	at org.jboss.resteasy.core.ResourceLocatorInvoker.invoke(ResourceLocatorInvoker.java:101)
keycloak2_1  | 	at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:406)
keycloak2_1  | 	at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:213)
keycloak2_1  | 	at org.jboss.resteasy.plugins.server.servlet.ServletContainerDispatcher.service(ServletContainerDispatcher.java:228)
keycloak2_1  | 	at org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:56)
keycloak2_1  | 	at org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:51)
keycloak2_1  | 	at javax.servlet.http.HttpServlet.service(HttpServlet.java:790)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletHandler.handleRequest(ServletHandler.java:85)
keycloak2_1  | 	at io.undertow.servlet.handlers.FilterHandler$FilterChainImpl.doFilter(FilterHandler.java:129)
keycloak2_1  | 	at org.keycloak.services.filters.KeycloakSessionServletFilter.doFilter(KeycloakSessionServletFilter.java:90)
keycloak2_1  | 	at io.undertow.servlet.core.ManagedFilter.doFilter(ManagedFilter.java:61)
keycloak2_1  | 	at io.undertow.servlet.handlers.FilterHandler$FilterChainImpl.doFilter(FilterHandler.java:131)
keycloak2_1  | 	at io.undertow.servlet.handlers.FilterHandler.handleRequest(FilterHandler.java:84)
keycloak2_1  | 	at io.undertow.servlet.handlers.security.ServletSecurityRoleHandler.handleRequest(ServletSecurityRoleHandler.java:62)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletDispatchingHandler.handleRequest(ServletDispatchingHandler.java:36)
keycloak2_1  | 	at org.wildfly.extension.undertow.security.SecurityContextAssociationHandler.handleRequest(SecurityContextAssociationHandler.java:78)
keycloak2_1  | 	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)
keycloak2_1  | 	at io.undertow.servlet.handlers.security.SSLInformationAssociationHandler.handleRequest(SSLInformationAssociationHandler.java:131)
keycloak2_1  | 	at io.undertow.servlet.handlers.security.ServletAuthenticationCallHandler.handleRequest(ServletAuthenticationCallHandler.java:57)
keycloak2_1  | 	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)
keycloak2_1  | 	at io.undertow.security.handlers.AbstractConfidentialityHandler.handleRequest(AbstractConfidentialityHandler.java:46)
keycloak2_1  | 	at io.undertow.servlet.handlers.security.ServletConfidentialityConstraintHandler.handleRequest(ServletConfidentialityConstraintHandler.java:64)
keycloak2_1  | 	at io.undertow.security.handlers.AuthenticationMechanismsHandler.handleRequest(AuthenticationMechanismsHandler.java:60)
keycloak2_1  | 	at io.undertow.servlet.handlers.security.CachedAuthenticatedSessionHandler.handleRequest(CachedAuthenticatedSessionHandler.java:77)
keycloak2_1  | 	at io.undertow.security.handlers.NotificationReceiverHandler.handleRequest(NotificationReceiverHandler.java:50)
keycloak2_1  | 	at io.undertow.security.handlers.AbstractSecurityContextAssociationHandler.handleRequest(AbstractSecurityContextAssociationHandler.java:43)
keycloak2_1  | 	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)
keycloak2_1  | 	at org.wildfly.extension.undertow.security.jacc.JACCContextIdHandler.handleRequest(JACCContextIdHandler.java:61)
keycloak2_1  | 	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)
keycloak2_1  | 	at org.wildfly.extension.undertow.deployment.GlobalRequestControllerHandler.handleRequest(GlobalRequestControllerHandler.java:68)
keycloak2_1  | 	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler.handleFirstRequest(ServletInitialHandler.java:292)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler.access$100(ServletInitialHandler.java:81)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler$2.call(ServletInitialHandler.java:138)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler$2.call(ServletInitialHandler.java:135)
keycloak2_1  | 	at io.undertow.servlet.core.ServletRequestContextThreadSetupAction$1.call(ServletRequestContextThreadSetupAction.java:48)
keycloak2_1  | 	at io.undertow.servlet.core.ContextClassLoaderSetupAction$1.call(ContextClassLoaderSetupAction.java:43)
keycloak2_1  | 	at org.wildfly.extension.undertow.security.SecurityContextThreadSetupAction.lambda$create$0(SecurityContextThreadSetupAction.java:105)
keycloak2_1  | 	at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1508)
keycloak2_1  | 	at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1508)
keycloak2_1  | 	at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1508)
keycloak2_1  | 	at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1508)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler.dispatchRequest(ServletInitialHandler.java:272)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler.access$000(ServletInitialHandler.java:81)
keycloak2_1  | 	at io.undertow.servlet.handlers.ServletInitialHandler$1.handleRequest(ServletInitialHandler.java:104)
keycloak2_1  | 	at io.undertow.server.Connectors.executeRootHandler(Connectors.java:326)
keycloak2_1  | 	at io.undertow.server.HttpServerExchange$1.run(HttpServerExchange.java:812)
keycloak2_1  | 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
keycloak2_1  | 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
keycloak2_1  | 	at java.lang.Thread.run(Thread.java:748)
keycloak2_1  |

```