package me.jrpc;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JrpcMethod {
    
    ResponseJsonObject execute (HttpServletRequest request, HttpServletResponse response, 
                      RequestJsonObject requestJsonObject); 

}
