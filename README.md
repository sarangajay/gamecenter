# gamecenter

# H2 console 
http://localhost:8081/h2-console/

# Graphql console
http://localhost:8081/graphiql?path=/graphql

# Graphql schema for the solution is available @ 
resources/graphql/schema.graphqls

# Test input for the requests are available @ 
test-data.graphql

# Run the application with the command
`mvn spring-boot:run`

# Run test and generate the test report with the command
`mvn clean test jacoco:report`