# About
Spring boot demo application with stack:
- Postgres
- Spring-data
- Spring REST controllers
- Kafka
- Docker composer

## Running the application
From the project's root folder run:
```
docker compose up
```

## Running the client
This will create 10 Payments:
```
docker exec -it client_c bash

mvn package

java -jar target/payment-client-0.0.1.jar --numPayments=10

```

## Running automated tests
```
docker exec -it client_c bash

cd ../backend

mvn test
```

## Query the payment table
```
docker exec -it pg_c psql -h localhost -U postgres -d portx -c "SELECT * FROM payment"
```

## Checking kafka messages
```
docker exec --interactive --tty kafka_c \
kafka-console-consumer --bootstrap-server kafka_c:9092 \
                       --topic payments
```
