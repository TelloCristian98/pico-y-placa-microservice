package com.CristianTello.picoyplaca.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pico_placa_rules")
public class PicoPlacaRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayOfWeek;

    @ElementCollection
    @CollectionTable(name = "restricted_digits", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "digit")
    private List<Integer> restrictedDigits;

    private LocalTime morningStart;
    private LocalTime morningEnd;
    private LocalTime eveningStart;
    private LocalTime eveningEnd;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getRestrictedDigits() {
        return restrictedDigits;
    }

    public void setRestrictedDigits(List<Integer> restrictedDigits) {
        this.restrictedDigits = restrictedDigits;
    }

    public LocalTime getMorningStart() {
        return morningStart;
    }

    public void setMorningStart(LocalTime morningStart) {
        this.morningStart = morningStart;
    }

    public LocalTime getMorningEnd() {
        return morningEnd;
    }

    public void setMorningEnd(LocalTime morningEnd) {
        this.morningEnd = morningEnd;
    }

    public LocalTime getEveningStart() {
        return eveningStart;
    }

    public void setEveningStart(LocalTime eveningStart) {
        this.eveningStart = eveningStart;
    }

    public LocalTime getEveningEnd() {
        return eveningEnd;
    }

    public void setEveningEnd(LocalTime eveningEnd) {
        this.eveningEnd = eveningEnd;
    }
}