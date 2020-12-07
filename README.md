# REST API built with Jersey and MongoDB

Description
* the database consists of details about several fictional companies.
* dependency injection using HK2 framework
* created tests with JUnit

# Project Setup

1. Clone the repository
2. Make sure you are in the **mongodbproject** directory
2. Build the project
```
mvn clean install
```
4. Build the docker image
```
docker build --tag mongodbproject:1.0 .
```
5. Start the container
```
docker run --publish 4000:4000 --detach --name bb mongodbproject:1.0
```
6. Access the endpoints
