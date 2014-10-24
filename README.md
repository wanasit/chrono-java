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

### Feature

```Java
Date result = Chrono.ParseDate("July 15, 2014 6:41 p.m. ET");
// result == Tue Jul 15 18:41:00 CDT 2014

List<ParsedResult> results = Chrono.Parse("July 15, 2014 6:41 p.m. ET");
// results.get(0).text == "July 15, 2014 6:41 p.m. ET"
// results.get(0).start.date() == Tue Jul 15 18:41:00 CDT 2014
// results.get(0).end == null

List<ParsedResult> results = Chrono.Parse("at ... event on November 11-13. Read more: http://..");
// results.get(0).index == 16
// results.get(0).text == "November 11-13"
// results.get(0).start.date() == Tue Nov 11 12:00:00 CST 2014
// results.get(0).end.date() == Thu Nov 13 12:00:00 CST 2014

```

For basic parsing, simply pass a natural language String to function `Chrono.Parse` or `Chrono.ParseDate`.  
 
* To get a quick date result, `Chrono.ParseDate` returns `java.util.Date`.
* To get every detailed information of date expession appeared in the text, `Chrono.Parse` returns `List<ParsedResult>`. 
