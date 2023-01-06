package com.ejbank.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ejbank_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;



    public Integer getId() {
        return id;
    }

    public Integer getAccount_id_from() {
        return account_id_from;
    }

    public Integer getAccount_id_to() {
        return account_id_to;
    }

    public String getAuthor() {
        return author;
    }

    public Float getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getApplied() {
        return applied;
    }

    public Date getDate() {
        return date;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    @Column(name = "account_id_from")
    private Integer account_id_from;

    @Column(name = "account_id_to")
    private Integer account_id_to;

    @Column
    private String author;

    @Column
    private Float amount;

    @Column
    private String comment;

    @Column
    private Boolean applied;

    @Column
    private Date date;

    public Transaction() {}




    public Transaction(Integer account_id_to, Integer account_id_from, String author, Float amount, String comment, Boolean applied, Date date) {
        this.account_id_to = Objects.requireNonNull(account_id_to);
        this.account_id_from = Objects.requireNonNull(account_id_from);
        this.author = Objects.requireNonNull(author);
        this.amount = Objects.requireNonNull(amount);
        this.comment = comment;
        this.applied = Objects.requireNonNull(applied);
        this.date = Objects.requireNonNull(date);
    }
}
