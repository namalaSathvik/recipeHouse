@echo off
setlocal

REM ===== Configure your Tomcat installation =====
set "TOMCAT_HOME=C:\Users\namal\Downloads\apache-tomcat-9.0.123\apache-tomcat-9.0.123"
set "SERVLET_API=%TOMCAT_HOME%\lib\servlet-api.jar"
set "CLASSES=WebContent\WEB-INF\classes"
set "DEPLOY=%TOMCAT_HOME%\webapps\RecipeHouse"

REM ===== Check Tomcat and Servlet API =====
if not exist "%SERVLET_API%" (
    echo ERROR: servlet-api.jar not found:
    echo %SERVLET_API%
    echo Please confirm TOMCAT_HOME points to your Tomcat 9 installation.
    goto error
)

if not exist "%CLASSES%" mkdir "%CLASSES%"

REM ===== Compile servlets =====
echo Compiling Java servlets...
javac -encoding UTF-8 -cp "%SERVLET_API%" -d "%CLASSES%" src\com\recipehouse\*.java
if errorlevel 1 goto error

REM ===== Deploy web application =====
if not exist "%TOMCAT_HOME%\webapps" (
    echo ERROR: Tomcat webapps folder not found: %TOMCAT_HOME%\webapps
    goto error
)
if exist "%DEPLOY%" rmdir /S /Q "%DEPLOY%"
xcopy /E /I /Y "WebContent" "%DEPLOY%" >nul
if errorlevel 1 goto error

echo.
echo BUILD SUCCESSFUL!
echo Deployed to: %DEPLOY%
echo Start Tomcat and open http://localhost:8080/RecipeHouse/
goto end

:error
echo.
echo BUILD FAILED. Check the error messages above.

:end
pause
endlocal
