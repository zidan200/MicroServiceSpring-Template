# e2e example
# Description
Creating a Microservice template for Handling Employee services
   - Employee CRUD
   - Employee aggregation with department [ use case with strategy pattern ]
                
# Security (OIDC)
 - Authentication 
 - Authorization (method, url)
 - Token validation                
## Input validation
 - Handle default annotation (min, notnull, email, size, etc)
 - Custom annotation (first name or last name not null)
 - Handle input validation errors/exceptions
 - Include custom errors in JSON response
    - Complex custom validation
    - Query param validation
    - Put error messages in localization properties file
## Error handling
 - General errors handled in a standard way
 - Include response code and custom exception messages
 - Remove custom exceptions that can be handled through exception handler
    - 1\. Handle 302 exception to return 401
    - 2\. Put draft to status codes possible
    - 3\. Put logging, errorhandling, ... in seperate jars 

## Sorting 
 - Data filtering 
                                                            
## Automation tests
 - unit tests [ Use constructor injection instead of @Autowired & dont use mockBean so that we wont be dependent on context ]
    - Junit 5
    - BDD mockito
    - Integration tests:(controller, client)

## Parallel execution (e.g. 1 API with multiple backend calls)
    
## [Hypermedia](https://www.baeldung.com/spring-hateoas-tutorial)
 - Task
    - Put heatous in single responsibility class
## Logging: SLF4j + logger configuration

## Tracing (sleuth)
## Local transactional
 - Support two method in a single transactional
 - Tune read methods
## Lombok 
 - Builder pattern
 - Generate hashcode and equal and to-string
 - Object comparison for entities
 - Default constructor and all constructor
## Integration with Kafka (sent event to kafka when create new employee)
## [CICD pipeline configuration](https://dzone.com/articles/learn-how-to-setup-a-cicd-pipeline-from-scratch)
 - Jenkins file contains  all stags as defined in the CICD page 
 - Follow gitflow workflow branching model to trigger the pipeline
## Spring cache abstraction layer
 - Cache collection
 - Cache single item
 - Update when changes
 - Set timeout
## Keys storage (spring cloud k8s)
 - Configuration storage (spring cloud k8s)
## Logging: (side cars) 

## Low priority
 - data processing (on Kafka) low priority 
 - reactor 
 - grpc
 - consumer cloud contract
 - circutbreaker
 - generate OpenAPI specs
##
    - Spring web client vs. rest template
    - security tests (like object reference) 
