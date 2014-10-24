chrono.java
===========

A natural language date parser (Java port of chrono.js) 


### Installation

The easiest way to install Chrono is using [Maven repository](http://stackoverflow.com/questions/20161602/loading-maven-dependencies-from-github) hosted on Github. If you already have Maven on your project, simply add the following `repository` and `dependency` into your `pom.xml`.

```xml

<repositories>
  ...
  <repository>
    <id>Chrono date parser library</id>
      <url>https://raw.github.com/wanasit/chrono-java/mvn-repo/</url>
      <snapshots>
           <enabled>true</enabled>
           <updatePolicy>always</updatePolicy>
      </snapshots>
    </repository>
</repositories>

```

```xml

<dependencies>
  ...
  <dependency>
       <groupId>com.wanasit</groupId>
       <artifactId>chrono</artifactId>
       <version>LATEST</version>
  </dependency>
</dependencies>
```
