/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jrpc.example.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.jrpc.JrpcMethod;
import me.jrpc.RequestJsonObject;
import me.jrpc.ResponseJsonObject;
import me.jrpc.example.data.User;
import me.jrpc.example.data.UserFilter;
import me.jrpc.example.users.UserManager;

/**
 *
 * @author Owner
 */
public class GetUsersByFilters implements JrpcMethod{

    @Override
    public ResponseJsonObject execute(HttpServletRequest request, 
             HttpServletResponse response, RequestJsonObject requestJsonObject) {
        UserFilter[] filters = (UserFilter[])requestJsonObject.data;
        User [] users = UserManager.getByFilters(filters);
        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        responseJsonObject.data = users;
        return responseJsonObject;
    }    
}
