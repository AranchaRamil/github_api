package com.arancharamil.githubapi.events;

import com.arancharamil.githubapi.rest.UserDetail;

import java.util.List;

/**
 * Created by desi on 02/10/2017.
 */

public class UserDetailEvent {

    private Boolean error;
    private UserDetail usersDetail;

    public Boolean isError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public UserDetail getUsersDetail() {
        return usersDetail;
    }

    public void setUsersDetail(UserDetail usersDetail) {
        this.usersDetail = usersDetail;
    }

    public UserDetailEvent(Boolean error, UserDetail userslist) {
        super();
        this.error = error;
        this.usersDetail = userslist;
    }



}