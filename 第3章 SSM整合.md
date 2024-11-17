# 整合持久层框架MyBatis
## 准备数据库表及数据
创建数据库：springboot

![image-20241117150929846](/home/lb/IdeaProjects/SpringBoot3/第3章 SSM整合.assets/image-20241117150929846.png)

## 创建SpringBoot项目
使用脚手架创建Spring Boot项目,引入mysql驱动以及mybatis的启动器		

依赖如下：

```xml
<!--mybatis的启动器-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
<!--mysql的驱动依赖-->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**<font style="color:#DF2A3F;">注意，之前也提到过：</font>**

+ **<font style="color:#DF2A3F;">Spring Boot官方提供的启动器的名字规则：spring-boot-starter-xxx</font>**
+ **<font style="color:#DF2A3F;">第三方（非Spring Boot官方）提供的启动器的名字规则：xxx-spring-boot-starter</font>**

## 编写数据源配置
前面提到过，Spring Boot配置统一可以编写到application.properties中，配置如下：

```properties
# Spring Boot脚手架自动生成的
spring.application.name=sb3-05-springboot-mybatis

# mybatis连接数据库的数据源
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=root
spring.datasource.password=123456
```

以上的配置属于连接池的配置，连接池使用的是Spring Boot默认的连接池：HikariCP

## 编写实体类Vip
表`t_vip`中的字段分别是：

+ id
+ name
+ card_number
+ birth

对应实体类`Vip`中的属性名分别是：

+ Long id;
+ String name;
+ String cardNumber;
+ String birth;

创建包`model`，在该包下新建Vip类，代码如下：

```java
public class Vip {
    private Long id;
    private String name;
    private String cardNumber;
    private String birth;

    public Vip() {
    }

    public Vip(Long id, String name, String cardNumber, String birth) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.birth = birth;
    }

    public Vip(String name, String cardNumber, String birth) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.birth = birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Vip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
```

## 编写Mapper接口
创建`repository`包，在该包下新建`VipMapper`接口，代码如下：

```java
import com.powernode.sb305springbootmybatis.model.Vip;

import java.util.List;

    public interface VipMapper {
        /**
         * 插入会员信息
         * @param vip
         * @return 1表示插入成功，其他值表示失败
         */
        int insert(Vip vip);

        /**
         * 根据id删除会员信息
         * @param id 会员唯一标识
         * @return 1表示删除成功，其他值表示失败
         */
        int deleteById(Long id);

        /**
         * 更新会员信息（id不可更新）
         * @param vip 会员信息
         * @return 1表示更新成功，其他值表示更新失败。
         */
        int update(Vip vip);

        /**
         * 根据id查询会员信息
         * @param id 会员的唯一标识
         * @return 会员信息
         */
        Vip selectById(Long id);

        /**
         * 获取所有会员信息
         * @return
         */
        List<Vip> selectAll();
    }
```

## 编写Mapper接口的XML配置文件
在`resources`目录下新建`mapper`目录，将来的`mapper.xml`配置文件放在这个目录下。

安装`MyBatisX`插件，该插件可以根据我们编写的`VipMapper`接口自动生成mapper的XML配置文件。	

然后在`VipMapper`接口上：alt+enter

生成`mapper of xml`：需要选择一个生成的位置

接下来，你会看到Mapper接口中方法报错了，可以在错误的位置上使用`alt+enter`，选择`Generate statement`.

这个时候在mapper的xml配置文件中便生成了对应的配置，如下：

接下来就是编写SQL语句了，最终`VipMapper.xml`文件的配置如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.lb.mybatis.repository.VipMapper">


    <insert id="insert">
        insert into t_vip(id, name, card_number, birth)
        values (null, #{name}, #{cardNumber}, #{birth})
    </insert>
    <update id="update"> update t_vip
                         set name=#{name},
                             card_number=#{cardNumber},
                             birth=#{birth}
                         where id = #{id}</update>
    <delete id="deleteById"> delete
                             from t_vip
                             where id = #{id} </delete>
    <select id="selectById" resultType="com.lb.mybatis.model.Vip">select *
                                                                  from t_vip
                                                                  where id = #{id}</select>
    <select id="selectAll" resultType="com.lb.mybatis.model.Vip">select *
                                                                 from t_vip</select>
</mapper>
```

