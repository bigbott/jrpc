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
public class AddNumbers implements JrpcMethod{

    @Override
    public ResponseJsonObject execute(HttpServletRequest request, 
             HttpServletResponse response, RequestJsonObject requestJsonObject) {
        Integer[] numbers = (Integer[])requestJsonObject.data;
        int result = numbers[0] + numbers[1];
        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        responseJsonObject.data = new Integer[]{result};
        return responseJsonObject;
    }    
}
