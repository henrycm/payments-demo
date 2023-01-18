# Checking kafka messages

```
docker exec --interactive --tty kafka_c \
kafka-console-consumer --bootstrap-server kafka_c:9092 \
                       --topic payments
```

## POST a payment

```
curl -i http://localhost:8080/payments -H "Content-Type: application/json" -d '{"id":"54ccd1af-82c0-4976-9ef4-e9dd10763c99_3","currency":"USD","amount":100.0,"originator":{"id":1,"name":"Bob"},"beneficiary":{"id":1,"name":"Ben"},"sender":{"accountType":"CHECKING","accountNumber":1005555552},"receiver":{"accountType":"SAVINGS","accountNumber":1005555551},"status":null}'
```