## 添加Mapper的扫描
在Spring Boot的入口程序上添加如下的注解，来完成`VipMapper`接口的扫描：

```java
@MapperScan(basePackages = "com.lb.mybatis.repository"	)
@SpringBootApplication
public class Sb306MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sb306MybatisApplication.class, args);
	}

}		
```

## 告诉MyBatis框架MapperXML文件的位置
在`application.properties`配置文件中进行如下配置：

```properties
mybatis.mapper-locations=classpath:mapper/*.xml
```

## 测试整合MyBatis是否成功
在Spring Boot主入口程序中获取Spring上下文对象`ApplicationContext`，从Spring容器中获取`VipMapper`对象，然后调用相关方法进行测试：

```java
package com.lb.mybatis;

import com.lb.mybatis.model.Vip;
import com.lb.mybatis.repository.VipMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.lb.mybatis.repository"	)
@SpringBootApplication
public class Sb306MybatisApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Sb306MybatisApplication.class, args);
		VipMapper vipMapper = (VipMapper) configurableApplicationContext.getBean("vipMapper");
		Vip vip = vipMapper.selectById(1L);
		System.out.println(vip);
		configurableApplicationContext.close();

	}

}
```

​				测试结果中可以看到`cardNumber`属性没有赋值成功，原因是：表中的字段名叫做`card_number`，和实体类`Vip`的属性名`cardNumber`对应不上。解决办法两个：

+ **第一种方式：查询语句使用as关键字起别名，让查询结果列名和实体类的属性名对应上。**

