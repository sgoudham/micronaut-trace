<a href="https://github.com/sgoudham/micronaut-trace/stargazers"><img src="https://img.shields.io/github/stars/sgoudham/micronaut-trace?colorA=363a4f&colorB=b7bdf8&style=for-the-badge"></a>
<a href="https://github.com/sgoudham/micronaut-trace/issues"><img src="https://img.shields.io/github/issues/sgoudham/micronaut-trace?colorA=363a4f&colorB=f5a97f&style=for-the-badge"></a>
<a href="https://github.com/sgoudham/micronaut-trace/contributors"><img src="https://img.shields.io/github/contributors/sgoudham/micronaut-trace?colorA=363a4f&colorB=a6da95&style=for-the-badge"></a>
<a href="https://search.maven.org/artifact/me.goudham/micronaut-trace"><img src="https://img.shields.io/maven-central/v/me.goudham/micronaut-trace?colorA=363a4f&colorB=a6da95&style=for-the-badge"></a>

# micronaut-trace

> Java annotation library to trace and time your method executions, written using [Micronaut](https://micronaut.io/).

## Installation

Be sure to replace the **VERSION** key below with the version shown above!

### Maven

```xml
<!-- https://mvnrepository.com/artifact/me.goudham/micronaut-trace -->

<dependency>
    <groupId>me.goudham</groupId>
    <artifactId>micronaut-trace</artifactId>
    <version>VERSION</version>
    <!-- If you want the .jar with all dependencies! WARNING: This might fix OR introduce various problems with Micronaut
    <classifier>shaded</classifier>
    -->
</dependency>
```

### Gradle

```gradle
repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/me.goudham/micronaut-trace
    implementation 'me.goudham:micronaut-trace:VERSION'
    
    // If you want the .jar with all dependencies! WARNING: This might fix OR introduce various problems with Micronaut
    implementation 'me.goudham:micronaut-trace:VERSION:shaded'
}
```

### Binaries

If you choose not to use a build tool, pre-built `.jar` files are available with every
single [release](https://github.com/sgoudham/micronaut-trace/releases).

## Usage

With the following example code:

`PrintService.java`

```java
package com.example;

import jakarta.inject.Singleton;
import me.goudham.micronaut.trace.annotation.Time;
import me.goudham.micronaut.trace.annotation.Trace;

import static java.lang.Thread.sleep;

@Singleton
public class PrintService {
    public PrintService() {
    }

    @Trace
    @Time
    public void printHello() throws InterruptedException {
        System.out.println("hello");
        printAfter5Seconds("hi");
    }

    @Trace
    @Time
    public void printAfter5Seconds(String message) throws InterruptedException {
        sleep(5000);
        System.out.println(message);
    }
}
```

`Application.java`

```java
package com.example;

import io.micronaut.context.ApplicationContext;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = ApplicationContext.run();
        PrintService printService = applicationContext.getBean(PrintService.class);
        printService.printHello();
    }
}
```

The output would look like:

```shell
08:09:04.577 [main] TRACE com.example.PrintService - [ENTERING]: printHello()
hello
08:09:04.577 [main] TRACE i.m.aop.chain.InterceptorChain - Intercepted method [void printHiAfter5Seconds(String message)] invocation on target: com.example.$PrintService$Definition$Intercepted@4ef74c30
08:09:04.577 [main] TRACE i.m.aop.chain.InterceptorChain - Proceeded to next interceptor [me.goudham.micronaut.trace.shared.TimeInterpreter@2438dcd] in chain for method invocation: void printHiAfter5Seconds(String message)
08:09:04.577 [main] TRACE i.m.aop.chain.InterceptorChain - Proceeded to next interceptor [me.goudham.micronaut.trace.shared.TraceInterpreter@41f69e84] in chain for method invocation: void printHiAfter5Seconds(String message)
08:09:04.581 [main] TRACE com.example.PrintService - [ENTERING]: printHiAfter5Seconds(java.lang.String message)
hi
08:09:09.582 [main] TRACE com.example.PrintService - [EXITING]: printHiAfter5Seconds(java.lang.String message)
08:09:09.582 [main] TRACE com.example.PrintService - [EXECUTION_TIME]: Elapsed execution time for printHiAfter5Seconds(java.lang.String message) is 5004 milliseconds
08:09:09.582 [main] TRACE com.example.PrintService - [EXITING]: printHello()
08:09:09.582 [main] TRACE com.example.PrintService - [EXECUTION_TIME]: Elapsed execution time for printHello() is 5005 milliseconds
```

## Contributing

Thank you for your interest in Contributing! See the following text for more information on how to build and test the
project.

### Development

This project was not intended to be feature-rich but rather a project to play about with the Micronaut framework and
learn more about annotation processing, defining custom annotations and deploying a Micronaut library.

It is highly recommended that you are familiar with the framework before trying to understand the code. You can find
the latest documentation [here](https://docs.micronaut.io/latest/guide/).

#### Tooling

This project uses the build tool [Maven](https://maven.apache.org/) from the java ecosystem. It is *highly* recommended
to develop using [Intellij IDEA](https://www.jetbrains.com/idea/) as it will allow you to take advantage of its
integration with maven tooling.

A great 5-minute introduction to Maven can be
found [here](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

#### Building

The project can be built with the following series of commands:

1. Clone Repository

    ```shell
    git clone https://github.com/sgoudham/micronaut-trace.git
    ```

2. [Enable Annotation Processing](https://docs.micronaut.io/latest/guide/#ideaSetup)

3. Build & Package

   ```shell
   ./mvnw clean package
   ```

4. Run Tests

   ```shell
   ./mvnw test
   ```

## License

[MIT](LICENSE)