package dev.hugofaria.metaapi.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dev.hugofaria.metaapi.domain.model.Sale;
import dev.hugofaria.metaapi.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SmsService {

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.from}")
    private String twilioPhoneFrom;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;

    private final SaleRepository repository;

    public SmsService(SaleRepository repository) {
        this.repository = repository;
    }

    public void sendSms(String saleId) {

        Sale sale = repository.findById(saleId).orElseThrow(NoSuchElementException::new);

        String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();

        String msg = "Vendedor " + sale.getSellerName() + " foi destaque em " + date + " com um total de R$ "
                + String.format("%.2f", sale.getAmount());

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        Message message = Message.creator(to, from, msg).create();

        System.out.println(message.getSid());
    }
}