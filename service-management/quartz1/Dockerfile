###
# vert.x docker example using a Java verticle
# To build:
#  docker build -t quartz1 .
# To run:
#   docker run -t -i --name=quartz1 quartz1
# To start:
#   docker start quartz1
###

# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME com.stulsoft.poc.service.management.quartz1.Quartz1MainVerticle
ENV VERTICLE_FILE target/quartz1-0.0.1-SNAPSHOT-jar-with-dependencies.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

# Copy your verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]