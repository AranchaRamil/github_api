package com.arancharamil.githubapi.events;

import java.util.List;

/**
 * Created by desi on 02/10/2017.
 */

public class UsersListEvent {

    private Boolean error;
    private List userslist;

    public UsersListEvent(Boolean error, List userslist) {
        super();
        this.error = error;
        this.userslist = userslist;
    }

    public Boolean isError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List getUserslist() {
        return userslist;
    }

}