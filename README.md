**Spring Batch from CSV to Postgres/Mysql DB**

- Master branch uses old impl with job & step.
    - You can either use an API or cron job to initiate. I used API.
    - use can either upload csv in API or put in classpath/resources folder. I used resources folder
    - Switched from JPA to JDBC



- impl2 branch uses new impl & Apache Commons CSV to read from CSV to a POJO.
    - use can either upload csv in API or put in classpath/resources folder. I used resources folder
    - spring batch tables are supposed to auto create on program run, but they didn't. Used SqlScriptRunner to manually create it on startup
    - reverted back to JPA


**Useful links**

- https://www.bezkoder.com/spring-boot-upload-csv-file/
- https://github.com/spring-projects/spring-batch/tree/main/spring-batch-core/src/main/resources/org/springframework/batch/core
- https://commons.apache.org/proper/commons-csv/user-guide.html#Handling_Byte_Order_Marks
- https://javadevcentral.com/read-csv-files-using-apache-commons-csv
- https://www.aurigait.com/blog/spring-batch/
- https://howtodoinjava.com/spring-batch/csv-to-database-java-config-example/
- https://javatechonline.com/spring-boot-batch-example-csv-to-mysql-using-jpa/
- https://www.onlinetutorialspoint.com/spring-boot/spring-boot-batch-example-csv-to-database.html
- https://www.toptal.com/spring/spring-batch-tutorial

