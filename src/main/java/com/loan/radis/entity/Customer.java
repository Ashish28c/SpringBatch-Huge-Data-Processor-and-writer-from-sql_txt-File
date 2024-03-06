package com.loan.radis.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_entity")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	
    @Column(name = "br_no")
    private int brNo;

    @Column(name = "customer_no")
    private String customerNo;

    @Id
    @Column(name = "acct_no")
    private String acctNo;

    @Column(name = "loan_bal")
    private double loanBal;

    @Column(name = "stat")
    private String stat;

    @Column(name = "name")
    private String name;

    @Column(name = "pan")
    private String pan;

    @Column(name = "uid_no")
    private String uidNo;

    @Column(name = "add1")
    private String add1;

    @Column(name = "add2")
    private String add2;

    @Column(name = "add3")
    private String add3;

    @Column(name = "add4")
    private String add4;

    @Column(name = "pin")
    private String pin;

    @Column(name = "tel_no")
    private String telNo;

    @Column(name = "act_type")
    private String actType;

    @Column(name = "arr_amt")
    private double arrAmt;

    @Column(name = "npa_date")
    private String npaDate;

    @Column(name = "old_bad_debt_ind")
    private String oldBadDebtInd;

    @Column(name = "bad_debt_ind")
    private String badDebtInd;

    @Column(name = "inca")
    private double inca;

    @Column(name = "theo_loan_bal")
    private double theoLoanBal;

    @Column(name = "theo_unpd_prin_bal")
    private double theoUnpdPrinBal;

    @Column(name = "cap_theo_unpd_int")
    private double capTheoUnpdInt;

    @Column(name = "theo_unpd_arrs_int")
    private double theoUnpdArrsInt;

    @Column(name = "unpd_prin_bal")
    private double unpdPrinBal;

    @Column(name = "cap_unpd_int")
    private double capUnpdInt;

    @Column(name = "unpd_chrg_bal")
    private double unpdChrgBal;

    @Column(name = "theo_unpd_chrg_bal")
    private double theoUnpdChrgBal;

    @Column(name = "unpd_arrs_int_bal")
    private double unpdArrsIntBal;

}
