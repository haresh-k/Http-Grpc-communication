# Event-Logger
Event logger has two backend components
* Component 1(EventFetcher) - To fetch the event log data from user using http request
* Component 2(Audit) - To publish the event log data in a file 
Both the components communicate with each other using grpc. 

The same proto file is available within both the components. Ideally in Prod, we would maintain a separate repo for the proto file and create stubs as part of the gradle task(Code Summit doesn't give direct access to the repo, so maintaining same files at both components).

## How to run Audit?
* Go into Audit root directory, run the below
```bash
./gradlew clean build
```
```bash
./gradlew installDist
```
* Run the server using :
```bash
./build/install/Audit/bin/audit-service
```
## How to run EventFetcher?
* Go into EventFetcher root directory, run the below
```bash
./gradlew clean build
```
* Run the server using : 
```bash
java -jar .\build\libs\EventFetcher-1.0.jar
```

## Usage
* Use post request to create a file for the event object.
```JSON
{
 "timestamp": 1658642951,
 "userId": 102800,
 "event": "Registering"
}
```
* The file will be available in `Audit/storage/` directory.