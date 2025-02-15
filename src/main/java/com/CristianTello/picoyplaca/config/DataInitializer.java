package com.CristianTello.picoyplaca.config;

import com.CristianTello.picoyplaca.model.PicoPlacaRule;
import com.CristianTello.picoyplaca.repository.PicoPlacaRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PicoPlacaRuleRepository ruleRepository;

    public DataInitializer(PicoPlacaRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public void run(String... args) {
        if (ruleRepository.count() == 0) {
            // Weekdays with restrictions
            ruleRepository.save(createRule("Monday", Arrays.asList(1, 2)));
            ruleRepository.save(createRule("Tuesday", Arrays.asList(3, 4)));
            ruleRepository.save(createRule("Wednesday", Arrays.asList(5, 6)));
            ruleRepository.save(createRule("Thursday", Arrays.asList(7, 8)));
            ruleRepository.save(createRule("Friday", Arrays.asList(9, 0)));

            // Weekends: no restrictions, full-day allowed
            ruleRepository.save(createRule("Saturday", Collections.emptyList()));
            ruleRepository.save(createRule("Sunday", Collections.emptyList()));
        }
    }

    private PicoPlacaRule createRule(String day, java.util.List<Integer> digits) {
        PicoPlacaRule rule = new PicoPlacaRule();
        rule.setDayOfWeek(day);
        rule.setRestrictedDigits(digits);
        if (day.equals("Saturday") || day.equals("Sunday")) {
            // For weekends, set the time range to cover the entire day
            rule.setMorningStart(LocalTime.MIN);
            rule.setMorningEnd(LocalTime.MAX);
            rule.setEveningStart(LocalTime.MIN);
            rule.setEveningEnd(LocalTime.MAX);
        } else {
            // For weekdays, use the standard restricted periods
            rule.setMorningStart(LocalTime.of(6, 0));
            rule.setMorningEnd(LocalTime.of(9, 30));
            rule.setEveningStart(LocalTime.of(16, 0));
            rule.setEveningEnd(LocalTime.of(20, 0));
        }
        return rule;
    }
}