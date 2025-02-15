package com.CristianTello.picoyplaca.service;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IServicePicoYPlaca {
    boolean isVehicleAllowed(String licensePlate, LocalDate date, LocalTime time);
}
