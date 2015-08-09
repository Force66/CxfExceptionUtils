# CxfExceptionUtils  
The exceptions that CXF generates for WSDL soap faults (e.g. input validations, etc) are less than helpful.  The main reason I say that is that all meaningful information somebody would need to solve the issue causing the exception is placed by Cxf in a FaultInfo field not natively reported in the exception CXF generates using wsdl2java.  This project contains root exceptions you can use that are *much* more convenient and provide needed information to support personnel.

## System Requirements  
* Java JDK 6.0 or above (it was compiled under JDK 7 using 1.6 as the target source).
* Apache Commons Lang version 3.0 or above

## Installation Instructions  
CxfExceptionUtils is easy to install whether you use maven or not.

### Example stack trace print using CxfSoapFaultException or CxfSoapFaultRuntimeException  
```  
com.postini.pstn.soapapi.v2.automatedbatch.BatchException_Exception: meaningless exception message
Exception Context:
 [1:cxfclient.message=meaningful embedded cxf info]
---------------------------------
 at org.force66.cxfutils.CxfSoapFaultRuntimeExceptionTest.testBasic(CxfSoapFaultRuntimeExceptionTest.java:28)
 at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)

 at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 ```  
 ### Example stack trace normal CXF exception generation  
 ```  
 com.postini.pstn.soapapi.v2.automatedbatch.BatchException_Exception: meaningles exception message
 at org.force66.cxfutils.CxfSoapFaultRuntimeExceptionTest.testBasic(CxfSoapFaultRuntimeExceptionTest.java:28)
 at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)

 at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 ```  

### Maven Users  
Maven users can find dependency information [here](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22CxfExceptionUtils%22).

### Non-Maven Users  
Include the following jars in your class path:  
* Download the CxfExceptionUtils jar from [Github](https://github.com/Force66/CxfExceptionUtils/releases) and put it in your class path.  
* Insure Apache Commons Lang version 3.0 or above is in your class path.  

## Usage Instructions  
Specify either CxfSoapFaultException or CxfSoapFaultRuntimeException as the root exception used with Cxf' wsdl2java.  

### Sample maven wsdl2java configuration
```  
<plugin>
	<groupId>org.apache.cxf</groupId>
	<artifactId>cxf-codegen-plugin</artifactId>
	<version>${cxf.version}</version>
	<executions>
		<execution>
			<id>generate-sources</id>
			<phase>generate-sources</phase>
			<configuration>
				<sourceRoot>${project.build.directory}/generated/cxf</sourceRoot>
				<wsdlRoot>${basedir}/src/main/resources/wsdl</wsdlRoot>
				<includes>
					<include>*.wsdl</include>
				</includes>

				<defaultOptions>
					<exceptionSuper>org.force66.cxfutils.CxfSoapFaultRuntimeException</exceptionSuper>
				</defaultOptions>
			</configuration>

			<goals>
				<goal>wsdl2java</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```  


