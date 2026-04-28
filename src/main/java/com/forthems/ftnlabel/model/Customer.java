package com.forthems.ftnlabel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Now Spring Boot will create a table inside it from your Java class.

// Entity means:
// Java class → database table

/*
fields(column name):
id
name
email
 */

@Entity // Create a table

/*
Customer → table "customer"
id → column "id"
name → column "name"
email → column "email"
 */
public class Customer {

    @Id //this is the id(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // so that id is auto created by MySql
    private Long id;

    private String name;
    private String email;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
