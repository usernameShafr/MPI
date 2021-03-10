#!/bin/sh -x
exec java -jar /app.jar --db_host=postgres --db_name="$POSTGRES_DB" --db_username="$POSTGRES_USER" --db_password="$POSTGRES_PASSWORD"