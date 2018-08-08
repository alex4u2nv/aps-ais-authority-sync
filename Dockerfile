FROM  alfresco/process-services

COPY  src/test/license/activiti.lic /usr/local/tomcat/lib/
COPY  target/test-classes/activiti-identity-service.properties /usr/local/tomcat/lib/
COPY  src/test/resources/activiti-app.properties /usr/local/tomcat/lib/
RUN   mkdir -p /usr/local/tomcat/webapps/activiti-app; cd $_ ; unzip ../activiti-app.war; mv ../activiti-app.war ../activiti-app.war.orig
COPY  target/*with-dependencies.jar  /usr/local/tomcat/webapps/activiti-app/WEB-INF/lib/
RUN   cd /usr/local/tomcat/webapps/activiti-app; jar  -cMf ../activiti-app.war .

