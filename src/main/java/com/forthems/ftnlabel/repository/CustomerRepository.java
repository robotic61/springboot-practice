package com.forthems.ftnlabel.repository;

import com.forthems.ftnlabel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Right now we have table but:

We cannot put data into table yet

We need something that can do:

INSERT INTO customer ...
SELECT * FROM customer ...

That “something” = Repository
Repository = tool that talks to database

⚠️ Important difference
In normal Java:
Interface → I must write the code(method) myself

Spring boot:
You write interface
↓
Spring sees it
↓
Spring builds the real class for you
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContaining(String word); // Find name containing "mm" like "%mm%"
    // or List<Customer> findByNameLike(String pattern)
    // SELECT * FROM customer WHERE name LIKE '%mm%';

    List<Customer> findByNameStartingWith(String word);  // Find name starting with "word"

    List<Customer> findByNameEndingWith(String word); // Find name ending with "word"

    List<Customer> findByNameContainingAndEmailContaining(String name, String email);
    // Combine conditions
    // Find name containing "name" amd email containing "email".

    List<Customer> findByIdEqualsAndNameContainingOrEmailContaining(Long id, String name, String email);
    // what if we want NameContainingOrEmailContaining then and findByIdEquals
    // or we want bracket () wants to do what first? A and (B or C), can just put (B or C) in front but...
    // what if we work with more than one table and connect them, like needing to use JOIN.
    // or we need COUNT, distinct
    // Use @Query = custom query
    // try simple queries
    // but real work: complex queries

    /*
    SELECT *
    FROM customer
    WHERE name LIKE ? AND email LIKE ?;

    findByNameContainingAndEmailContaining("mm", "gmail");

    SELECT *
    FROM customer
    WHERE name LIKE '%mm%' AND email LIKE '%gmail%';
     */

    List<Customer> findByNameAndEmail(String name, String email);
    /*
    SELECT * FROM customer
    WHERE name = ? AND email = ?;

    findByNameAndEmail("Temmy", "temmy@gmail.com");
    SELECT * FROM customer
    WHERE name = "Temmy" AND email = "temmy@gmail.com"
     */

    void deleteByName(String name);
    // DELETE FROM customer WHERE name = ?;
    // Deletes ALL customers where name = given value

    void deleteByNameContaining(String word);
    // DELETE FROM customer WHERE name LIKE '%keyword%';

    void deleteByEmail(String email);


}
    // JpaRepository<Entity, IdType>
    // this repository works with the Customer table and
    //  Customer's table id type is Long.

    //1. Which table? → Customer
    //2. What type is the id? → Long
    //JpaRepository<Customer, Long> = “Repository for Customer table using Long as id”

    /*
    Customer → which table
    Long → type of id


    Normal interface → YOU implement
    JpaRepository interface → SPRING implements

    Scans for repository interfaces
    Recognizes JpaRepository
    Generates implementation dynamically

    🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴
    JpaRepository = interface that declares database methods
    CustomerRepository = interface that inherits those methods
    Spring = creates the actual implementation automatically
    🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴

    Behind the scenes, Spring generates something like:

    class CustomerRepositoryImpl implements CustomerRepository {

        public Customer save(Customer c) {
            // Hibernate INSERT logic
        }

        public List<Customer> findAll() {
            // SELECT logic
        }

        public Optional<Customer> findById(Long id) {
            // SELECT WHERE id
        }
     }

    Spring boot implements ready-made database methods like:
    save()
    findAll()
    findById()
    deleteById()

    save(customer);          // insert or update
    findAll();               // get all rows
    findById(id);            // get one row
    deleteById(id);          // delete row
    count();                 // count rows

    JpaRepository (interface)
       ↓ declares methods
    CustomerRepository (interface)
       ↓ inherits methods
    Spring generates class
       ↓
    You use it in service
     */
