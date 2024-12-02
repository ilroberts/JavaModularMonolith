package com.ilroberts.modulith.accounts;

import com.ilroberts.modulith.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class AccountHandler {

    @ApplicationModuleListener
    public void handleNewCustomerAccount(Customer.CustomerCreatedEvent event) {
        log.info("New customer account created: {}", event);
    }
}
