# cmp_player

`cmp_player` is a Spring Boot WebFlux microservice that exposes player data
through a reactive REST endpoint. The service delegates data retrieval to an
upstream player API and returns the upstream response as a stream of
`PlayerDto` objects.

## Features

- Reactive HTTP API built with Spring WebFlux and Project Reactor.
- Configurable upstream player service URL.
- Configurable application port and public endpoint path.
- WebClient connection and response timeouts.
- Maven Wrapper support, so a separate Maven installation is not required.

## Requirements

- Java 21
- Internet access the first time the Maven Wrapper downloads Maven and project
  dependencies

## Running the service

The development profile is the recommended way to run the application because
it provides the local development port and upstream URL.

### Windows

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

### Linux or macOS

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The repository also includes `run.sh`, which starts the service with the
development profile on Linux or macOS:

```bash
./run.sh
```

When startup completes, the service listens on
`http://localhost:9090`.

## API

### Get all players

```http
GET /player
```

The endpoint returns a reactive stream serialized as a JSON array by Spring
WebFlux:

```json
[
  {
    "id": 10,
    "name": "Example Player",
    "jersey": "7",
    "country": "Mexico",
    "position": "Forward"
  }
]
```

Example request:

```bash
curl http://localhost:9090/player
```

The response fields are defined by `PlayerDto`:

| Field | Type | Description |
| --- | --- | --- |
| `id` | `Integer` | Player identifier |
| `name` | `String` | Player name |
| `jersey` | `String` | Jersey number |
| `country` | `String` | Player country |
| `position` | `String` | Player position |

The endpoint calls the upstream URL configured by `base.url.player`. The
upstream service must return JSON objects compatible with the response fields
above.

## Configuration

The default application configuration is in
`src/main/resources/application.properties`:

```properties
spring.application.name=cmp_player
```

The development profile is in
`src/main/resources/application-dev.properties`:

```properties
spring.application.name=cmp_player
server.port=9090
base.url.player=http://localhost:8081/player
player.url=player
```

| Property | Default in `dev` | Purpose |
| --- | --- | --- |
| `server.port` | `9090` | Port used by this service |
| `base.url.player` | `http://localhost:8081/player` | Upstream player API URL |
| `player.url` | `player` | Public controller path |

To override a property without editing the repository, pass it as a Spring
Boot command-line argument:

```powershell
.\mvnw.cmd spring-boot:run `
  -Dspring-boot.run.profiles=dev `
  "-Dspring-boot.run.arguments=--server.port=9091 --base.url.player=http://localhost:8081/player"
```

The WebClient is configured with a 28-second connection timeout and a
20-second response timeout.

## Building and testing

Run the test suite:

```powershell
.\mvnw.cmd test
```

Build the packaged application:

```powershell
.\mvnw.cmd clean package
```

Run the packaged JAR:

```powershell
java -jar target/cmp_player-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

## Project structure

```text
src/
├── main/
│   ├── java/com/cmp_player/
│   │   ├── CmpPlayerApplication.java   # Application entry point
│   │   ├── config/WebClientConfig.java # WebClient and timeout settings
│   │   ├── controller/PlayerController.java
│   │   ├── dto/PlayerDto.java           # Player response contract
│   │   └── service/PlayerService.java  # Upstream API integration
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
└── test/
    └── java/com/cmp_player/
        └── CmpPlayerApplicationTests.java
```

The request flow is:

1. `PlayerController` receives `GET /player`.
2. `PlayerService` performs a `GET` request to `base.url.player` using
   `WebClient`.
3. The upstream JSON response is decoded into `PlayerDto` objects.
4. The resulting `Flux<PlayerDto>` is returned to the client.

## Troubleshooting

- **Connection refused when requesting `/player`:** verify that the upstream
  service is running at `base.url.player` and that its endpoint returns
  compatible JSON.
- **The service starts on an unexpected port:** confirm that the `dev` profile
  is active or set `server.port` explicitly.
- **Maven or dependency download failures:** verify Java 21 is selected and
  that the machine can access Maven Central.

## Credits

**Dr. Alfonso José Barroso Barajas**

Technology Architect

## License

No license has been specified for this project.
