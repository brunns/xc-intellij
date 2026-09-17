# xc-intellij

IntelliJ plugin to allow running [xc](https://xcfile.dev/) tasks.

Plugin based on the [IntelliJ Platform Plugin Template][template].

## Tasks

### pc

Requires: test, lint, verify

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

### hello

Test task

```sh
echo "Hello from xc"
```