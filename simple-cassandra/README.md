# simple-cassandra
Playing with Cassandra on Scala.

## Installation
https://academy.datastax.com/planet-cassandra/cassandra

## Config
Define cdc_raw_directory in apache-cassandra\conf\cassandra.yaml

## Create keyspace
 1. Run cqlsh.bat
 1. Run
 ```
create keyspace test1 with replication={'class':'SimpleStrategy', 'replication_factor':1};
```
 