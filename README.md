# Elasticsearch Microservice

An API REST using Scala Play + Finagle + Elasticsearch 

## Current Version

For Play 2.4.2:

```scala
  "com.twitter" % "finagle-http_2.11"   % "6.27.0",
  "com.twitter" % "bijection-util_2.11" % "0.8.1"
```

### Installation

You need type:
```sh
./elasticsearch_index_mappings.sh
./load_documents.sh

$ activator
[fara] $ clean
[fara] $ reload
[fara] $ update
[fara] $ compile
[fara] $ run
```
## CRUD in Elasticsearch
Save 2 documents:
```sh
curl -X PUT "http://localhost:9200/mx/postal_code/2" -d "
{
    \"cp\"         : \"20008\",
    \"colonia\"    : \"Delegación de La Secretaría de Comercio y Fomento Industrial\",
    \"ciudad\"     : \"Aguascalientes\",
    \"delegacion\" : \"Aguascalientes\",
    \"location\": {
        \"lat\": \"22.0074\",
        \"lon\": \"-102.2837\"
    }
}"
curl -XPOST "http://localhost:9200/mx/postal_code" -d "
{
    \"id\"         : \"20008\",
    \"cp\"         : \"20008\",
    \"colonia\"    : \"Delegación de La Secretaría de Comercio y Fomento Industrial\",
    \"ciudad\"     : \"Aguascalientes\",
    \"delegacion\" : \"Aguascalientes\",
    \"location\": {
        \"lat\": \"22.0074\",
        \"lon\": \"-102.2837\"
    }
}"
```
View documents:
```sh
http://localhost:9200/mx/postal_code/_search?pretty
```
Delete document:
```sh
curl -XDELETE http://localhost:9200/mx/postal_code/3
```

## CRUD in API REST

For example i use HttpRequester ([Mozilla Firefox extension](https://addons.mozilla.org/es/firefox/addon/httprequester/)).
HttpRequester is a tool for easily making HTTP requests (GET/PUT/POST/DELETE), viewing the responses, and keeping a history of transactions.

READ (POST for HttpRequester):
```sh
http://localhost:9000/api/read
{"term":"xxx"}
```

CREATE (POST for HttpRequester):
```sh
http://localhost:9000/api/create
{
    "id"         : "5602e292a552099c6067a5f3",
    "cp"         : "208",
    "colonia"    : "xxx",
    "ciudad"     : "yyy",
    "delegacion" : "zzz",
    "location": {
        "lat": 22.0074,
        "lon": -102.2837
    }
}
```
UPDATE (POST for HttpRequester):
```sh
http://localhost:9000/api/update
{
    "id"         : 3,
    "cp"         : 207,
    "colonia"    : "AAAAAAAAAAAAAAAA",
    "ciudad"     : "BBBBBBBBBBBBBBBB",
    "delegacion" : "CCCCCCCCCCCCCCCC",
    "location": {
        "lat": 22,
        "lon": -102
    }
}
```

DELETE (DELETE for HttpRequester)
```sh
http://localhost:9000/api/delete/3
```



