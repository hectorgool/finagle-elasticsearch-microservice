curl -XPOST "http://localhost:9200/mx/postal_code/1" -d "
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


curl -XPOST "http://localhost:9200/mx/postal_code/2" -d "
{
    \"cp\"         : \"20009\",
    \"colonia\"    : \"xxx\",
    \"ciudad\"     : \"yyy\",
    \"delegacion\" : \"kkk\",
    \"location\": {
        \"lat\": \"21.0074\",
        \"lon\": \"-100.2837\"
    }
}"