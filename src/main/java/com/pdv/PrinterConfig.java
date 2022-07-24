package com.pdv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pers.pete.printer.ThermalPrinter;

@Configuration
public class PrinterConfig {
  
  @Bean
  public ThermalPrinter thermalPrinter() {
    return new ThermalPrinter(145, true, "EPSON");
  }
}