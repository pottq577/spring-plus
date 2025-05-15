# SPRING PLUS

`prefix(domain): [level-step] subject` 형식으로 커밋이 기록되며, 브랜치 별로 각 단계가 분리되어 있습니다.

각 단계가 완성되면 `Lv0 (부분)완성` 의 형식으로 PR 후 merge & commit 합니다.

과제 진행사항은 커밋 히스토리에서 확인할 수 있습니다.

# ⚠️ 빌드 전 확인사항

현재 `application.properties` 는 `.gitignore` 에 추가되어 있습니다.

빌드 전 `./src/main/` 패키지 하위에 `resources` 패키지를 만들고 `application.properties` 파일을 생성해야 하며,<br/>
아래의 설정값을 본인 환경에 맞도록 수정해야 합니다.
- `DataSource` 의 `spring.datasource.username` , `spring.datasource.password` , `spring.datasource.url`
- `JWT` 의 `jwt.secret.key`

```properties
### ./src/main/resources/application.properties ###

spring.application.name=expert

### DataSource ###
spring.datasource.url={DB_URL}
spring.datasource.username={USERNAME}
spring.datasource.password={PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### Hibernate ###
spring.jpa.hibernate.ddl-auto=create
# show SQL query
spring.jpa.properties.hibernate.show_sql=true
# prettier sql query
spring.jpa.properties.hibernate.format_sql=true
# add /* */ comments
spring.jpa.properties.hibernate.use_sql_comments=false
# add highlight in SQL keyword
spring.jpa.properties.hibernate.highlight_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

### JWT ###
jwt.secret.key={JWT_SECRET_KEY}

### Logging ###
logging.file.name=logs/test.log
logging.logback.rollingpolicy.max-file-size=100MB
logging.logback.rollingpolicy.file-name-pattern=${LOG_FILE}.%d{yyyy-MM-dd}.%i.log
```
