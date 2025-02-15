package com.CristianTello.picoyplaca.service;

import com.CristianTello.picoyplaca.model.PicoPlacaRule;
import com.CristianTello.picoyplaca.model.CheckLog;
import com.CristianTello.picoyplaca.repository.PicoPlacaRuleRepository;
import com.CristianTello.picoyplaca.repository.CheckLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PicoYPlacaServiceTest {

    @Mock
    private PicoPlacaRuleRepository ruleRepository;

    @Mock
    private CheckLogRepository logRepository;

    @InjectMocks
    private PicoYPlacaService picoYPlacaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsVehicleAllowed_whenRestricted() {
        // Setup a rule for Monday (restricted for plates ending in 1,2)
        PicoPlacaRule rule = new PicoPlacaRule();
        rule.setDayOfWeek("Monday");
        rule.setRestrictedDigits(Arrays.asList(1, 2));
        rule.setMorningStart(LocalTime.of(6, 0));
        rule.setMorningEnd(LocalTime.of(9, 30));
        rule.setEveningStart(LocalTime.of(16, 0));
        rule.setEveningEnd(LocalTime.of(20, 0));

        LocalDate date = LocalDate.of(2025, 2, 10); // Monday
        LocalTime time = LocalTime.of(8, 0);
        String licensePlate = "ABC-1231"; // ends with 1, should be restricted

        when(ruleRepository.findByDayOfWeek("Monday")).thenReturn(Optional.of(rule));

        boolean allowed = picoYPlacaService.isVehicleAllowed(licensePlate, date, time);
        assertFalse(allowed);

        // Verify that a log entry was created
        verify(logRepository, times(1)).save(any(CheckLog.class));
    }

    @Test
    void testIsVehicleAllowed_whenAllowed() {
        // Setup a rule for Monday (restricted for plates ending in 1,2)
        PicoPlacaRule rule = new PicoPlacaRule();
        rule.setDayOfWeek("Monday");
        rule.setRestrictedDigits(Arrays.asList(1, 2));
        rule.setMorningStart(LocalTime.of(6, 0));
        rule.setMorningEnd(LocalTime.of(9, 30));
        rule.setEveningStart(LocalTime.of(16, 0));
        rule.setEveningEnd(LocalTime.of(20, 0));

        LocalDate date = LocalDate.of(2025, 2, 10); // Monday
        LocalTime time = LocalTime.of(11, 0); // outside restricted times
        String licensePlate = "ABC-1231"; // ends with 1, but time is okay

        when(ruleRepository.findByDayOfWeek("Monday")).thenReturn(Optional.of(rule));

        boolean allowed = picoYPlacaService.isVehicleAllowed(licensePlate, date, time);
        assertTrue(allowed);
        verify(logRepository, times(1)).save(any(CheckLog.class));
    }
}