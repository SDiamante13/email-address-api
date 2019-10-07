## Email Address API

Please write a web service that takes in a list of email addresses and returns an integer indicating the number of unique
email addresses. Where "unique" email addresses means they will be delivered to the same account using Gmail account matching.
Specifically: Gmail will ignore the placement of "." in the username. And it will ignore any portion of the username after a "+".

Examples:
test.email@gmail.com, test.email+spam@gmail.com and testemail@gmail.com will all go to the same address, and thus the result should be 1.

## Running the app

This app is written in Kotlin and targets the 1.8 JVM version (1.8.0_181).

## Using the app 

After running the Spring Boot Application in your IDE of choice, you may use a REST client like Postman, Insomnia, or the terminal to access the API.

#### Request example

```json

{ "emailAddresses": ["steven.diamante+spam@gmail.com", "bob.smith@gmail.com", "susanyang+somethinggmail.com",
                    "bob.smith+tank@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com"] }
```

#### Expected output

```json
{
  "emailAddressCount": 3
}
```

#### Curl example

To run a curl command from the terminal: 

```
curl --request POST \
  --url http://localhost:8080/email/count \
  --header 'content-type: application/json' \
  --data '{ "emailAddresses": 
 ["steven.diamante+spam@gmail.com", "bob.smith@gmail.com", "susanyang+somethinggmail.com",
            "bob.smith+tank@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com"] }'
```
