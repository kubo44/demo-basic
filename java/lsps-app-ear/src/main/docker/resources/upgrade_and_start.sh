#!/usr/bin/env bash

# Stop on error
#set -euxo pipefail
set -e

function wait_for_server() {
  echo "=> Waiting for the server to boot"
  until `$JBOSS_HOME/bin/jboss-cli.sh --controller=localhost:9990 -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
	echo "=> Waiting..."
  done
}

$JBOSS_HOME/lsps/lsps-db-migrations.sh

echo "-------------------------------------------------"
echo "-              START WILDFLY TEMPORARILY        -"
echo "-------------------------------------------------"
rm -f nohup.out
nohup $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

wait_for_server

# Must be done after server is started
$JBOSS_HOME/lsps/upload-model_app-db-migration.sh

# Keep WF as last command to occupy console (and keed container running)
echo "-------------------------------------------------"
echo "-                  START WILDFLY                -"
echo "-------------------------------------------------"
$JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0