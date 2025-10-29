# Hilfen Programmierung

## Lombok

Muss evtl. in der IDE eingeschalten werden:

### IntelliJ IDEA

1. File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
2. Haken bei "Enable annotation processing" setzen

## Chroma DB

[Chroma DB Doku](https://docs.trychroma.com/guides/deploy/docker)

```shell
docker run -v ./chroma-data:/data -p 8000:8000 chromadb/chroma
```