![](https://cdn.nlark.com/yuque/0/2024/png/21376908/1729135489213-4e91d0d1-17f7-48fd-ad1c-568443cd78e0.png)

+ **第二种方式：通过配置自动映射**

在`application.properties`配置文件中进行如下配置：

```properties
mybatis.configuration.map-underscore-to-camel-case=true
```

map-underscore-to-camel-case 是一个配置项，主要用于处理数据库字段名与Java对象属性名之间的命名差异。在许多数据库中，字段名通常使用下划线（_）分隔单词，例如 first_name 或 last_name。而在Java代码中，变量名通常使用驼峰式命名法（camel case），如 firstName 和 lastName。

当使用MyBatis作为ORM框架时，默认情况下它会将SQL查询结果映射到Java对象的属性上。如果数据库中的字段名与Java对象的属性名不一致，那么就需要手动为每个字段指定相应的属性名，或者使用某种方式来自动转换这些名称。

map-underscore-to-camel-case 这个配置项的作用就是在查询结果映射到Java对象时，自动将下划线分隔的字段名转换成驼峰式命名法。这样可以减少手动映射的工作量，并提高代码的可读性和可维护性。

## 测试其他方法是否正常
测试程序如下：

```java
package com.lb.mybatis;

import com.lb.mybatis.model.Vip;
import com.lb.mybatis.repository.VipMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@MapperScan(basePackages = "com.lb.mybatis.repository")
@SpringBootApplication
public class Sb306MybatisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Sb306MybatisApplication.class, args);
        VipMapper vipMapper = (VipMapper) configurableApplicationContext.getBean("vipMapper");
        Vip vip = vipMapper.selectById(1L);
        System.out.println(vip);

        // 添加会员信息
        Vip newVip = new Vip("杰克", "1234567892", "1999-11-10");
        vipMapper.insert(newVip);
        // 查询所有会员信息
        List<Vip> vips = vipMapper.selectAll();
        System.out.println(vips);
        // 修改会员信息
        vip.setName("zhangsan");
        vipMapper.update(vip);
        // 查询所有会员信息
        List<Vip> vips2 = vipMapper.selectAll();
        System.out.println(vips2);
        // 删除会员信息
        vipMapper.deleteById(1L);
        // 查询所有会员信息
        List<Vip> vips3 = vipMapper.selectAll();
        System.out.println(vips3);
        configurableApplicationContext.close();
    }

}
```

# Lombok库
Lombok 是一个 Java 库，它可以通过注解的方式减少 Java 代码中的样板代码。Lombok 自动为你生成构造函数、getter、setter、equals、hashCode、toString 方法等，从而避免了手动编写这些重复性的代码。这不仅减少了出错的机会，还让代码看起来更加简洁。

**<font style="color:#DF2A3F;">Lombok只是一个编译阶段的库，能够帮我们自动补充代码，在Java程序运行阶段并不起作用。（因此Lombok库并不会影响Java程序的执行效率）</font>**

例如我们有这样一个java源文件`User.java`，代码如下：

```java
@Data
public class User{
    private String name;
}
```

以上代码在程序的编译阶段，Lombok库会将`User.java`文件编译生成这样的`User.class`字节码文件：

```java
public class com.lb.lomboktest.model.User {
  public com.powernode.lomboktest.model.User();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public java.lang.String getName();
    Code:
       0: aload_0
       1: getfield      #7                  // Field name:Ljava/lang/String;
       4: areturn

  public void setName(java.lang.String);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #7                  // Field name:Ljava/lang/String;
       5: return

  public boolean equals(java.lang.Object);
    Code:
       0: aload_1
       1: aload_0
       2: if_acmpne     7
       5: iconst_1
       6: ireturn
       7: aload_1
       8: instanceof    #8                  // class com/powernode/lomboktest/model/User
      11: ifne          16
      14: iconst_0
      15: ireturn
      16: aload_1
      17: checkcast     #8                  // class com/powernode/lomboktest/model/User
      20: astore_2
      21: aload_2
      22: aload_0
      23: invokevirtual #13                 // Method canEqual:(Ljava/lang/Object;)Z
      26: ifne          31
      29: iconst_0
      30: ireturn
      31: aload_0
      32: invokevirtual #17                 // Method getName:()Ljava/lang/String;
      35: astore_3
      36: aload_2
      37: invokevirtual #17                 // Method getName:()Ljava/lang/String;
      40: astore        4
      42: aload_3
      43: ifnonnull     54
      46: aload         4
      48: ifnull        65
      51: goto          63
      54: aload_3
      55: aload         4
      57: invokevirtual #21                 // Method java/lang/Object.equals:(Ljava/lang/Object;)Z
      60: ifne          65
      63: iconst_0
      64: ireturn
      65: iconst_1
      66: ireturn

  protected boolean canEqual(java.lang.Object);
    Code:
       0: aload_1
       1: instanceof    #8                  // class com/powernode/lomboktest/model/User
       4: ireturn

  public int hashCode();
    Code:
       0: bipush        59
       2: istore_1
       3: iconst_1
       4: istore_2
       5: aload_0
       6: invokevirtual #17                 // Method getName:()Ljava/lang/String;
       9: astore_3
      10: iload_2
      11: bipush        59
      13: imul
      14: aload_3
      15: ifnonnull     23
      18: bipush        43
      20: goto          27
      23: aload_3
      24: invokevirtual #24                 // Method java/lang/Object.hashCode:()I
      27: iadd
      28: istore_2
      29: iload_2
      30: ireturn

  public java.lang.String toString();
    Code:
       0: aload_0
       1: invokevirtual #17                 // Method getName:()Ljava/lang/String;
       4: invokedynamic #28,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;
       9: areturn
}
```

通过字节码可以看到Lombok库的`@Data`注解可以帮助我们生成`无参构造器`、`setter`、`getter`、`toString`、`hashCode`、`equals`。

## Lombok 的主要注解
**@Data**：

+ 等价于 `@ToString`, `@EqualsAndHashCode`, `@Getter`，`@Setter`, `@RequiredArgsConstructor`.
+ 用于生成：必要参数的构造方法、getter、setter、toString、equals 和 hashcode 方法。

**@Getter** / **@Setter**：

+ 分别用于生成所有的 getter 和 setter 方法。
+ 可以作用于整个类，也可以作用于特定的字段。

**@NoArgsConstructor**：

+ 生成一个无参构造方法。

**@AllArgsConstructor**：

+ 生成一个包含所有实例变量的构造器。

**@RequiredArgsConstructor**：

+ 生成包含所有被 `final` 修饰符修饰的实例变量的构造方法。
+ **<font style="color:#DF2A3F;">如果没有</font>**`**<font style="color:#DF2A3F;">final</font>**`**<font style="color:#DF2A3F;">的实例变量，则自动生成无参数构造方法。</font>**

**@ToString** / **@EqualsAndHashCode**：

+ 用于生成 toString 和 equals/hashCode 方法。
+ **<font style="color:#DF2A3F;">这两个注解都有</font>**`**<font style="color:#DF2A3F;">exclude</font>**`**<font style="color:#DF2A3F;background-color:#ffffff;">属性，通过这个属性可以定制toString、hashCode、equals方法。</font>**

**<font style="color:#DF2A3F;background-color:#ffffff;"></font>**

## 如何使用 Lombok？
创建一个普通的Maven模块来快速测试一下Lombok库的使用：

### 添加依赖
在 Maven 的 `pom.xml` 文件中添加 Lombok 依赖：

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.34</version>
    <scope>provided</scope>
</dependency>
```

### IDEA中安装Lombok插件
高版本的IntelliJ IDEA工具默认都是绑定Lombok插件的，不需要再额外安装：

**<font style="color:#DF2A3F;">Lombok插件不是必须要安装的</font>**，为了提高开发效率以及开发者的体验，安装Lombok插件是有必要的。

也就是说安装了Lombok插件之后，编写代码的时候，才会有方法的提示功能。

当IDEA中没有安装lombok插件时：程序会有很好的提示功能。

### 使用 Lombok 注解
在 Java 类中使用 Lombok 提供的注解。

```java
import lombok.Data;

@Data
public class User {
    private String name;
}
```

编写测试程序：

```java
public class Test {
    public static void main(String[] args) {
        User user = new User();
        user.setName("jackson");
        System.out.println(user.getName());
        System.out.println(user.toString());
        System.out.println(user.hashCode());
        User user2 = new User();
        user2.setName("jackson");
        System.out.println(user.equals(user2));
    }
}

```

以下的注解可以自行测试：

+ @Getter
+ @Setter
+ @ToString【exclude属性】
+ @EqualsAndHashCode【exclude属性】
+ @NoArgsConstructor
+ @AllArgsConstructor
+ @RequiredArgsConstructor

**<font style="color:#DF2A3F;">注：Lombok只能帮助我们生成无参数构造方法和全参数构造方法，其他定制参数的构造方法无法生成。</font>**

## Lombok的其他常用注解
@Value

@Builder

@Singular

@Slf4j

......

### @Value
该注解会给所有属性添加`final`，给所有属性提供`getter`方法，自动生成`toString`、`hashCode`、`equals`

**通过这个注解可以创建不可变对象。**

```java
@Value
public class Customer {
    Long id;
    String name;
    String password;
}
```

测试程序：

```java
public class CustomerTest {
    public static void main(String[] args) {
        Customer c1 = new Customer(1L, "jackson", "123");
        System.out.println(c1);
        System.out.println(c1.getId());
        System.out.println(c1.getName());
        System.out.println(c1.getPassword());
        System.out.println(c1.hashCode());
        Customer c2 = new Customer(1L, "jackson", "123");
        System.out.println(c1.equals(c2));
    }
}

```

可以查看一下字节码，你会发现，@Value注解的作用只会生成：全参数构造方法、getter方法、hashCode、equals、toString方法。（没有setter方法。）

### @Builder
#### GoF23种设计模式之一：建造模式
建造模式（Builder Pattern）属于创建型设计模式。GoF23种设计模式之一。

用于解决对象创建时参数过多的问题。它通过将对象的构造过程与其表示分离，使得构造过程可以逐步完成，而不是一次性提供所有参数。建造模式的主要目的是让对象的创建过程更加清晰、灵活和可控。

简而言之，建造模式用于：

1. **简化构造过程**：通过逐步构造对象，避免构造函数参数过多。
2. **提高可读性和可维护性**：让构造过程更加清晰和有序。
3. **增强灵活性**：允许按需配置对象的不同部分。

这样可以更方便地创建复杂对象，并且使得代码更加易于理解和维护。

#### 建造模式的代码
建造模式代码如下：

```java
// 建造模式
public class Person {
    // 属性
    private final String name;
    private final int age;
    private final String email;

    // 私有的全参数构造方法
    private Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    // 静态内部类
    public static class PersonBuilder {
        private String name;
        private int age;
        private String email;

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        // 建造对象的核心方法
        public Person build() {
            return new Person(name, age, email);
        }
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + ", email='" + email + '\'' + '}';
    }

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("jackson")
                .age(20)
                .email("jackson@123.com")
                .build();
        System.out.println(person);
    }
}

