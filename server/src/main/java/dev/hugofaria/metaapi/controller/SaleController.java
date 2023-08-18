package dev.hugofaria.metaapi.controller;

import dev.hugofaria.metaapi.domain.model.Sale;
import dev.hugofaria.metaapi.service.SaleService;
import dev.hugofaria.metaapi.service.SmsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "sales")
public class SaleController {

    private final SaleService service;

    private final SmsService smsService;

    public SaleController(SaleService service, SmsService smsService) {
        this.service = service;
        this.smsService = smsService;
    }

    @GetMapping
    public Page<Sale> findSales(
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate,
            Pageable pageable) {
        return service.findSales(minDate, maxDate, pageable);
    }

    @GetMapping("/{id}/notification")
    public void notifySms(@PathVariable String id) {
        smsService.sendSms(id);
    }
}