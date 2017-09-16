#!/bin/bash
mvn clean install -f jfsql-parent/pom.xml
mvn clean compile assembly:single -f jfsql-parent/jfsql/pom.xml
sudo cp jfsql-parent/jfsql/target/jfsql.jar /usr/bin/jfsql.jar
sudo cp jfsql-parent/jfsql/src/main/resources/jfsql /usr/bin/jfsql
