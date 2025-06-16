# Fiwarecraft Project - AI Assistant Context

## Project Overview

Fiwarecraft is a Minecraft Spigot plugin that bridges Minecraft server data with FIWARE-based data integration platforms. This plugin enables data sharing between Minecraft gameplay and external systems through the MOC (makeOurCity) client library.

**Key Purpose**: Connect Minecraft server events and data to data integration infrastructure for smart city applications.

## Project Structure

```
fiwarecraft/
├── src/main/java/city/makeour/fiwarecraft/
│   ├── App.java                    # Main plugin class (JavaPlugin)
│   ├── client/
│   │   └── FcMocClient.java       # FIWARE MOC client wrapper
│   └── model/
│       ├── NgsiV2Entity.java      # Base NGSI-v2 entity model
│       └── Ping.java              # Ping status entity
├── src/test/java/                 # Unit tests
├── docs/                          # Documentation
├── .github/workflows/             # CI/CD workflows
└── pom.xml                       # Maven configuration
```

## Technology Stack

- **Language**: Java 17
- **Framework**: Spigot API 1.19.4
- **Build Tool**: Maven
- **Testing**: JUnit 4 + MockBukkit
- **Data Format**: JSON (Jackson)
- **External Library**: MOC Java client (moc-java 0.0.3)
- **CI/CD**: GitHub Actions

## Development Setup

### Prerequisites
- Java 17 or 21
- Maven 3.6+
- Spigot/Paper server for testing

### Common Commands
```bash
# Run tests
mvn test

# Run specific test class
mvn test -Dtest=NgsiV2EntityTest

# Clean and test
mvn clean test

# Build plugin JAR
mvn clean package

# Build without tests
mvn clean package -DskipTests
```

### Running Tests
The project uses JUnit 4 with MockBukkit for Minecraft plugin testing. All tests should be placed in `src/test/java/` following the same package structure as main code.

## Code Conventions

### Package Structure
- `city.makeour.fiwarecraft` - Root package
- `.model` - Data models and entities
- `.client` - External service clients
- Test classes should mirror main package structure

### Naming Conventions
- Classes: PascalCase
- Methods/Fields: camelCase
- Constants: UPPER_SNAKE_CASE
- Test classes: End with `Test`

### Code Style
- Use Jackson annotations for JSON serialization
- Follow builder pattern for fluent APIs
- Use `@Nonnull` annotations from Jakarta
- Keep methods focused and single-purpose
- No comments unless absolutely necessary

## Data Models

### NgsiV2Entity
Base class for NGSI-v2 compliant entities with:
- `type` field for entity type
- `id` field for unique identifier
- Jackson JSON serialization support
- Fluent builder methods

### Testing Patterns
- Unit tests for all model classes
- JSON serialization/deserialization testing
- MockBukkit for plugin integration testing
- Test naming: `test[MethodName][Scenario]`

## Dependencies

### Main Dependencies
- `spigot-api`: Minecraft server API
- `moc-java`: MOC client library
- `jackson-*`: JSON processing
- `jakarta.annotation-api`: Annotations

### Test Dependencies
- `junit`: Testing framework
- `MockBukkit`: Minecraft plugin testing

## CI/CD

GitHub Actions workflow runs on:
- Push to main, develop, feature/* branches
- Pull requests to main, develop
- Tests against Java 17 and 21
- Generates test reports and artifacts

## Important Notes

### Plugin Architecture
- Main class: `App` extends `JavaPlugin`
- Plugin lifecycle managed by Spigot
- MOC client integration for external data sharing
- Event-driven architecture for Minecraft events

### FIWARE Integration
- Uses NGSI-v2 data model specification
- Entities must have `type` and `id` fields
- JSON serialization follows FIWARE standards
- Data flows through MOC client to FIWARE platform

### Development Workflow
1. Create feature branch from develop
2. Write tests first (TDD approach)
3. Implement functionality
4. Run `mvn clean test` before committing
5. Create PR to develop branch
6. CI/CD will run automated tests

## Troubleshooting

### Common Issues
- **Tests fail**: Check Java version (requires 17+)
- **Plugin doesn't load**: Verify plugin.yml configuration
- **Dependencies missing**: Run `mvn clean install`
- **MOC client errors**: Check network connectivity and MOC service status

### Logging
Plugin uses Bukkit's built-in logger. Log levels:
- INFO: Normal operation messages
- WARNING: Non-critical issues
- SEVERE: Critical errors

## Future Development

When adding new features:
1. Follow existing package structure
2. Add comprehensive unit tests
3. Update this CLAUDE.md if architecture changes
4. Consider FIWARE/NGSI-v2 compliance for new entities
5. Test with actual Minecraft server environment