```

#### 使用@Builder注解自动生成建造模式的代码
该注解可以直接帮助我们生成以上的代码。使用`@Builder`注解改造以上代码。

```java
@Data
@Builder
// 建造模式
public class Person {
    // 属性
    private String name;
    private int age;
    private String email;

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("jackson")
                .age(20)
                .email("jackson@123.com")
                .build();
        System.out.println(person);
    }
}

```

### @Singular
@Singular注解是辅助@Builder注解的。

当被建造的对象的属性是一个集合，这个集合属性使用@Singular注解进行标注的话，可以连续调用集合属性对应的方法完成多个元素的添加。如果没有这个注解，则无法连续调用方法完成多个元素的添加。代码如下：

```java
@Data
@Builder
// 建造模式
public class Person {
    // 属性
    private final String name;
    private final int age;
    private final String email;
    // Singular翻译为：单数。表示一条一条添加
    @Singular("addPhone")
    private final List<String> phones;

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("jackson")
                .age(20)
                .email("jackson@123.com")
                .addPhone("15222020214")
                .addPhone("14875421424")
                .addPhone("16855241424")
                .build();
        System.out.println(person);
    }
}

```

### @Slf4j
Lombok 支持多种日志框架的注解，可以根据你使用的日志框架选择合适的注解。以下是 Lombok 提供的**<font style="color:#DF2A3F;">部分日志注解</font>**及其对应的日志框架：

1. `@Log4j`：
    - 自动生成一个 `org.apache.log4j.Logger` 对象。
    - 适用于 Apache Log4j 1.x 版本。
2. `@Slf4j`：
    - 自动生成一个 `org.slf4j.Logger` 对象。
    - 适用于 SLF4J（Simple Logging Facade for Java），这是一种日志门面，可以与多种实际的日志框架（如 Logback、Log4j 等）集成。
3. `@Log4j2`：
    - 自动生成一个 `org.apache.logging.log4j.Logger` 对象。
    - 适用于 Apache Log4j 2.x 版本。

#### 使用示例
假设我们有一个类 `ExampleClass`，并且我们想要使用 SLF4J 作为日志框架，我们可以这样使用 `@Slf4j` 注解：

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {
    public void login(){
        log.info("登录验证...");
    }
    // 测试
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.login();
    }
}
```

