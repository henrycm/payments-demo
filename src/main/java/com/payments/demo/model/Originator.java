package com.payments.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Originator {
    @Id
    private long id;
    private String name;

    public Originator() {
    }

    public Originator(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}