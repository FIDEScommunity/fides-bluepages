# Getting started
This guide will describe how to locally run a version of the FIDES Bluepages for development purposes.

## Prerequisites
The following tools should be installed:
- [SDKMan](https://sdkman.io/)
- [Node version manager](https://github.com/nvm-sh/nvm)
- Docker

## Introduction
The FIDES Bluepages consists of two components, the frontend and backend. The frontend is a React-based web application that runs using nodejs. The backend is a Java-based Spring Boot application.

## Bluepages Backend
The backend can be started either in an IDE, such as IntelliJ, or via the command-line. For the latter, first go to the backend folder:

```
cd fides-bluepages-backend
```

Ensure the correct JDK is used:

```
sdk env install
```
Start the FIDES Bluepages Backend application:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Bluepages Frontend
The frontend application can be started either in an IDE, such as IntelliJ, or via the command-line. For the latter, first go to the frontend folder:

```
cd fides-bluepages-frontend
```

Ensure the correct node version is used:

```
nvm use
```

Make sure all libraries are installed:

```
npm install
```

Start the FIDES Bluepages Frontend application:

```
npm start
```

Finally open the following URL in your browser:

[FIDES Bluepages](http://localhost:3000)
