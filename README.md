e2e example      -----------------------------------------------------> Done
#mandatory
    # employee CRUD (DONE)
    # employee aggregation with department  (DONE)
                # use case with strategy pattern (Mahdy - Zidan)  
input validation
    # handle default annotation (min, notnull, email, size, etc) (DONE)
    # custom annotation (first name or last name not null) (Done)
    # handle input validation errors/exceptions (DONE)
    # include custom errors in JSON response (DONE)
                # complex custom validation (Zidan - mahrous)
                # query param validation  (Zidan - mahrous)
                # put error messages in localization properties file (Zidan - gemy)
error handling
    # general errors handled in a standard way (DONE)
    # include response code and custom exception messages (DONE)
    # remove custom exceptions that can be handled through exception handler(DONE)
                # handle 302 exception to return 401
                # put draft to status codes possible and review with abdo
                # discuss if putting logging, errorhandling, ... in seperate jars (Mahrous - Zidan)

sorting (Sorting Done)
                # Data filtering 
                                                            
automation tests
    unit tests // Use constructor injection instead of @Autowired & dont use mockBean so that we wont be dependent on context
                                # Junit 5 (Islam - Eid)
                                # BDD mockito (Islam - Eid)
    integration tests:(controller, client) (Mahdy - Zidan)

parallel execution (e.g. 1 API with multiple backend calls) (DONE)
security (OIDC)  (DONE)
    authentication 
    authorization (method, url)
    token validation
    
hypermedia (DONE) //put heatous in single responsibility class
logging: SLF4j + logger configuration (Islam - Noha/Mahdy)

tracing (sleuth) (Noha - Khaled)
local transactional (DONE)
    # support two method in a single transactional
    # tune read methods
lombok (DONE)
    #builder pattern
    #generate hashcode and equal and to-string
    #object comparison for entities
    #default constructor and all constructor
integration with Kafka (sent event to kafka when create new employee) (Mahdy/Gemy - Abdo)
CICD pipeline configuration
    #Jenkins file contains  all stags as defined in the CICD page https://confluence.sp.vodafone.com/display/VFD/CICD
    #follow gitflow workflow branching model to trigger the pipeline
spring cache abstraction layer (DONE)
    #cache collection
    #cache single item
    #update when changes
    #set timeout
keys storage (spring cloud k8s) (Zidan - Mahdy/Noha/Gemy)

configuration storage (spring cloud k8s) (Zidan - Mahdy/Noha/Gemy)
logging: (side cars) (Mahrous/Gemy - Khaled)
Spring web client vs. rest template (Islam - Gemy)
security tests (like object reference, TBD) 
#low priority
use our own custom sort for responses from rest APIs  (Islam - Gemy)
filtering(Elements, Attributes) 
data processing (on Kafka) low priority (Mahdy - Abdo)
reactor (Mahrous - Islam/Eid)
grpc (Mahdy - Abdo)
consumer cloud contract (Abdo)
circutbreaker (Mahrous - zidan)
generate OpenAPI specs (TBC with Abdo)
