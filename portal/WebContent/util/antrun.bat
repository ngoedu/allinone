@ECHO OFF

set ANT_HOME=D:\Km\Tools\ant-1.10.1
set JAVA_HOME=D:\Km\NOC\jdk1.8.0_144
set BUILD_HOME=D:\Km\NOC\apache-tomcat-8.0.46\webapps\ROOT\util\ant

SET /P taskname=Please enter task name: 
IF "%taskname%"=="upload" GOTO Default
IF "%taskname%"=="list" GOTO ListTargets
ECHO Execute %taskname%...
call %ANT_HOME%\bin\ant.bat -buildfile %BUILD_HOME%\build.xml %taskname%
GOTO End

:ListTargets
call %ANT_HOME%\bin\ant.bat -p -buildfile %BUILD_HOME%\build.xml
GOTO End

:Default
call %ANT_HOME%\bin\ant.bat -buildfile %BUILD_HOME%\build.xml
GOTO End


:End
pause
