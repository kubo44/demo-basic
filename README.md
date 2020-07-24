# Custom LSPS Application

This is a customized LSPS Application, business application with GO-BPMN models
based on the default LSPS Application.

<!--TODO: Insert information on your application.-->

The application has been customized based on the instructions in the
[custom application guide](https://lsps-docs.whitestein.com/data/DOCVERSION/custom-application/applicationapi.html).

# Quick Start

To further adjust the application and test it locally,
1. [Install PDS Enterprise](https://lsps-docs.whitestein.com/data/DOCVERSION/pds/enterpriseedition.html)
2. [Import the sources to the IDE](https://lsps-docs.whitestein.com/data/DOCVERSION/custom-application/importingexistingresources.html)
3. [Run a local server with the application](https://lsps-docs.whitestein.com/data/DOCVERSION/custom-application/importingexistingresources.html)

<!--TODO: describe any customizations and custom API you introduced to your application-->

# Features

## Custom Theme

[Information on how to create a custom theme](https://lsps-docs-stage.whitestein.com/data/DOCVERSION/custom-application/customizingthemes.html)

## Custom Objects

[Information on how to create custom functions, task types, and form components](https://lsps-docs.whitestein.com/data/DOCVERSION/custom-application/creatingcustomobjects.html)

<!--
## Models and Modules

* `main`: module that imports any other modules
* `user-interface`: module with a demo GUI forms
-->

# Build

To build the application, run `mvn clean install` in the root of the application.

Further information on building is available
[here](https://lsps-docs.whitestein.com/data/DOCVERSION/custom-application/buildinganddeploying.html#buildingfordeployment).

## Docker

When you run `mvn clean install -Plsps.docker` default docker image for the app is build.

project_name=$(printf 'ARTIFACT_ID=${project.artifactId}\n0\n' | mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate | grep '^ARTIFACT_ID'|cut -d= -f2)
It can be started by the command (replace variables):
`docker run --name=lsps -d -p 8080:8080 ${docker.tag.prefix}/${project.artifactId}:latest`
e.g.
`docker run --name=lsps -d -p 8080:8080 docker.k8s.whitestein.com/lsps/lsps-app:latest`

Usefull docker commands:
`docker exec -it lsps bash`	enter the container
`docker logs lsps --follow`	see logs
`docker images` list local images
`docker container ls -a` list all local containers
`docker system prune` remove not used resources

# Deployment

To deploy your application:
1. [Set up the target environment](https://lsps-docs.whitestein.com/data/DOCVERSION/server-deployment/index.html)
2. Deploy the ear to the server.
3. [Export your modules with GO-BPMN export](https://lsps-docs-stage.whitestein.com/data/DOCVERSION/pds/export.html#exportingamoduleusingthego-bpmnexportfeature) and [upload them to the server](https://lsps-docs-stage.whitestein.com/data/DOCVERSION/management/modulemanagement.html#moduleupload).
4. [Run the models as applicable](https://lsps-docs-stage.whitestein.com/data/DOCVERSION/management/modelinstancemanagement.html#creatingmodelinstance).

Make sure the underlying database tables (LSPS system tables, application's tables
with business data, and tables of BAM and Scaffolding libraries) use correct structure
(you might need to update the database tables if the structures have changed).

Instructions on database migration are available [here](https://lsps-docs.whitestein.com/data/DOCVERSION/server-deployment/serverupgrade.html#dbsystemtablesupgrade).

# Documentation
<!--insert links to the documentation of your application-->
<!--## Application Documentation

TODO
-->

## LSPS Documentation

- [LSPS Quickstart](https://lsps-docs.whitestein.com/data/DOCVERSION/quickstart/index.html)
- [LSPS Academy](https://lsps-docs.whitestein.com/data/DOCVERSION/academy/index.html)

For further information on LSPS and BPMN models refer to
[official LSPS documentation](https://lsps-docs.whitestein.com/data/DOCVERSION/index.html).
