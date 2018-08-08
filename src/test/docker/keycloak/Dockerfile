FROM jboss/keycloak
MAINTAINER https://gitter.im/Alfresco/platform-services


ADD springboot-realm.json /opt/jboss/keycloak/

ENTRYPOINT [ "/opt/jboss/docker-entrypoint.sh" ]

ENV PORT_OFFSET 300

EXPOSE 8380 30081

CMD ["-b", "0.0.0.0", "-Dkeycloak.import=/opt/jboss/keycloak/springboot-realm.json -Djboss.socket.binding.port-offset=${env.PORT_OFFSET}"]