在这个例子中，`log` 是一个静态成员变量，表示一个 `org.slf4j.Logger` 对象。Lombok 自动生成了这个日志对象，并且你可以直接使用它来进行日志记录。

#### 选择合适的注解
选择哪个注解取决于你使用的日志框架。例如：

+ 如果你使用的是 SLF4J，可以选择 `@Slf4j`。
+ 如果你使用的是 Log4j 1.x，可以选择 `@Log4j`。
+ 如果你使用的是 Log4j 2.x，可以选择 `@Log4j2`。

#### 注意事项
确保在使用这些注解之前，已经在项目中引入了相应的日志框架依赖。例如，如果你使用 SLF4J，你需要在项目中添加 SLF4J 的依赖，以及一个具体的日志实现（如 Logback）。对于其他日志框架，也需要相应地添加依赖。

#### 示例依赖
如果你使用 Maven 项目，并且选择了 SLF4J + Logback 的组合，可以添加以下依赖：

```xml
<!--Slf4j日志规范-->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.16</version>
</dependency>
<!--Slf4j日志实现：logback-->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.5.11</version>
</dependency>
```

通过这些日志注解，你可以方便地在类中使用日志记录功能，而无需手动创建日志对象。

# MyBatis逆向生成
MyBatis逆向工程：使用IDEA插件可以根据数据库表的设计逆向生成MyBatis的Mapper接口 与 MapperXML文件。

