# inventory-api

Project containing a simple REST API in Java/ Spring.

Build
===

The application can be build as follows:
```
mvn clean install
```

To build skipping the tests:
```
mvn clean install -DskipTests 
```


Test
===


The tests in the application can be build using:
```
mvn test
```

Run
===

The application uses different profiles so to run the application in development:

```
mvn spring-boot:run -DSPRING_PROFILES_ACTIVE=dev
```

The application (swagger) should be up and running under port 8080:

http://localhost:8080
