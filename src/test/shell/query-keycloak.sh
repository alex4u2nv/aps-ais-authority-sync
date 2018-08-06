#!/usr/bin/env bash

####################################################################################################
# Some scripts to query keycloak's API This is used for inspecting/testing keycloaks api
#
#
#
#
#
#
####################################################################################################
KEYCLOAK_URL=http://localhost:8180/auth
KEYCLOAK_REALM=springboot
KEYCLOAK_CLIENT_ID=admin
KEYCLOAK_CLIENT_SECRET=admin
USER_ID=admin


export TKN=$(curl -s -X POST "${KEYCLOAK_URL}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=${KEYCLOAK_CLIENT_ID}" \
 -d "password=${KEYCLOAK_CLIENT_SECRET}" \
 -d 'grant_type=password' \
 -d 'client_id=admin-cli' | jq -r '.access_token')

user_page=$(curl -s -X GET "${KEYCLOAK_URL}/admin/realms/${KEYCLOAK_REALM}/users?first=0&max=100" \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .)



count=`echo $user_page | jq '. | length '`
echo "User Page Count:: ${count} "




user_count=$(curl -s -X GET "${KEYCLOAK_URL}/admin/realms/${KEYCLOAK_REALM}/users/count" \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .)

echo "Total User Count: ${user_count}"


groups=$(curl -s -X GET "${KEYCLOAK_URL}/admin/realms/${KEYCLOAK_REALM}/groups" \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .)



groupsMembers=$(curl -s -X GET "${KEYCLOAK_URL}/admin/realms/${KEYCLOAK_REALM}/groups/e0c3dbe3-8582-4a35-b3b7-da70ace0d67f/members" \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .)

echo $groupsMembers