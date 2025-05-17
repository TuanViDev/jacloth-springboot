package com.vanlang.webbanquanao.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;
    private String address;
    private String phone;
    private String paymentMethod;
    private int totalAmount;
    private String status;
    private String checkoutUrl;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "invoice_id")
    private List<Checkout> checkout;
}
