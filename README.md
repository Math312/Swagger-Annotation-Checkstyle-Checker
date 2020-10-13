# README

本项目是基于maven checkstyle的自定义规则，用于动态检测Java代码中Swagger注解是否书写完全。现支持如下功能：

1. Controller 不添加@Api注解，或者@Api注解中value值为null或者空字符串
2. @RequestMapping、@GetMapping、@PostMapping、@PutMapping、@DeleteMapping修饰的方法出现如下情况：
    1. 不添加@ApiOperation注释，或者属性内容为null或者空字符串
    2. 不添加@ApiImplicitParams注释

使用方法：

1. 将当前项目进行打包安装：
    
    ```shell script
    mvn clean install
    ```

2. 在业务系统中配置如下文件：

checkstyle.xml

```xml
<module name="Checker">
    <module name="TreeWalker">
        <module name="com.sharedaka.checkstyle.check.SwaggerMethodAnnotationCheck"/>
        <module name="com.sharedaka.checkstyle.check.SwaggerClassAnnotationCheck"/>
    </module>
</module>
```

pom.xml
```xml
           <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.1.1</version>
                <configuration>
                    <!-- checkstyle的配置文件路径，如果不在根路径则在前面加上具体的目录即可 -->
                    <configLocation>checkstyle.xml</configLocation>
                </configuration>
                <!-- 该配置非常重要，如果没有executions标签，则在该工程执行install时不会执行自定义的校验 -->
                <executions>
                    <execution>
                        <!-- 该插件执行的生命周期点，在initialize时进行执行 -->
                        <phase>initialize</phase>
                        <!-- 执行目标，要想具体了解目标的概念需要了解maven的插件生命周期，这里不再展开，下方会有推荐阅读的链接 -->
                        <goals>
                            <goal>checkstyle</goal><!-- checkstyle只会统计数量。check会在控制台输出具体结果 -->
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <!-- 依赖的已安装的自定义插件 -->
                    <dependency>
                        <groupId>com.sharedaka</groupId>
                        <artifactId>sharedaka-code-check-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
```

```xml
    <reporting>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.1.1</version>
                <reportSets>
                    <reportSet>
                        <reports>
                            <report>checkstyle</report>
                        </reports>
                    </reportSet>
                </reportSets>
            </plugin>
        </plugins>
    </reporting>
```