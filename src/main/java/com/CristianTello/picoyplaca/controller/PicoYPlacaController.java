package com.CristianTello.picoyplaca.controller;

import com.CristianTello.picoyplaca.service.PicoYPlacaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class PicoYPlacaController {

    private final PicoYPlacaService picoYPlacaService;

    @Autowired
    public PicoYPlacaController(PicoYPlacaService picoYPlacaService) {
        this.picoYPlacaService = picoYPlacaService;
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkPicoYPlaca(
            @RequestParam @NotBlank(message = "License plate is required")
            @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "License plate must be like ABC-1234")
            String licensePlate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            LocalTime time) {

        boolean allowed = picoYPlacaService.isVehicleAllowed(licensePlate, date, time);
        Map<String, Object> response = new HashMap<>();
        response.put("licensePlate", licensePlate);
        response.put("date", date);
        response.put("time", time);
        response.put("isAllowed", allowed);
        return ResponseEntity.ok(response);
    }
}