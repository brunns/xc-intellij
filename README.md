# xc-intellij

IntelliJ plugin to allow running [xc](https://xcfile.dev/) tasks.

Plugin based on the [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template).

## Tasks

### pc

Requires: test, lint, verify

RunDeps: async

### test

Run tests

```sh
./gradlew test
```

### lint

Code linting

```sh
./gradlew ktlintCheck
```

### verify

Verify plugin

```sh
./gradlew verifyPlugin 
```

### format

Format code

```sh
./gradlew ktlintFormat
```

### clean

Clean

```sh
./gradlew clean
```

### hello

Test task

```sh
echo "Hello from xc"
```