package com.CristianTello.picoyplaca.service;
import com.CristianTello.picoyplaca.model.CheckLog;
import com.CristianTello.picoyplaca.model.PicoPlacaRule;
import com.CristianTello.picoyplaca.repository.CheckLogRepository;
import com.CristianTello.picoyplaca.repository.PicoPlacaRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class PicoYPlacaService implements IServicePicoYPlaca {

    private final PicoPlacaRuleRepository ruleRepository;
    private final CheckLogRepository logRepository;

    @Autowired
    public PicoYPlacaService(PicoPlacaRuleRepository ruleRepository, CheckLogRepository logRepository) {
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public boolean isVehicleAllowed(String licensePlate, LocalDate date, LocalTime time) {
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        PicoPlacaRule rule = ruleRepository.findByDayOfWeek(dayName)
                .orElseThrow(() -> new RuntimeException("No rule found for " + dayName));

        char lastChar = licensePlate.charAt(licensePlate.length() - 1);
        int lastDigit = Character.getNumericValue(lastChar);

        boolean isInMorning = !time.isBefore(rule.getMorningStart()) && !time.isAfter(rule.getMorningEnd());
        boolean isInEvening = !time.isBefore(rule.getEveningStart()) && !time.isAfter(rule.getEveningEnd());
        boolean isRestricted = rule.getRestrictedDigits().contains(lastDigit) && (isInMorning || isInEvening);

        CheckLog log = new CheckLog();
        log.setLicensePlate(licensePlate);
        log.setQueryDate(date);
        log.setQueryTime(time);
        log.setIsAllowed(!isRestricted);
        logRepository.save(log);

        return !isRestricted;
    }
}
