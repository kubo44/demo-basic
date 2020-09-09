#!/usr/bin/env bash

# Stop on error
#set -euxo pipefail
set -e

echo "----------------------------------------"
echo "-             Upload Model             -"
echo "----------------------------------------"
if java -jar $JBOSS_HOME/lsps/lsps-cli-*-full.jar modelUpload -d http://localhost:8080 -m $JBOSS_HOME/lsps/*.zip -u admin -p admin --dbUpdateStrategy update; then
  echo "...Upload Model SUCCESS"
else
  echo "...Upload Model FAILED exit"
  $JBOSS_HOME/bin/jboss-cli.sh  --connect controller=localhost:9990 command=:shutdown
  exit 1
fi

# This is needed to run tests
echo "----------------------------------------"
echo "-             Enable Drop Create       -"
echo "----------------------------------------"
if java -jar $JBOSS_HOME/lsps/lsps-cli-*-full.jar settingUpdate -d http://localhost:8080 -u admin -p admin -n ENABLE_DROP_CREATE -v true; then
  echo "...Enable Drop Create SUCCESS"
else
  echo "...Enable Drop Create FAILED exit"
  $JBOSS_HOME/bin/jboss-cli.sh  --connect controller=localhost:9990 command=:shutdown
  exit 1
fi

echo "=> Shutting down WildFly"
$JBOSS_HOME/bin/jboss-cli.sh  --connect controller=localhost:9990 command=:shutdown

echo "=> Waiting for the server to stop"
PID=$(cat "$JBOSS_HOME/pid")
while [ -e "/proc/$PID" ]; do
    sleep 1
	echo "=> Waiting for WF to finish..."
done

echo "-------------------------------------------------"
echo "-                  APP DB Migration             -"
echo "-------------------------------------------------"
appDbMigration=$(find $JBOSS_HOME/lsps/ -name "*db-migration-*-full.jar" | grep -vE 'lsps-db-migration-lsps|lsps-db-migration-bam')
if java -jar $appDbMigration --databaseUrl "${LSPS_JDBC_URL}" --username $LSPS_DB_USER --password $LSPS_DB_PASSWORD; then
    echo "..... APP DB Migration SUCCESS"
else
    echo "-------------------------------------------------"
    echo "-          APP DB Migration Failed!             -"
    echo "-------------------------------------------------"
    exit 1
fi