## 安装插件`free mybatis tools`	
## 在IDEA中配置数据
## 创建数据库，创建表，准备数据
## 使用脚手架创建SpringBoot项目
添加依赖：mybatis依赖、mysql驱动、Lombok库

## 生成MyBatis代码放到SpringBoot项目中
在表上右键：Mybatis-Generator

代码生成后，如果在IDEA中看不到，这样做（重新从硬盘加载）：

**<font style="color:#DF2A3F;">注意：生成的</font>**`**<font style="color:#DF2A3F;">VipMapper</font>**`**<font style="color:#DF2A3F;">接口上自动添加了</font>**`**<font style="color:#DF2A3F;">@Repository</font>**`**<font style="color:#DF2A3F;">注解，这个注解没用，删除即可。</font>**

**<font style="color:#DF2A3F;"></font>**

## 编写mybatis相关配置
application.properties属性文件的配置：

```properties
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=root
spring.datasource.password=123456

mybatis.mapper-locations=classpath:com/powernode/springboot/repository/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
```

## 编写测试程序
```java
@MapperScan(basePackages = "com.powernode.springboot.repository")
@SpringBootApplication
public class Sb306SpringbootMybatisGeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb306SpringbootMybatisGeneratorApplication.class, args);
        VipMapper vipMapper = applicationContext.getBean("vipMapper", VipMapper.class);
        // 增
        Vip vip = new Vip();
        vip.setName("孙悟空");
        vip.setBirth("1999-11-11");
        vip.setCardNumber("1234567894");
        vipMapper.insert(vip);
        // 查一个
        Vip vip1 = vipMapper.selectByPrimaryKey(2L);
        System.out.println(vip1);
        // 改
        vip1.setName("孙行者");
        vipMapper.updateByPrimaryKey(vip1);
        // 删
        vipMapper.deleteByPrimaryKey(1L);

        // 关闭Spring容器
        applicationContext.close();
    }
}
```

到此，Spring Boot整合MyBatis结束！

# 整合SpringMVC（SSM整合）
SSM整合：Spring + SpringMVC + MyBatis

Spring Boot项目本身就是基于Spring框架实现的。因此SSM整合时，只需要在整合MyBatis框架之后，引入`web启动器`即可完成SSM整合。

## 使用脚手架创建SpringBoot项目	
添加依赖：web启动器、mybatis启动器、mysql驱动依赖、lombok依赖

## 使用`free mybatis tool`插件逆向生成MyBatis代码
将`springboot`数据库中的`t_vip`表逆向生成mybatis代码。

## 整合MyBatis		
1. 编写数据源的配置

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
```

2. 编写mapper xml配置文件的位置

```properties
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
```

3. 在主入口类上添加`@MapperScan`注解

```java
package com.powernode.sb307ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.powernode.sb307ssm.repository"})
@SpringBootApplication
public class Sb307SsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sb307SsmApplication.class, args);
    }

}
```

## 编写service
编写`VipService`接口：

```java
public interface VipService {
    /**
     * 根据id获取会员信息
     * @param id 会员标识
     * @return 会员信息
     */
    Vip getById(Long id);
}

```

编写`VipServiceImpl`实现类：

```java

@Service("vipService")
public class VipServiceImpl implements VipService {

    @Autowired
    private VipMapper vipMapper;

    @Override
    public Vip getById(Long id) {
        return vipMapper.selectByPrimaryKey(id);
    }
}

```

## 编写controller
编写`VipController`，代码如下：

```java
@RestController
public class VipController {
    
    @Autowired
    private VipService vipService;
    	
    @GetMapping("/vip/{id}")
    public Vip detailById(@PathVariable("id") Long id){
        Vip vip = vipService.getById(id);
        return vip;
    }
}

```

## 启动服务器测试
​	

