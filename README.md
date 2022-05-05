![DEMO View](files/logo/white.png?raw=true "DEMO View")

## Overview

BRACU Space is a cloud storage system where users can upload, download, transfer, delete, and share files. The project
has been developed to achieve three goals. One is to implement the MVC (Model, View, Controller) design pattern, the
second one is to implement OOP (Object-Oriented Programming) concepts, and the last one is to enhance the frontend look
and performance. However, the main goal of this project was chosen to get hands-on experience with cloud services.

## Live

- Elastic Beanstalk - [Endpoint](http://bracuspace-env-1.eba-8ppnvg8d.ap-south-1.elasticbeanstalk.com/)
  | [Public IP](http://65.1.24.248/) (Latest)
- Heroku - [URL](https://bracuspace.herokuapp.com/) (Backup)

## Badges

[![Live](https://img.shields.io/badge/Live-Demo-red)](http://65.1.24.248/)
[![README](https://img.shields.io/badge/Help-Doc-lightgrey)](README.md)
[![GitHub Release](https://img.shields.io/github/v/release/tanviranindo/bracuspace)](https://github.com/tanviranindo/bracuspace/releases)
[![GitHub License](https://img.shields.io/github/license/tanviranindo/bracuspace.svg)](https://github.com/tanviranindo/bracuspace/blob/master/LICENSE.md)
[![Last Commit](https://img.shields.io/github/last-commit/tanviranindo/bracuspace/master)](https://github.com/tanviranindo/bracuspace/commits/master)

## Contents

- [Features](#Features)
- [Stacks](#Stacks)
- [Review](#Review)
- [Usage](#Usage)
- [Note](#Note)
- [Todo](#Todo)
- [Bugs](#bug-fixed)
- [Demo](#Demo)
- [Attachments](#Attachments)

## Features

- Users can log in, register and view account details
- Users can view, upload (local and remote), download, transfer, and delete files
- Admin can view, search, create, update, block, and delete users
- Admin can view, create, manage, and update roles

## Stacks

### Development

- **Backend** - Spring Boot, Spring Security, Spring Data JPA and Spring Mail
- **Frontend** - Thymeleaf
- **Database** - MySQL

### Deployment

- **Database** - Relational Database
  Service ([RDS](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_CreateDBInstance.html))
- **Storage** - DigitalOcean
  Space ([DO](https://www.digitalocean.com/community/tutorials/how-to-create-a-digitalocean-space-and-api-key)) / Simple
  Storage Service ([S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/create-bucket-overview.html))
- **Machine** - Elastic
  Beanstalk ([EB](https://aws.amazon.com/blogs/devops/deploying-a-spring-boot-application-on-aws-using-aws-elastic-beanstalk/))
  / Elastic Compute Cloud ([EC2](https://docs.aws.amazon.com/efs/latest/ug/gs-step-one-create-ec2-resources.html))

## Review

- For developing the application models, views and controllers are packaged in a distributed order for following
  the `MVC` design pattern. And, repositories, services and DTOs are also distributed.
- For deployment a standard MySQL database was created in `RDS`. And then a bucket in `Space` was created for storing
  the files. But currently each user will be provided a folder to store files only. Only that particular user can access
  that folder. Then, using `EC2` instance machine was created and configured. Lastly, deployed and hosted the
  application in the machine.

## Usage

BRACU Space is fully dependent on cloud services. So, there is no need to create or import any database. But the project
is developed on `JDK 11`. Clone this repository to get the project folder

```shell
git clone https://github.com/tanviranindo/bracuspace.git
```

To build the project

```shell
mvn clean install
```

To run the project in localhost

```shell
mvn spring-boot:run
```

Server port is configured as`server.port=5000`. View the live demo in `http://localhost:5000/`.

Login Handles -

```shell
ADMIN
email - admin@gmail.com
password - admin

USER
email - user1@gmail.com
password - user1
```

## Note

For testing the demo application, authentication keys are ~~provided~~. For deploying the application into production,
create a new `application-prod.properties` similar to `application-demo.properties` and configure the environments with
secret keys and switch the environment profile from `demo` to `prod`.

```properties
spring.profiles.active=prod
```

### Change Datasource

Create a new `MySQL` database on local or remote database. Or download the schema from [here](#Attachments).

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${USERNAME}
spring.datasource.password=${PASSWORD}
```

### Change Storage

The project is built on DigitalOcean Space, you need to create a bucket on
either [DigitalOcean Space](https://www.digitalocean.com/community/tutorials/how-to-create-a-digitalocean-space-and-api-key)
or [AWS S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/create-bucket-overview.html). DigitalOcean Space also
uses `AWS Java SDK S3`. However, after creating the bucket, credentials need to provided here.

```properties
storage.key=${STORAGE_KEY}
storage.secret=${SECRET_KEY}
storage.endpoint=${ENDPOINT}
storage.region=${REGION}
storage.bucket=${BUCKET_NAME}
```

But there is only one difference between Amazon S3 and DigitalOcean Space which is their authentication building method.
DigitalOcean can authentication with end point configuration whereas S3 calls static credential provider.

### DO Space

```java

@Configuration
public class StorageConfig() {
    private String accessKey;
    private String secretKey;
    private String endPoint;
    private String region;

    @Bean
    public AmazonS3 s3client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
```

### AWS S3

```java

@Configuration
public class StorageConfig() {
    private String accessKey;
    private String secretKey;
    private String endPoint;
    private String region;

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
```

### Change Email

To change the email, you need to create your own `SMTP` server or `GMAIL` can be used for sending emails. Change it
accordingly.

```properties
spring.mail.host=${HOST_NAME}
spring.mail.username=${MAIL_ADDRESS}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.port=${SMTP_PORT}
```

### Change Multipart

By default, properties have been configured with 1024MB. For reducing or extending the file upload limit, customization
can be done here.

```properties
spring.servlet.multipart.file-size-threshold=${SIZE}
spring.servlet.multipart.max-file-size=${SIZE}
spring.servlet.multipart.max-request-size=${SIZE}
```

Note: In local host the system can handle upload and download upto 10 GB or limit can be customized but in deployed site
it can handle upto 1MB only as for free tier.

## Todo

- [x] Users can log in and register
- [x] Admin can create users and roles
- [x] Admin can view, sort, search, edit and delete users
- [x] Pagination and cache is implemented for admin to view users
- [x] Email service for decent application behavior
- [x] Users can view, upload, download and delete files
- [x] User can upload from remote URL directly
- [x] Users can view profile
- [x] Role permissions for admin
- [x] File upload through remote URL directly
- [ ] Cloud to cloud storage transfer for files or buckets
- [ ] Progress bar for file upload
- [ ] Users can add profile pictures
- [ ] User profile edit feature needs to be added
- [ ] Users can sort and search files
- [ ] File pagination and cache need to be added
- [ ] Users can share files publicly or with other users
- [ ] Folder structure needs to be implemented
- [ ] Users account verification by email authentication
- [ ] Recover and change password

## Bug Fixed

- [x] Sorting in admin view for the users

## Demo

![DEMO View](files/demo/demo.gif?raw=true "DEMO View")

## Attachments

- #### [SQL](files/sql)
- #### [Diagrams](files/diagrams)
- #### [Demo](files/demo)
