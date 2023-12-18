## log4shell true positive

This is a simple web project with a log4shell vulnerability. 
The project defines a simple `scabench.HelloWorldService` get service returning a plain text string `hello world`.
The service does not expect parameters, and if parameters are encountered, an error 
is logged and 400 is returned. 

The vulnerable dependency is [org.apache.logging.log4j:log4j-core:2.14.1](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core/2.14.1), the vulnerability is [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228). 

A unit test is provided to demonstrate the vulnerability. The test need a ldap server providing vulnerable code, the respective executable is provided by the project, it is defined in a separate project [https://github.com/jensdietrich/Log4J-RCE-Proof-Of-Concept](https://github.com/jensdietrich/Log4J-RCE-Proof-Of-Concept) based on [https://github.com/HyCraftHD/Log4J-RCE-Proof-Of-Concept](https://github.com/HyCraftHD/Log4J-RCE-Proof-Of-Concept).

The test will create a file `foo` based on a command encoded in a request parameter. 




