# Spring Demo

## Introduction 
This is a demo project for Spring Boot 2. You can look at it as my notebook for Spring features and some development
practices. This README.md file contains some notes about the project and some development practices I try to follow.

# Database
# Testing
## Notes 
There are some rules I always try to follow when creating tests
1. Do NOT use production code for test initialisation. There are many reasons for not doing it, but my main reasons are: 
    1. May produce false positives since the production code may be buggy
    2. The tests can be harder to maintain since changing the signature of the production code may force you to change 
    the tests too
2. Keep as much as possible of the initialization of the test in the test method. Scrolling up and down to be able to 
understand the test can be tiresome
3. Try to keep mocking to a minimum
4. Put most of the effort in creating good integration tests
5. Mock dependencies instead of mocking the client calling the dependencies, e.g. create a mock server instead
of mocking the REST clients. This can make the job of creating and maintaining the tests easier
6. When using Spring, always try to initialize only the parts of the application used in the test. Springs test slices
can come in handy
## Initializing the database
Spring Boot automatically loads the DDL and data from schema.sql and data.sql.