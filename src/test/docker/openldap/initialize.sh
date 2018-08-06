#!/usr/bin/env bash
set -x
echo "making directory"
mkdir -p /container/service/slapd/assets/config/bootstrap/ldif/custom/

echo "copying ldiff"
cp /tmp/ldap/ldif/* /container/service/slapd/assets/config/bootstrap/ldif/custom/

/container/tool/run
