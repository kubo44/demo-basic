#!/usr/bin/env bash

# Stop on error
#set -euxo pipefail
set -e

echo "Wait to db to be ready"
$JBOSS_HOME/lsps/wait-for-it.sh -h $LSPS_DB_HOST -p $LSPS_DB_PORT -t 0

# Grant user lsps right XA_RECOVER_ADMIN to recover XA transactions (on mysql8 only root user has it by default)
mysql -h $LSPS_DB_HOST --port=$LSPS_DB_PORT -uroot -p$LSPS_DB_ROOT_PASSWORD $LSPS_DB_NAME -e "GRANT XA_RECOVER_ADMIN ON *.* TO '$LSPS_DB_USER'@'%';FLUSH PRIVILEGES;SET GLOBAL time_zone = '+1:00';" 

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