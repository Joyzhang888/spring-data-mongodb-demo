# spring-data-mongodb-demo

Mongo DB with Spring Boot Application using Spring Data MongoDB 

This application helps you to integrate your spring boot application with MongoDB

#### Pre-requisites:

Up and running instance of MongoDb. If you do not have MongoDB installed on your machine please follow the link https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/. This link provides step by step installation guide.

#### Start the Mongodb database:

Go to the location where mongodb data/db folder is created.In my system the location is /Users/vinayanayak/data-mongodb/mongodb/data/db and start the service

mongod --dbpath=.

#### Build the service:

mvn clean install

Once the build is successful you can notice the query classes for your pojos.For example for Hotel.java you can observe that QHotel.java is generated under target/generated-sources/annotations directory

#### Start the service:

mvn spring-boot:run

#### REST Endpoints:

GET http://localhost:8085/hotels/all

PUT http://localhost:8085/hotels

POST http://localhost:8085/hotels

DELETE http://localhost:8085/hotels

GET http://localhost:8085/hotels/{id}

GET http://localhost:8085/hotels/price/{maxPrice}

GET http://localhost:8085/hotels/address/city

Find all the Hotels in a country:

http://localhost:8085/hotels/address/country/{country}

Find all the Hotels with maximum priceperNight is 150 and having atleast one rating greater than 5:

GET http://localhost:8085/hotels/recommended
