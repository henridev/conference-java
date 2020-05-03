# steps

1. add pom
2. add model
3. add repository 
4. add service 
5. run application


# dependency injection 

instead of declaring and hardcoding the instantiation of a class 
within a class. we will instantiate a new class outside the class and then 
inject it into the class that needs it. 

# Spring project structure



|--src
|----java
|------com.name
|--------model
|----------test
|--------repository 
|----------hibernate repo implementation
|----------actual repo
|--------service 



# Starter Files 

## model 

just a class with getter and setters to define what our data looks like 

```java
package com.capgemini.model;

public class Speaker {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
```

## repository 

this will have a way to implement our function and then an actual interface with which we will implement our defined implementation 

```java
package com.capgemini.repository;
import com.capgemini.model.Speaker;
import java.util.ArrayList;
import java.util.List;
// here we have te implements which means this defnition is use in this repo 
public class HibernateSpeakerRepositoryImpl implements SpeakerRepository {
    public List<Speaker> findAll() {
        List<Speaker> speakers = new ArrayList<Speaker>();

        Speaker speaker = new Speaker();
        speaker.setFirstName("Henri");
        speaker.setLastName("De Bel");

        speakers.add(speaker);

        return speakers;
    }
}
```

in our code we can now just call on SpeakerRepository 

```java
package com.capgemini.repository;
import com.capgemini.model.Speaker;
import java.util.List;

public interface SpeakerRepository {
    List<Speaker> findAll();
}
```

## Services

this will have a way to implement our function and then an actual interface with which we will implement our defined implementation 

```java
package com.capgemini.service;
import com.capgemini.model.Speaker;
import com.capgemini.repository.SpeakerRepository;
import java.util.List;

public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository repository;

    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.repository = speakerRepository;
    }

    public List<Speaker> findAll() {
        return repository.findAll();
    }

    public void setRepository(SpeakerRepository repository) {
        this.repository = repository;
    }
}
```

```java
package com.capgemini.service;
import com.capgemini.model.Speaker;
import java.util.List;
public interface SpeakerService {
    List<Speaker> findAll();
}
```

## start up an application 

```java
import com.capgemini.service.SpeakerService;
import com.capgemini.service.SpeakerServiceImpl;


public class Application {
    public static void main(String[] args){
        SpeakerService service = new SpeakerServiceImpl();
        System.out.println(service.findAll().get(0).getFirstName());
    }
}
```

# Configuration of spring 

all of the configuration starts here and trickles into the whole application 

```java
import com.capgemini.repository.HibernateSpeakerRepositoryImpl;
import com.capgemini.repository.SpeakerRepository;
import com.capgemini.service.SpeakerService;
import com.capgemini.service.SpeakerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// class level anotation 
@Configuration
public class AppConfig {
    @Bean(name = "speakerService")
    public SpeakerService getSpeakerService() {
        // we get our reposository and inject it into our implementation 
        // to then get a service back 
        SpeakerServiceImpl service = new SpeakerServiceImpl(this.getSpeakerRepository());
        return service;
    }

    @Bean(name = "speakerRepository")
    public SpeakerRepository getSpeakerRepository() {
        return new HibernateSpeakerRepositoryImpl();
    }
}

```



# Scopes

**singleton** = default bean scope means one instance per spring container 

**prototype** = ensures a unique instance per request (opposite singleton)

**Request** = bean instance per http request (more than prototype)

**session** = single bean per http session 

**globalsession** = same one for entire life of the application

```java
@Configuration
public class AppConfig {
    @Bean(name = "speakerService")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SpeakerService getSpeakerService() {
        SpeakerServiceImpl service = new SpeakerServiceImpl(this.getSpeakerRepository());
        // service.setRepository(this.getSpeakerRepository());
        return service;
    }

    @Bean(name = "speakerRepository")
    public SpeakerRepository getSpeakerRepository() {
        return new HibernateSpeakerRepositoryImpl();
    }
}

public class Application {
    public static void main(String[] args){
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        SpeakerService service = appContext.getBean("speakerService", SpeakerService.class);
        System.out.println(service);

        SpeakerService service2 = appContext.getBean("speakerService", SpeakerService.class);
        System.out.println(service2);
    }
}

// we will get e new instance of the service every time we request it from the container 

```

# autowired

```
@ComponentScan({"
	com.capgemini
"})
// look here for autowired annotations 

// speaker repository bean automaticaly injected into setter 
@Autowired
public void setRepository(SpeakerRepository repository) {
    System.out.println("SpeakerService setter");
    this.repository = repository;
}
```

