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
1) `./src/test/resources/activiti-identity-service.properties:keycloak.auth-server-url`
2) `docker-compose.yml:IDENTITY_SERVICE_AUTH` 
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
