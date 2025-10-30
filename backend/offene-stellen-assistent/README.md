# Hilfen Programmierung

## Lombok

Muss evtl. in der IDE eingeschalten werden:

### IntelliJ IDEA

1. File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
2. Haken bei "Enable annotation processing" setzen

## Redis DB

[Chroma DB Doku](https://docs.trychroma.com/guides/deploy/docker)

```shell
 docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 -e REDIS_ARGS="--requirepass mypassword" redis/redis-stack:latest
```