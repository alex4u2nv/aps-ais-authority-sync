FROM osixia/openldap

RUN mkdir -p /usr/local/bin
COPY  initialize.sh /usr/local/bin/initialize.sh
RUN chmod 755 /usr/local/bin/initialize.sh

ENTRYPOINT  /usr/local/bin/initialize.sh
