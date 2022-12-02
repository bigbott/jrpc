
## JRPC is a Java JSON-RPC over HTTP web(*) mini framework. 

*It can be used for creating back-ends for SPA web apps, mobile apps, desktop apps - any app that sends/receives JSON over HTTP.


### Why was it written?
I needed a simple Java backend for my SPA/Cordova app. I like Java, but Java frameworks are too "enterprise" and not simple.

### Standard
It is not a JSON-RPC over HTTP specification reference implementation: it follows specification but not exactly. 
The main difference is request JSON: 

According to the specification, request JSON should look like this: 

`POST example.com/`
`{"method": "sum", "params": {"a":3, "b":4}, "id":0}`

JRPC expects this:

`POST example.com/Sum `
`{"data": [3, 4], "dataType": "Integer"}`

The differences are:
1. Method went from payload to URL. More suitable for HTTP
2. Word `data` is used instead of `params`. Data should be always an array. It can be an array of primitives or objects. 
3. Additional parameter `dataType` specifies Java type which will be used to deserialize data JSON array into an array of Java objects/primitives.
   The next values are legit: Integer, Long, Double, Boolean, String (and other Java primitives wrappers classes), 
   and custom objects (POJOs) like User, Product, Order, etc.
4. `Id` parameter is omitted, as it does not make sense for HTTP



### Conventions
- Only POST is used. 
- Data request parameter is always an array.
- Data returned by the server is always an array.
- For each method specified in the request e.g. /Sum Java class Sum should be written that implements `JrpcMethod ``interface `with `execute `method. 
   

### Configuration

File `jrpc-config.json` should be placed in the `/resources/` folder (which should be in the classpath).

jrpc-config.json:

`{`

   `"onInitClass": "com.example.InitManager",`

   `"onInitMethod": "load",`

   `"methodPackage": "com.example.method"`

   `"dataPackage": "com.example.data"  `

`}`

The first two parameters are optional and specify the class and method which will be called when the application is (re)loaded on the server (re)start. 

"methodPackages" specifies where classes that implement the Method interface are. 
"dataPackage" specifies where data classes are. 
JRPC creates related objects by reflection using class.forName(<dataPackage> + "." + <dataType>). "dataType" comes with request. 

 
### Demo
The demo is [here](https://phoneparator.com/jrpc). 

### Getting started
The best way to start with JRPC is to download JRPC-example project from github. If you don't use Netbeans your IDE should have 
"Create a project from existing sources" option. Otherwise, please, change IDE or just copy-paste the code manually. 
JRPC-example project contains full demo code (client and server). 
The client uses jQuery. If you have this "we don't use jQuery anymore" attitude, please, take it as an opportunity and rewrite the client in "vanilla JavaScript". Lol.

### More simple Java frameworks
**JPaS** - stands for Java Pages Simplified. It is a simple web mini framework intended to create multi-page applications, i.e. applications that know
       how to process GET requests, created by the user clicking a link or bookmark. It does not use any templates e.g. JSP, Velocity, Thymeleaf, etc.
       Instead, it parses plain HTML at server start time using JSoup and modifies them thru Java API at request time. (not finished yet)   

**JSId** - stands for Java Simple Identification. It is an authentication framework that uses custom encrypted JWT (JSON Web Tokens) for authentication. 
       It consists of a JQuery plugin that renders login/register dialogs and a Java Servlet Filter that check credentials and generates tokens. 
          (not finished yet)  


### License
MIT license. 
