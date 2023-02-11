![img.png](assets/img.png)

# bring-svydovets

## Project description

---

[//]: # (Bring is a dependency injection framework. It uses IoC &#40;Inversion of Control&#41; container --------?????????????????????????????????????????????????)
[//]: # (**Bring project is an implementation of the [Inversion of control container &#40;IoC&#41;]&#40;https://en.wikipedia.org/wiki/Dependency_injection&#41;.**)

Objects created by the container are also called managed objects or beans. 
The container can be configured by detecting specific Java annotations.

Objects can be obtained by means of either dependency lookup or dependency injection. 
Dependency lookup is a pattern where a caller asks the container object for an object with a specific name or of a specific type. 
Dependency injection is a pattern where the container passes objects by name to other objects, via either constructors, properties.


## Get started

---
1. ```git clone https://github.com/rovein/bring-svydovets```
2. ```cd <path_to_bring_svydovets>/bring-svydovets```
3. ```mvn clean install -DskipTests```
4. add as a dependency
```
<dependency>
   <groupId>com.bobocode.svydovets</groupId>
   <artifactId>bring-svydovets</artifactId>
   <version>1.0</version>
</dependency>
```

**What you need:**

1. ```Java 17 or later```
2. ```Maven 3.5+```

An example of simple `Bring-Svydovets` application

Create an `AnnotationApplicationContext` with root package as constructor param
```
public class BringDemo {
    public static void main(String[] args) {
        AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.bring");
    }
}
```

For adding beans into context use **[@Component](#component)** or **[@Bean](#bean)**

**Please NOTE: default constructor is required**
```
public interface Printable {
    void printHello();
}
```


```
public class DemoBean implements Printable {
    @Override
    public void printHello() {
        System.out.println("Hello from DemoBean");
    }
}

@Configuration()
public class DemoConfiguration {
    @Bean
    public DemoBean getDemoBean() {
        return new DemoBean();
    }
}
```
```
@Component
public class DemoComponent implements Printable {
    @Override
    public void printHello() {
        System.out.println("Hello from DemoComponent");
    }
}
```

Now you can get the objects from AnnotationApplicationContext

```
public class BringDemo {
    public static void main(String[] args) {
        AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.bring");

        Printable demoComponent = applicationContext.getBean(DemoComponent.class);
        Printable demoBean = applicationContext.getBean(DemoBean.class);

        demoComponent.printHello();
        demoBean.printHello();
    }
}
```
For more options please see **Features**

**Enjoy**

****

## Features

---
- **[Application context](#context)**
- **[@Configuration](#configuration)** - configuration file
- **[@Bean](#bean)**, **[@Component](#component)** - class that managed by IoC container ([@Component versus @Bean](#component-versus-bean))
- **[@AutoSvydovets](#autoSvydovets)** - [field](#field-injection)/[constructor](#constructor-injection)/[setter](#setter-injection) injection
- **[@Qualifier](#qualifier)** - specify a bean name
- **[@Value](#value)** - injects value from property to bean
- **[@Primary](#primary)** - make preferable for injection without specifying the bean name
- **[@Scope](#scope)** - allow to set BeanScope value Singleton or Prototype
- **[@PostConstruct](#postConstruct)** - allows to execute code after dependency injection before the class is put into service
- **[BeanPostProcessor](#beanPostProcessor)** - hook that allows for custom modification of new bean instances


### Context

---
Create new instance of **AnnotationApplicationContext**
and pass a string with packages name as a parameter. 
This packages and all sub packages will be scanned and beans will be added to the context.

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.autowiring.success.SuccessPrinterServiceImpl;

public class Application {
  public static void main(String[] args) {
    AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.success");
    SuccessPrinterServiceImpl printerService = applicationContext.getBean(SuccessPrinterServiceImpl.class);
  }
}
```
</details>


### @Configuration

---
To mark a class that will contain beans - use **@Configuration** annotation. 
To declare a bean create a method and mark it as **[@Bean](#bean)**.

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;

@Configuration
public class TestConfig {

    @AutoSvydovets
    private AutoSvydovetsDependency autoSvydovetsDependency;

    @Bean
    public FooService fooService() {
        FooService fooService = new FooService();
        fooService.setMessage("Foo");
        return fooService;
    }

    @Bean
    public FooBarService fooBarService() {
        FooBarService fooBarService = new FooBarService(fooService());
        fooBarService.setMessage("Bar");
        return fooBarService;
    }

    @Bean
    public AutoSvydovetsClientBean autoSvydovetsClientBean() {
        return new AutoSvydovetsClientBean(autoSvydovetsDependency);
    }
}
```
</details>


### @Component versus @Bean

---
- **[@Component](#component)** is a class level annotation (annotation configuration) whereas **[@Bean](#bean)** is a method level annotation (Java configuration).
- **[@Component](#component)** need not be used with the **[@Configuration](#configuration)** annotation whereas **[@Bean](#bean)** annotation has to be used within the class which is annotated with **[@Configuration](#configuration)**.
- We cannot create a bean of a class using **[@Component](#component)**, if the class is outside bring container whereas we can create a bean of a class using **[@Bean](#bean)** even if the class is present outside the bring container.


### @Bean

---
Is a class that managed by IoC container.
Used inside annotated class with **[@Configuration](#configuration)**.
By default, **bean name = method name**. 
The bean name can be provided via the annotation property `value`

The default bean **[Scope](#scope)** is `Singleton`

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;

@Configuration
public class TestConfig {
    @Bean("fooService1")
    public FooService foo() {
        return new FooService();
    }
}
```
</details>


### @Component

---
Is a class that managed by IoC container.

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class AutoSvydovetsDependency {
    
}
```
</details>

By default, **component name = class name**.
The component name can be provided via the annotation property `value`

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;

@Component("bean1")
public class Service {
    
}
```
</details>


### @AutoSvydovets

---
**Not recommended** to use together [constructor injection](#constructor-injection) **with** [field injection](#field-injection) - it may lead to unpredictable results.

> #### Field injection
> 
>><details>
>><summary>Example</summary> 
>>
>>```java
>>import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
>>import com.bobocode.svydovets.annotation.annotations.Component;
>>
>>@Component("printer-bean")
>>public class SuccessPrinterServiceImpl {
>>      @AutoSvydovets
>>      private SuccessMessageServiceImpl messageService;
>>
>>      @AutoSvydovets
>>      @Qualifier("dependencyImpl")
>>      private Dependency dependency;
>>}
>>```
>> When a `NoUniqueBeanException` occurs, use **[@Primary](#primary)** or **[@Qualifier](#qualifier)**.
>></details>

> #### Constructor injection
>
>><details>
>><summary>Example</summary> 
>>
>>```java
>>import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
>>import com.bobocode.svydovets.annotation.annotations.Component;
>>
>>@Component
>>public class Service {
>>
>>    private BarDependency barDependency;
>>
>>    public Service() {
>>    }
>>
>>    @AutoSvydovets
>>    public Service(FooDependency fooDependency, BarDependency barDependency) {
>>        this.barDependency = barDependency;
>>    }
>>}
>>```
>> For constructor injection, we necessarily need a default constructor.
>> When a `NoUniqueBeanException` occurs, use **[@Primary](#primary)** or **[@Qualifier](#qualifier)**.
>></details>

> #### Setter injection
>
>><details>
>><summary>Example</summary> 
>>
>>```java
>>import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
>>import com.bobocode.svydovets.annotation.annotations.Component;
>>import com.bobocode.svydovets.annotation.annotations.Qualifier;
>>
>>@Component("printer-bean")
>>public class SetterSuccessPrinterServiceImpl {
>>    private SetterSuccessMessageService messageService;
>>
>>    @AutoSvydovets
>>    @Qualifier("setterSuccessMessageService1Impl")
>>    public void setMessageService(SetterSuccessMessageService messageService) {
>>        this.messageService = messageService;
>>    }
>>}
>>```
>> When a `NoUniqueBeanException` occurs, use **[@Primary](#primary)** or **[@Qualifier](#qualifier)**.
>></details>


### @Qualifier

---
If there are multiple implementations of interface, `@Qualifier` can be used with name of implementation with `@Autowired` annotation.

Сan be used with [field injection](#field-injection-qualifier)/[setter injection](#setter-injection-qualifier)

> #### Field injection qualifier
>
>><details>
>><summary>Example</summary> 
>>
>>```java
>>import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
>>import com.bobocode.svydovets.annotation.annotations.Component;
>>
>>@Component("printer-bean")
>>public class SuccessPrinterServiceImpl {
>>      @AutoSvydovets
>>      @Qualifier("dependencyImpl")
>>      private Dependency dependency;
>>}
>>```
>></details>

> #### Setter injection qualifier
>
>><details>
>><summary>Example</summary> 
>>
>>```java
>>import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
>>import com.bobocode.svydovets.annotation.annotations.Component;
>>import com.bobocode.svydovets.annotation.annotations.Qualifier;
>>
>>@Component("printer-bean")
>>public class SetterSuccessPrinterServiceImpl {
>>    private SetterSuccessMessageService messageService;
>>
>>    @AutoSvydovets
>>    @Qualifier("setterSuccessMessageService1Impl")
>>    public void setMessageService(SetterSuccessMessageService messageService) {
>>        this.messageService = messageService;
>>    }
>>}
>>```
>></details>


### @Primary

---
If you have multiple implementations of the same type, 
you can make one of them preferable to implement by using **[@Primary](#primary)** annotation.

Put on top of a **[@Component](#component)** class.

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Primary;

@Component
@Primary
public class SecondaryAnnotationService {
    
}
```
</details>

Also, @Primary can be used with @Bean annotation on the method and class should be annotated as @Configuration

<details>
<summary>Example</summary> 

```java
@Configuration
public class TestConfig {

    @AutoSvydovets
    private AutoSvydovetsDependency autoSvydovetsDependency;

    @Bean
    @Primary
    public FooService fooSecondaryService() {
        FooService fooService = new FooService();
        fooService.setMessage("FooPrimary");
        return fooService;
    }

    @Bean
    public FooService fooService() {
        FooService fooService = new FooService();
        fooService.setMessage("Foo");
        return fooService;
    }
}
```

</details>

### @Value

---
**[@Value](#value)** allows to inject predefined values to bean.  
In order it to work `application.properties` file needs to be put to `src/main/resources` folder
properties should be split by `=` sign, like `key=value`  
Property can be injected directly:
<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class SimpleValueBean {
    @Value("simpleAccountId")
    public String accountId;
}
```
</details>

Another option is to predefine it in property file:

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class AdminAccount {
    @Value("{accountIdNum}")
    public Long accountId;
}
```
</details>

So far injections for 5 types supported:
1. Integer
2. Long
3. Double
4. String
5. `List<String>` - comma separated values will be transformed into a list of strings.

property file example:
<details>
<summary>Example</summary> 

```properties
accountId=testValue
accountIdNum=123
roles=User,Admin,SuperAdmin
```
</details>

### @Scope

---
**[@Scope](#scope)** allow to specify `Singleton` or `Prototype` for bean definition.

`Singleton` - returns single instance of class for every **[Bean](#bean)**

`Prototype` - returns a new instance of class for every **[Bean](#bean)**

The annotation could be specify on the class level if the class marked as **[@Component](#component)**:

<details>
<summary>Example</summary> 

```java
@Component
@Scope(BeanScope.PROTOTYPE)
public class MessageService implements CustomService {

    private String hello = "Hello";

    public String getMessage() {
        return hello;
    }

}
```
</details>

The annotation could be specify on the method level if the method marked as **[@Bean](#bean)**:

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;
import com.bobocode.svydovets.annotation.annotations.Scope;
import com.bobocode.svydovets.annotation.register.BeanScope;

@Configuration
public class TestConfig {

    @Bean
    @Scope(BeanScope.PROTOTYPE)
    public FooService fooService() {
        FooService fooService = new FooService();
        fooService.setMessage("Foo");
        return fooService;
    }

}
```
</details>

### @Value

---
**[@Value](#value)** allows to inject predefined values to bean.  
In order it to work `application.properties` file needs to be put to `src/main/resources` folder
properties should be split by `=` sign, like `key=value`  
Property can be injected directly:
<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class SimpleValueBean {
    @Value("simpleAccountId")
    public String accountId;
}
```
</details>

Another option is to predefine it in property file:

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class AdminAccount {
    @Value("{accountIdNum}")
    public Long accountId;
}
```
</details>

So far injections for 5 types supported:
1. Integer
2. Long
3. Double
4. String
5. `List<String>` - comma separated values will be transformed into a list of strings.

property file example:
<details>
<summary>Example</summary> 

```properties
accountId=testValue
accountIdNum=123
roles=User,Admin,SuperAdmin
```
</details>

### @PostConstruct

---
**[@PostConstruct](#postConstruct)** is used for methods only and allows to execute method(s) after dependency injection is done. Method(s) annotated with this annotation is invoked before the class is put into service.

PostConstruct annotation can be applied for several methods, but in this case execution order is not guaranteed.

The method can have any access modifier: public, protected, package private, or private.

Requirements to method(s):
1. Method can not have parameters.
2. Method can not be static.
3. Method should be inside the class annotated with Component or Configuration annotation.


<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.annotations.PostConstruct;

@Component
public class MessageService {
    
    @PostConstruct
    private void init() {
        // some code here
    }
}
```
</details>

### @BeanPostProcessor

---
This interface allows custom modification of new bean instances.
The BeanPostProcessor methods will apply to all beans.

<details>
<summary>Example</summary> 

```java
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MyAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ToNull.class))
                .toList();

        for (Field field : fields) {
            inject(bean, field);
        }

        return bean;
    }

    private void inject(Object bean, Field field) {
        try {
            field.setAccessible(true);
            field.set(bean, null);
        } catch (IllegalAccessException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }
}

@Component
public class BppComponent1WithFieldAnnotation {

    @ToNull
    private String string = "not null";
}
```
</details>