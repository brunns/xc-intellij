# xc-intellij

IntelliJ plugin to allow running [xc](https://xcfile.dev/) tasks.

Plugin based on the [IntelliJ Platform Plugin Template][https://github.com/JetBrains/intellij-platform-plugin-template].

## Tasks

### pc

Requires: test, lint, verify

RunDeps: async

### test

```sh
./gradlew test
```

### lint

```sh
./gradlew ktlintCheck
```

### verify

```sh
./gradlew verifyPlugin 
```

### format

```sh
./gradlew ktlintFormat
```

### clean

```sh
./gradlew clean
```

### hello

Test task

```sh
echo "Hello from xc"
```