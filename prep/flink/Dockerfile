FROM flink:1.17.1-scala_2.12-java11

# Download the connector libraries
RUN wget -P /opt/sql-client/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-connector-kafka/1.17.1/flink-sql-connector-kafka-1.17.1.jar; \
    wget -P /opt/sql-client/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-json/1.17.1/flink-json-1.17.1.jar;

# Download JDBC connector libraries
RUN wget -P /opt/sql-client/lib https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-jdbc/1.16.0/flink-connector-jdbc-1.16.0.jar; \
    wget -P /opt/sql-client/lib https://jdbc.postgresql.org/download/postgresql-42.6.0.jar

# Download cassandra connector libraries
RUN wget -P /opt/sql-client/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-cassandra_2.12/3.1.0-1.17/flink-connector-cassandra_2.12-3.1.0-1.17.jar; \
    wget -P /opt/flink/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-cassandra_2.12/3.1.0-1.17/flink-connector-cassandra_2.12-3.1.0-1.17.jar; 