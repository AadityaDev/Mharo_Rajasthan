package com.technawabs.mharorajasthan.pojo;

public class LoginResponse {

    private String auth_token;
    private String username;
    private int hasura_id;
    private String[] hasura_roles;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHasura_id() {
        return hasura_id;
    }

    public void setHasura_id(int hasura_id) {
        this.hasura_id = hasura_id;
    }

    public String[] getHasura_roles() {
        return hasura_roles;
    }

    public void setHasura_roles(String[] hasura_roles) {
        this.hasura_roles = hasura_roles;
    }
}
