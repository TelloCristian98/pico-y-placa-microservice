package com.CristianTello.picoyplaca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pico_placa_rules")
@Data
@NoArgsConstructor
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
}