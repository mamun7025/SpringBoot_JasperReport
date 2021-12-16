package com.mrgroup.springjasper;

import com.mrgroup.springjasper.domain.Customer;
import com.mrgroup.springjasper.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    CustomerRepository repository;

    public void insertTableData(){

        // 1
        Customer custInst = new Customer();
        custInst.setCustomerCode("C0001");
        custInst.setCustomerName("Walmart");
        custInst.setCustomerShortName("WAL");
        custInst.setCustomerBIN("BIN-00000000123");
        custInst.setCustomerNID("190000000000123");
        custInst.setContactNumber("01779282132");
        custInst.setAddress("Toronto, Canada");
        custInst.setBalance(89000000.00);
        custInst.setIsActive(true);
        custInst.setCreationUser("system");
        custInst.setCreationDateTime(new Date());
        this.repository.save(custInst);

        // 2
        custInst = new Customer();
        custInst.setCustomerCode("C0002");
        custInst.setCustomerName("Decathlon");
        custInst.setCustomerShortName("DCL");
        custInst.setCustomerBIN("BIN-00000000125");
        custInst.setCustomerNID("190000000000125");
        custInst.setContactNumber("01779282132");
        custInst.setAddress("Paris, France");
        custInst.setBalance(99000000.00);
        custInst.setIsActive(true);
        custInst.setCreationUser("system");
        custInst.setCreationDateTime(new Date());
        this.repository.save(custInst);

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("---CommandLineAppStartupRunner");
        this.insertTableData();

    }


}
