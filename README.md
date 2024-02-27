# README

## What is this api for? ##

* API back end application for an e-commerce
    * Create a Cart which will hold products
    * Get a Cart information given its id
    * Add one or more products to that Cart
    * Delete a Cart
    * A Cart will have a TTL of 10 minutes

## How to build and run this app ##

### Pre-requisites ###

In order to build this app the following software should be installed and added to your PATH:
- JDK 11.0.10 (<https://jdk.java.net/archive/>)
- Apache Maven 3 (3.8.1+ recommended) (<https://maven.apache.org/download.cgi>)

### Building and running ###

First, compile and generate the jar artifact.

```
mvn clean install
```

At this point you could run locally your service with:

```
java -jar target/onebox-1.0-SNAPSHOT.jar
```

Once started, the service can be reached at <http://localhost:8080>.

Then, create the docker image with this:

```
docker build -t onebox .
```

Run docker image with this:

```
docker run -it -p 8080:8080 onebox
```

Create a Cart which holds products:

POST http://localhost:8080/cart

``````
{
    "id": 1,
    "products": [
        {
            "id": 1,
            "description": "coca-cola",
            "amount": 2.5
        }
    ]
}
``````

Update a Cart

PUT http://localhost:8080/cart/

````
{
    "id": 1,
    "products": [
        {
            "id": 1,
            "description": "coca-cola",
            "amount": 2.5
        },
                {
            "id": 2,
            "description": "pepsi",
            "amount": 3.5
        },
           {
            "id": 3,
            "description": "choco",
            "amount": 3.7
        }
    ]
}
````

Get a Cart given its Id

GET http://localhost:8080/cart/1

Delete a Cart given its id

DELETE http://localhost:8080/cart/1


## Unit Testing Reports

One class as been unit tested, it is CartServiceImplTest
* We can generate the Unit Tests with this:

```
mvn clean verify
```

## Integration Testing

Controller class has been tested, class name is CartControllerITTest

## Final thoughts

The Cart has been designed to be volatile in the sense that introducing the keyboard 'volatile' to 
the instance it is safe to share the class amongst other threads in execution in the runtime
environment, this is not achieved only by providing the keyboard 'volatile' but designing it as a
singleton and synchronizing the precise instantiation moment that prevents race condition on other 
threads while instantiating.

The Cart is volatile in a sense that it does not reside in a permanent way in any db or file, it 
lives in memory and it does for a period of time after that it is removed from the dynamic repository


