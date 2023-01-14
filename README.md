<a href="https://github.com/sgoudham/trace/stargazers"><img src="https://img.shields.io/github/stars/sgoudham/trace?colorA=363a4f&colorB=b7bdf8&style=for-the-badge"></a>
<a href="https://github.com/sgoudham/trace/issues"><img src="https://img.shields.io/github/issues/sgoudham/trace?colorA=363a4f&colorB=f5a97f&style=for-the-badge"></a>
<a href="https://github.com/sgoudham/trace/contributors"><img src="https://img.shields.io/github/contributors/sgoudham/trace?colorA=363a4f&colorB=a6da95&style=for-the-badge"></a>
<a href="https://search.maven.org/artifact/me.goudham/trace"><img src="https://img.shields.io/maven-central/v/me.goudham/trace?colorA=363a4f&colorB=a6da95&style=for-the-badge"></a>

# trace

> A Java annotation library to trace and time your method executions, written using [Micronaut](https://micronaut.io/).

## Installation

Be sure to replace the **VERSION** key below with the version shown above!

### Maven

```xml
<!-- https://mvnrepository.com/artifact/me.goudham/trace -->

<dependency>
  <groupId>me.goudham</groupId>
  <artifactId>trace</artifactId>
  <version>VERSION</version>
</dependency>
```

### Gradle

```gradle
repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/me.goudham/trace
    implementation group: 'me.goudham', name: 'trace', version: 'VERSION'
}
```

### Binaries

If you choose not to use a build tool, pre-built `.jar` files are available with every
single [release](https://github.com/sgoudham/trace/releases).

## Usage

TODO

## Contributing

TODO

### Development

This project uses the build tool [Maven](https://maven.apache.org/) from the java ecosystem. It is *highly* recommended
to develop using [Intellij IDEA](https://www.jetbrains.com/idea/) as it will allow you to take advantage of its
integration with maven tooling.

The project can be built using the command:

```shell
./mvnw clean package
```

and running tests is as simple as:

```shell
./mvnw test
```

A great 5-minute introduction to Maven can be
found [here](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

### CI / CD

This project has a GitHub actions workflow to automatically build binaries and deploy to maven central. The workflows
are stored at [.github/workflows](.github/workflows)