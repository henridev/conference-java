import com.capgemini.repository.HibernateSpeakerRepositoryImpl;
import com.capgemini.repository.SpeakerRepository;
import com.capgemini.service.SpeakerService;
import com.capgemini.service.SpeakerServiceImpl;
import com.capgemini.util.CalendarFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Calendar;

@Configuration
@ComponentScan({"com.capgemini"})
public class AppConfig {

    @Bean(name="cal")
    public CalendarFactory calFactory() {
        CalendarFactory factory = new CalendarFactory();
        factory.addDays(2);
        return factory;
    }

    @Bean
    public Calendar cal() throws Exception{
        return calFactory().getObject();
    }
//    @Bean(name = "speakerService")
//    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
//    public SpeakerService getSpeakerService() {
//        // SpeakerServiceImpl service = new SpeakerServiceImpl(this.getSpeakerRepository());
//        SpeakerServiceImpl service = new SpeakerServiceImpl();
//        // service.setRepository(this.getSpeakerRepository());
//        return service;
//    }

/*    @Bean(name = "speakerRepository")
    public SpeakerRepository getSpeakerRepository() {
        return new HibernateSpeakerRepositoryImpl();
    }*/
}


