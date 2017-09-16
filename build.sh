#!/bin/bash
mvn clean install -f jfsql-parent/pom.xml
mvn clean compile assembly:single -f jfsql-parent/jfsql/pom.xml
