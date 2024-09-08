package com.octl2.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bp_warehouse")
@Getter
@Setter
public class Warehouse {
    private Long partner_id;

    @Id
    @Column(name = "warehouse_id")
    private Long id;

    @Column(name = "warehouse_name")
    private String name;

    @Column(name = "warehouse_shortname")
    private String shortname;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    private String address;

    @Column(name = "full_address")
    private String fullAddress;

}
