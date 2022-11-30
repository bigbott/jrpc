package me.jrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class JrpcServlet extends HttpServlet {

    private static final long serialVersionUID = -7510097244119507496L;

    private static Gson gson = new Gson();

    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config);
            ConfigJsonObject configJsonObject = ConfigJson.getInstance(gson);

            if (configJsonObject.onInitClass == null || configJsonObject.onInitMethod == null) {
                return;
            }

            Class clazz = Class.forName(configJsonObject.onInitClass);
            Method method = clazz.getMethod(configJsonObject.onInitMethod, null);
            method.invoke(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(JrpcServlet.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ResponseJsonObject responseJsonObject =  new ResponseJsonObject();
//
//    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestJson = readJsonFromRequest(request);

        ConfigJsonObject configJsonObject = ConfigJson.getInstance(gson);

        RequestJsonObject requestJsonObject = gson.fromJson(requestJson, RequestJsonObject.class);
        if (requestJsonObject.dataType == null) {
            sendResponseWithError(response, "dataType should be provided");
        }

        String error = fixRequestJsonObjectData(requestJsonObject, requestJson, configJsonObject);
        if (error != null) {
            sendResponseWithError(response, error);
        }

        ServletContext context = getServletContext();

        JrpcMethod jrpcMethod = getJrpcMethod(request, context, configJsonObject);

        ResponseJsonObject responseJsonObject = jrpcMethod.execute(request, response, requestJsonObject);

        if (responseJsonObject == null) {
            responseJsonObject = new ResponseJsonObject();
        }

        sendResponse(response, responseJsonObject);

    }

    private String getDataAsString(String requestJson) {
        int indexOfData = requestJson.indexOf("data");
        int indexOfOpenSquareBracket = requestJson.indexOf("[", indexOfData);
        int indexOfCloseSquareBracket = requestJson.lastIndexOf("]");
        String data = requestJson.substring(indexOfOpenSquareBracket, indexOfCloseSquareBracket + 1);
        return data;
    }

    private String getMethodClassName(String pathInfo) {
        //example pathInfo: /add-two-numbers
        String methodClassName = "";
        String path = pathInfo.substring(1);
        if (path.contains("-")) {
            String[] pathData = path.split("-");

            for (String word : pathData) {
                methodClassName += Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            return methodClassName;
        }

        methodClassName = Character.toUpperCase(path.charAt(0)) + path.substring(1);
        return methodClassName;
    }

    private boolean isPrimitive(String dataType) {
        String[] primitives = {"Integer", "Long", "Boolean", "Double", "String", "Short", "Float"};
        for (String s : primitives) {
            if (s.equalsIgnoreCase(dataType)) {
                return true;
            }
        }
        return false;
    }

    private String readJsonFromRequest(HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonBuilder.toString();
    }

    private String fixRequestJsonObjectData(RequestJsonObject requestJsonObject, String requestJson, ConfigJsonObject configJsonObject) {
        String dataPackage = configJsonObject.dataPackage;
        if (isPrimitive(requestJsonObject.dataType)) {
            dataPackage = "java.lang";
        }
        String dataObjectFullName = dataPackage + "." + requestJsonObject.dataType;
        String dataJson = getDataAsString(requestJson);
        try {
            Class dataArrayClass = Class.forName("[L" + dataObjectFullName + ";");
            requestJsonObject.data = (Object[]) gson.fromJson(dataJson, dataArrayClass);
        } catch (Exception e) {
            e.printStackTrace();
            return "cannot create dataClass from dataType sent";
        }
        return null;
    }

    private JrpcMethod getJrpcMethod(HttpServletRequest request, ServletContext context, ConfigJsonObject configJsonObject) {
        String methodClassName = getMethodClassName(request.getPathInfo());
        String methodClassFullName = configJsonObject.methodPackage + "." + methodClassName;
        JrpcMethod methodObject = (JrpcMethod) context.getAttribute(methodClassFullName);
        if (methodObject == null) {
            try {
                Class methodClass = Class.forName(methodClassFullName);
                methodObject = (JrpcMethod) methodClass.newInstance();
                context.setAttribute(methodClassFullName, methodObject);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return methodObject;
    }

    private void sendResponse(HttpServletResponse response, ResponseJsonObject responseJsonObject) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(gson.toJson(responseJsonObject));
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(JrpcServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendResponseWithError(HttpServletResponse response, String error) {
        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        responseJsonObject.error = error;
        sendResponse(response, responseJsonObject);
    }

}
