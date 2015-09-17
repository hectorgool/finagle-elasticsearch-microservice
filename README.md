Elasticsearch Microservice
=================================

Scala Play 2.4.2 + Finagle +  Elasticsearch 


1) ./activator assembly


2) startserver.sh


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


http://localhost:9200/mx/postal_code/_search?pretty


curl -H "Content-Type: application/json" -X POST -d '
{
    "id"         : 3,
    "cp"         : 208,
    "colonia"    : "xxx",
    "ciudad"     : "yyy",
    "delegacion" : "zzz",
    "location": {
        "lat": 22.0074,
        "lon": -102.2837
    }
}' http://localhost:9000/api/save


curl -XDELETE http://localhost:9200/mx/postal_code/3


curl -H "Content-Type: application/json" -X POST -d '
{
    "id"         : 3
}' http://localhost:9000/api/delete