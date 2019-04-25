#!/bin/sh
set -e
properties="aliapm.properties"
name=$APP_NAME
if [  -e $properties ]; then
    if [ -n $env ]; then
	    name="$name-$env";
    fi
    sed -i "s/appName=.*/appName=$name/g" $properties
fi
java $1 $2 -Djava.security.egd=file:/dev/./urandom -jar $JAR_PATH --spring.profiles.active=$env
exec "$@"