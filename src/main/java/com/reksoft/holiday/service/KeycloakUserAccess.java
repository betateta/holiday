package com.reksoft.holiday.service;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeycloakUserAccess {
    @Value("${keycloak.auth-server-url}")
    private String serverURL;
    @Value(("${keycloak.realm}"))
    private String workRealm;
    private final String REALM= "master";
    private final String USERNAME="admin";
    private final String PASSWORD="admin_password";
    private final String CLIENT_ID="admin-cli";

    public String getUsername(String userID) {
        System.out.println("server url:"+serverURL);
        System.out.println("realm:"+REALM);
        System.out.println("client _id:"+CLIENT_ID);

        Keycloak keycloak = KeycloakBuilder
                .builder()
                .serverUrl(serverURL)
                .realm(REALM)
                .username(USERNAME)
                .password(PASSWORD)
                .clientId(CLIENT_ID)
                .grantType(OAuth2Constants.PASSWORD)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        //keycloak.tokenManager().getAccessToken().;

        RealmResource realmResource = keycloak.realm(workRealm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userID);;

        String userName = userResource.toRepresentation().getUsername();

        keycloak.realm(REALM).logoutAll();
        if (!keycloak.isClosed()){
            keycloak.close();
        }

        System.out.println("username:"+userName);

        return userName;
    }
    public Set<String> getUserRoles(String userID) {
        System.out.println("server url:"+serverURL);
        System.out.println("realm:"+REALM);
        System.out.println("client_id:"+CLIENT_ID);

        Keycloak keycloak = KeycloakBuilder
                .builder()
                .serverUrl(serverURL)
                .realm(REALM)
                .username(USERNAME)
                .password(PASSWORD)
                .clientId(CLIENT_ID)
                .grantType(OAuth2Constants.PASSWORD)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        RealmResource realmResource = keycloak.realm(workRealm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userID);

        System.out.println("find user:"+userResource.toRepresentation().getUsername());
        System.out.println("roles:"+userResource.roles().realmLevel().listEffective());
        System.out.println("data:"+userResource.toRepresentation().toAttributes());

        Set<String> roles = userResource.roles().realmLevel().listEffective()
                .stream()
                .map(roleRepresentation -> roleRepresentation.toString())
                .collect(Collectors.toSet());

        keycloak.realm(REALM).logoutAll();
        if (!keycloak.isClosed()){
            keycloak.close();
        }

        return roles;
    }
}
