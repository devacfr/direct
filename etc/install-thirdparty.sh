#!/bin/sh

mvn install:install-file -DgroupId=com.softwarementors.extjs -DartifactId=directjngine -Dversion=2.2 -Dpackaging=jar -Dfile=./maven/directjngine.2.2.jar -DpomFile=./maven/directjngine.2.2-pom.xml -Dsources=./maven/directjngine.2.2-sources.jar -Djavadoc=./maven/directjngine.2.2-javadoc.jar
