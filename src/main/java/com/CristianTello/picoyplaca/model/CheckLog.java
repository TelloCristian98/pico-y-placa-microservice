package com.CristianTello.picoyplaca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "check_logs")
@Data
@NoArgsConstructor
public class CheckLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private LocalDate queryDate;
    private LocalTime queryTime;
    private Boolean isAllowed;

    private LocalDateTime createdAt = LocalDateTime.now();
}
