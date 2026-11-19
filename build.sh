#!/bin/sh
# change this path to YOUR Tomcat folder
TOMCAT_HOME=/opt/apache-tomcat-9.0.90

mkdir -p WebContent/WEB-INF/classes
javac -encoding UTF-8 -cp "$TOMCAT_HOME/lib/servlet-api.jar" -d WebContent/WEB-INF/classes src/com/recipehouse/*.java || exit 1
mkdir -p "$TOMCAT_HOME/webapps/RecipeHouse"
cp -r WebContent/* "$TOMCAT_HOME/webapps/RecipeHouse/"
echo "Done! Start Tomcat and open http://localhost:8080/RecipeHouse/"
