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

/**
 *
 * @author Owner
 */
public class EchoWords implements JrpcMethod{

    @Override
    public ResponseJsonObject execute(HttpServletRequest request, 
             HttpServletResponse response, RequestJsonObject requestJsonObject) {
        String[] words = (String[])requestJsonObject.data;
        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        responseJsonObject.data = words;
        return responseJsonObject;
    }    
}
