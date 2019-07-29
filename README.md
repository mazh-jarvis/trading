# Introduction
<p>
A trading application that simulates managing stocks associated with an account. The target users for this app are front-end, full-stack, and back-end(testing purposes) developers. This app is an example of microservices that uses [IEX API](https://iexcloud.io/) to retrieve stock data and stores information using a Postgres database.
</p>

- [Quick Start](#quick-start)
    - [Git clone and Maven build](#git-clone-and-maven-build)
    - [Postgres initialization](#postgres-initialization)
    - [How to consume REST API?](#how-to-consume-rest-api?)
- [REST API Usage](#rest-api-usage)
    - [Swagger](#swagger)
    - [Quote controller](#quote-controller)
    - [Trader controller](#trader-controller)
    - [Order controller](#order-controller)
    - [App controller](#app-controller)
    - [Dashboard controller](#dashboard-controller)
- [Architecture](#architecture)
    - [Controller](#controller)
    - [Service](#service)
    - [DAO](#dao)
    - [SpringBoot](#springboot)
    - [Postgres and Iex](#postgres-and-iex)
- [Improvements](#improvements)

## Quick Start
**Prerequisites:** Java 1.8, Docker 10.x, CentOS 7

The following is the start-up script for this app. It will start docker, set up the required database setup and environment variables.
```bash
#!/bin/bash
set -e
cd "$(dirname "$0")"
​
ls lib/trading-1.0-SNAPSHOT.jar
​
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi
​
export SPRING_PROFILES_ACTIVE=$1
export PSQL_HOST=$2
export PSQL_USER=$3
export PSQL_PASSWORD=$4
export IEX_PUB_TOKEN=$5
export PGPASSWORD=$PSQL_PASSWORD
export PSQL_URL="jdbc:postgresql://${PSQL_HOST}:5432/jrvstrading"
​
#start docker
systemctl status docker || systemctl start docker || sleep 5
​
#create docker volume to persist db data
docker volume ls | grep "pgdata" || docker volume create pgdata || sleep 1
​
#stop existing jrvs-psql container
docker ps | grep jrvs-psql && docker stop $(docker ps | grep jrvs-psql | awk '{print $1}')
​
#start docker
docker run --rm --name jrvs-psql -e POSTGRES_PASSWORD=$PSQL_PASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 $PSQL_USER
sleep 5
​
psql -h $PSQL_HOST -U $PSQL_USER -f ./sql_ddl/init_db.sql
psql -h $PSQL_HOST -U $PSQL_USER -d jrvstrading -f ./sql_ddl/schema.sql
​
#run springboot app
/usr/bin/java -jar ./lib/trading-1.0-SNAPSHOT.jar
```


### Git clone and Maven build
Todo

### Postgres initialization
Todo

### How to consume REST API?
Todo(use swagger generator)

# Rest API Usage
## Swagger
todo
## Quote controller
todo
## Trader controller
todo
## App controller
todo
## Dashboard controller
todo

# Architecture
### Class diagram
![](assets/images/TradingAppCD.png)

### Entity relationship
![](assets/images/TradingAppER.png)

### Controller
todo
### Service
todo
### DAO
todo
### SpringBoot
todo
### Postgres and IEX
todo

# Improvements
todo (5+ points)