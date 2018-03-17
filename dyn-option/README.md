# dyn-option
Exercises with dynamic options from running command line

Running Main from IntelliJ with following configuration:
  - VM Options: -DmyParam=123 -DmyJavaHome=${JAVA_HOME}
  - Program arguments: param1 ${JAVA_HOME}
  
 Result:
 ```
 Args:
 param1
 D:\Applications\Java\jdk_1.8
 
 System environment variables (only started with my-prefix):
 name=myJavaHome, value=D:\Applications\Java\jdk_1.8
 name=myParam, value=123
``` 
