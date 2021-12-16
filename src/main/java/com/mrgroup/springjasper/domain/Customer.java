package com.mrgroup.springjasper.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String customerCode;
    String customerName;
    String customerShortName;

    @Column(name = "CUSTOMER_BIN")
    String customerBIN;
    @Column(name = "CUSTOMER_NID")
    String customerNID;
    String contactNumber;
    String address;

    Double balance;
    Boolean isActive;

    @Column(name = "CREATION_DATETIME")
    Date creationDateTime;
    @Column(name = "CREATION_USR")
    String creationUser;
    @Column(name = "LAST_UPDATE_DATETIME")
    Date lastUpdateDateTime;
    @Column(name = "LAST_UPDATE_USER")
    String lastUpdateUser;


}
