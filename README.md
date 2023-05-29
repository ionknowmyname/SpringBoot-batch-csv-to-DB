**Spring Batch from CSV to Postgres/Mysql DB**

- Master branch uses old impl with job & step.
    - You can either use an API or cron job to initiate. I used API.
    - use can either upload csv in API or put in classpath/resources folder. I used resources folder
    - Switched from JPA to JDBC



- impl2 branch uses new impl & Apache Commons CSV to read from CSV to a POJO.
    - use can either upload csv in API or put in classpath/resources folder. I used resources folder
    - spring batch tables are supposed to auto create on program run, but they didn't. Used SqlScriptRunner to manually create it on startup
    - reverted back to JPA