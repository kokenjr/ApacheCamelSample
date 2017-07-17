# Backbase Exercise
Backend server and services for Backbase transactions exercise.

This application uses Java 1.8, Spring, Tomcat, and Camel.  Maven is used for building the application.


## Build

To run the application, use the following command in the root of the project:

```mvn tomcat7:run```

This will start the camel server on port 8081 of localhost (i.e. [http://localhost:8081](http://localhost:8081)).

## API Doc
You can access the Swagger doc here: [http://localhost:8081/api-doc](http://localhost:8081/api-doc)

## Test

You can run unit and integration tests with the following command:

``` mvn test ```

You can also test using [cURL](github.com/curl/curl), while the server is running. 

To get all transactions:
``` curl -u backbase:B4CKB453 http://localhost:8081/api/transactions ```

To get all transactions filtered by type:
``` curl -u backbase:B4CKB453 http://localhost:8081/api/transactions/type/sandbox-payment ```

To get total transaction amount for a specific transaction type:
``` curl -u backbase:B4CKB453 http://localhost:8081/api/transactions/type/sandbox-payment/amount ```
