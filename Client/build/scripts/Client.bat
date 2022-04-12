@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Client startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and CLIENT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Client-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javafx-fxml-17.0.1-win.jar;%APP_HOME%\lib\javafx-controls-17.0.1-win.jar;%APP_HOME%\lib\javafx-controls-17.0.1.jar;%APP_HOME%\lib\javafx-graphics-17.0.1-win.jar;%APP_HOME%\lib\javafx-graphics-17.0.1.jar;%APP_HOME%\lib\javafx-base-17.0.1-win.jar;%APP_HOME%\lib\javafx-base-17.0.1.jar;%APP_HOME%\lib\Networking-1.0-SNAPSHOT.jar;%APP_HOME%\lib\Services-1.0-SNAPSHOT.jar;%APP_HOME%\lib\Model.jar;%APP_HOME%\lib\guava-31.0.1-jre.jar;%APP_HOME%\lib\log4j-core-2.10.0.jar;%APP_HOME%\lib\log4j-api-2.10.0.jar;%APP_HOME%\lib\iservice_2.11-2.4.1.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-3.12.0.jar;%APP_HOME%\lib\error_prone_annotations-2.7.1.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\scala-logging_2.11-3.5.0.jar;%APP_HOME%\lib\scala-request-json_2.11-2.2.jar;%APP_HOME%\lib\play-json-extensions_2.11-0.8.0.jar;%APP_HOME%\lib\play-json_2.11-2.5.9.jar;%APP_HOME%\lib\scala-compiler-2.11.8.jar;%APP_HOME%\lib\scala-reflect-2.11.8.jar;%APP_HOME%\lib\dispatch-core_2.11-0.11.4.jar;%APP_HOME%\lib\scala-uri_2.11-0.4.16.jar;%APP_HOME%\lib\play-json-naming_2.11-1.1.0.jar;%APP_HOME%\lib\play-iteratees_2.11-2.5.9.jar;%APP_HOME%\lib\play-functional_2.11-2.5.9.jar;%APP_HOME%\lib\play-datacommons_2.11-2.5.9.jar;%APP_HOME%\lib\parboiled_2.11-2.1.2.jar;%APP_HOME%\lib\spray-json_2.11-1.3.2.jar;%APP_HOME%\lib\scala-stm_2.11-0.7.jar;%APP_HOME%\lib\shapeless_2.11-2.3.0.jar;%APP_HOME%\lib\scala-xml_2.11-1.0.4.jar;%APP_HOME%\lib\scala-parser-combinators_2.11-1.0.4.jar;%APP_HOME%\lib\macro-compat_2.11-1.1.1.jar;%APP_HOME%\lib\scala-library-2.11.8.jar;%APP_HOME%\lib\logback-classic-1.2.3.jar;%APP_HOME%\lib\logback-core-1.2.3.jar;%APP_HOME%\lib\async-http-client-1.9.40.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\joda-time-2.9.4.jar;%APP_HOME%\lib\joda-convert-1.8.1.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.7.6.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.7.6.jar;%APP_HOME%\lib\jackson-databind-2.7.6.jar;%APP_HOME%\lib\jackson-core-2.7.6.jar;%APP_HOME%\lib\jackson-annotations-2.7.6.jar;%APP_HOME%\lib\config-1.3.0.jar;%APP_HOME%\lib\netty-3.10.6.Final.jar


@rem Execute Client
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %CLIENT_OPTS%  -classpath "%CLASSPATH%" StartRpcClient %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable CLIENT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%CLIENT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
