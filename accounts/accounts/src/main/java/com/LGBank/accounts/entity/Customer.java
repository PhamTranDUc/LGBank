package com.LGBank.accounts.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

@Entity
@Data
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "customer_id")
    private Long customerId;

    private String name;

    private String email;

    @Column(name= "mobile_number")
    private String mobileNumber;
}
