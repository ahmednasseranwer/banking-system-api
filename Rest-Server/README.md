# Banking system Apis

#### Java 8, spring boot

####  Access API: http://localhost:8888/swagger-ui/index.html

### Pre-requisite
-   need to start docker platform 

###  Start Project 
1. run file "startup.sh"
2. make sure 2 docker container is up and running 
3. call http://localhost:8888/swagger-ui/index.html to check health check of java container

### problems
- In case of facing issues of not created database in postgress image, you need to connect to postgress using commandline and try to create database with name "bank_system" manually 
