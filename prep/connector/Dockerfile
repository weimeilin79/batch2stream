FROM debezium/connect-base:2.5

LABEL maintainer="Debezium Community"

ENV DEBEZIUM_VERSION="1.5.4.Final" \
    MAVEN_REPO_CENTRAL="" \
    MAVEN_REPOS_ADDITIONAL="" \
    MAVEN_DEP_DESTINATION=$KAFKA_CONNECT_PLUGINS_DIR \
    POSTGRES_MD5=2488cff8b332186accac42f6fc91c7e0 
   # \ SCRIPTING_MD5=726a92ff8ee1eaaec8dfd21980623bbe 
   # \ KAFKA_CONNECT_CASSANDRA_DIR=$KAFKA_CONNECT_PLUGINS_DIR/kafka-connect-cassandra

RUN docker-maven-download debezium postgres "$DEBEZIUM_VERSION" "$POSTGRES_MD5" 
    # && \ docker-maven-download debezium-optional scripting "$DEBEZIUM_VERSION" "$SCRIPTING_MD5"

#RUN mkdir $KAFKA_CONNECT_CASSANDRA_DIR 

#COPY debezium-connector-cassandra-4 $KAFKA_CONNECT_CASSANDRA_DIR
#COPY ./config /kafka/config