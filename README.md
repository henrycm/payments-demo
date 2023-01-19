## Running the application
From the project's root folder run:
```
docker composer up
```

## Running the client
This will create 10 Payments:
```
docker exec -it client_c bash

mvn package

java -jar target/payment-client-0.0.1.jar --payload="{\"currency\":\"USD\",\"amount\":100.0,\"originator\":{\"id\":1,\"name\":\"Bob\"},\"beneficiary\":{\"id\":2,\"name\":\"Ben\"},\"sender\":{\"accountType\":\"CHECKING\",\"accountNumber\":1005555552},\"receiver\":{\"accountType\":\"SAVINGS\",\"accountNumber\":1005555551},\"status\":null}" --numPayments=10

```

## Running automated tests
```
docker exec -it client_c bash

cd ../backend

mvn test
```

## Query the payment table
From the host:
```
psql -h localhost -U postgres -d portx -c "SELECT * FROM payment"
```

## Checking kafka messages
From the host:
```
docker exec --interactive --tty kafka_c \
kafka-console-consumer --bootstrap-server kafka_c:9092 \
                       --topic payments
```
