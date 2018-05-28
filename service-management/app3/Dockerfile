FROM openjdk:8-jdk-alpine                                           

ARG VERTICLE_FILE_ARG=app3-0.0.1-SNAPSHOT-fat.jar     
ENV VERTICLE_FILE=$VERTICLE_FILE_ARG
# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

#Configuring the Java Virtual Machine
ENV JAVA_OPTS ""

#vertx syatem variales configured though env variable
ENV VERTX_OPTS "-Dvertx.cluster.managerClass=io.vertx.spi.cluster.hazelcast.HazelcastClusterManager"

# Copy your fat jar to the container
COPY target/$VERTICLE_FILE $VERTICLE_HOME/                         

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java $DEBUG $JAVA_OPTS $VERTX_OPTS -jar $VERTICLE_FILE -cluster"]
