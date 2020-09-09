#!/usr/bin/env bash

# Stop on error
#set -euxo pipefail
set -e

echo "-------------------------------------------------"
echo "-                  LSPS DB Migration            -"
echo "-------------------------------------------------"
echo "..... LSPS migration"
if java -jar $JBOSS_HOME/lsps/lsps-db-migration-lsps-*-full.jar --databaseUrl "${LSPS_JDBC_URL}" --username $LSPS_DB_USER --password $LSPS_DB_PASSWORD; then
    echo "..... SUCCESS"
else
    echo "-------------------------------------------------"
    echo "-          LSPS DB Migration Failed!            -"
    echo "-------------------------------------------------"
    exit 1
fi

echo "-------------------------------------------------"
echo "-                  BAM DB Migration             -"
echo "-------------------------------------------------"
echo "..... BAM migration"
if java -jar $JBOSS_HOME/lsps/lsps-db-migration-bam-*-full.jar --databaseUrl "${LSPS_JDBC_URL}" --username $LSPS_DB_USER --password $LSPS_DB_PASSWORD; then
    echo "..... SUCCESS"
else
    echo "-------------------------------------------------"
    echo "-          BAM DB Migration Failed!             -"
    echo "-------------------------------------------------"
    exit 1
fi