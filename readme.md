
# ClusteredData Warehouse

Data warehouse for Bloomberg to analyze FX deals.

## Technologies used

Language: Java

Java Version: 11

Database: MySQL

Framework: Spring Boot, Spring Data JPA, Hibernate

Testing Framework: Mockito, Junit, RestAssured

Application for testing APIs: Postman

## Setup project locally

Firstly, clone the project to your machine

```bash
  git clone https://github.com/Nitishrestha/bloomberg.git
```
Then create a database named FXdeals in your MySQL database

```bash
  CREATE DATABASE FXdeals;
```

Then go to the project directory

```bash
  cd Bloomberg
```
Then install maven dependencies used in project

```bash
  mvn clean install
```
After successful build, a target folder will be created. To navigate to target folder:

```bash
  cd target
```
### Steps to start docker:
Step 1: build docker image using following command:
```bash
 docker build -t bloomberg:latest .
```
Step 2: Start/Run docker compose command to initiate the project:
```bash
 docker-compose up
```

Step 3: Start verifying with the endpoints

## API Reference

### 1. After opening the postman, upload CSV file

```http
  POST /api/v1/forexDeal
```
### In the Headers section of postman:

| Key | Value     | Required                |
| :-------- | :------- | :------------------------- |
| `Content-Type` | `application/csv` |    Yes |
| `Content-disposition` | `attachment; filename=[yourfilename.csv]` |  Yes|

### In the Body section, select the form-data

| Key | Value     | Required                |
| :-------- | :------- | :------------------------- |
| `file` | `select files` |    Yes |

### Clicking on the select files, a window will open where you need to select the csv file for uploading

#### Response after successfully uploading csv
  ```json
   {
    "message": "SUCCESSFULLY UPLOADED THE FILE: file.csv"
    }
 ```
### For the testing purpose, there are 4 files added to the resources folder.
1. file.csv (valid file)
2. Invalid.csv (csv file but with different header)
3. 4mb.txt (file of size 4 mb exceeding the max size limit (2 mb))
4. Sample.txt(irrelevant file because we only need csv files to get uploaded)


 