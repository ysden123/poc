FROM openjdk:8-jre-alpine

ARG APP_FILE=workflow-loader-1.0.0-exec.jar
ENV APP_FILE $APP_FILE
ENV APPLICATION_HOME /usr/apps/workflow-loader

ENV JAVA_OPTS ""

COPY target/$APP_FILE $APPLICATION_HOME/

WORKDIR $APPLICATION_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java $JAVA_OPTS -jar $APP_FILE"]