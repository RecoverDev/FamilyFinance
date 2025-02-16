package ru.student.familyfinance_desktop.Configuration;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import ru.student.familyfinance_desktop.Model.WorkPeriod;

@Configuration
public class ServiceConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WorkPeriod currentPeriod() {
        WorkPeriod period = new WorkPeriod();
        LocalDate date = LocalDate.now();
        period.setCurrentPeriod(LocalDate.of(date.getYear(), date.getMonthValue(), 1));
        return period;
    }


}
