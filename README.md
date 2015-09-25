Elasticsearch Microservice
=================================

Scala Play 2.4.2 + Finagle +  Elasticsearch 


1) ./activator assembly


2) startserver.sh

```
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
```

```
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


http://localhost:9200/mx/postal_code/_search?pretty


curl -H "Content-Type: application/json" -X POST -d '
{
    "id"         : "3",
    "cp"         : 208,
    "colonia"    : "xxx",
    "ciudad"     : "yyy",
    "delegacion" : "zzz",
    "location": {
        "lat": 22.0074,
        "lon": -102.2837
    }
}' http://localhost:9000/api/create


curl -XDELETE http://localhost:9200/mx/postal_code/3.0

curl -XDELETE http://localhost:9000/api/delete/3


READ
POST
http://localhost:9000/api/read
{"term":"xxx"}


CREATE
POST
http://localhost:9000/api/create
{
    "id"         : 5602e292a552099c6067a5f3,
    "cp"         : 208,
    "colonia"    : "xxx",
    "ciudad"     : "yyy",
    "delegacion" : "zzz",
    "location": {
        "lat": 22.0074,
        "lon": -102.2837
    }
}


UPDATE
POST
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


DELETE
DELETE
http://localhost:9000/api/delete/3.0