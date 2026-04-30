package com.forthems.ftnlabel.repository;

import com.forthems.ftnlabel.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    @Query(value = "SELECT * FROM customer WHERE name LIKE %:word%", nativeQuery = true)
    List<Customer> seachByNameContainsSql(@Param("word") String word);
    /*
    🔥 Key rule (remember this)

    The string inside @Param("...") must match :... in the query
    NOT the Java variable name

     “This Java parameter should be called word inside the query.”

     So Spring maps:
     input → word → :word
     */

    /*
    🧠 Goal
    👉 Find customers where:
    name contains a keyword

    👉 SQL:
    SELECT * FROM customer WHERE name LIKE '%word%';

    1. @Query(...)

    This tells Spring Data JPA:

    “Do not create the query from the method name. Use this SQL query that I wrote myself.”

    So instead of relying on method naming like:

    findByNameContaining(String word)

    you write the SQL manually.

    2. SELECT * FROM customer WHERE name LIKE %:word%

    searchByNameSql("john")

    SELECT * FROM customer WHERE name LIKE '%john%'

    3. LIKE

    Exact match:

    WHERE name = 'John'

    Only matches:
    John

    LIKE match:

    WHERE name LIKE '%John%'

    Matches anything containing John.

    4. % = anything

    5. :word

    This is a named parameter.

    It means:

    “Spring, replace this with the value from the Java method parameter.”

    6. nativeQuery = true

    This means:

    “This query is real SQL.”

    So Spring treats it as database SQL:

    SELECT * FROM customer WHERE name LIKE ...

    Not JPQL.

    If it was JPQL, it would look more like:

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:word%")

    JPQL uses the Java entity class Customer.

    Native SQL uses the real database table customer.

    7. searchByNameSql(...)

    This is your method name.

    Because you already wrote @Query,
    the method name does not need to follow JPA naming rules.

    Spring uses the SQL inside @Query, not the method name.

    11. @Param("word") String word

    @Param("word") String word

    This connects the Java variable to the SQL parameter.

    This part:

    @Param("word")

    connects to:

    :word

    So when you call:

    searchByNameSql("tom")

    Spring puts "tom" into :word.

    Full meaning:
    @Query(value = "SELECT * FROM customer WHERE name LIKE %:word%", nativeQuery = true)
    List<Customer> searchByNameSql(@Param("word") String word);

    Means:

    “Run a native SQL query on the customer table.
    Find all rows where the name column contains the given word.
    Convert those rows into Customer objects and return them as a list.”

    Example:

    customerRepository.searchByNameSql("ann");

    Runs something like:

    SELECT * FROM customer WHERE name LIKE '%ann%';

    Returns customers like:

    Ann
    Anna
    Joanne
    Brianna


    Yes—you can absolutely type it in lowercase 👍

    @Query(value = "select * from customer where name like %:word%", nativeQuery = true)

    This will work exactly the same as:

    @Query(value = "SELECT * FROM customer WHERE name LIKE %:word%", nativeQuery = true)
    🔤 So… lowercase or uppercase in SQL?
    ✔️ Technically (how SQL works)

    SQL is case-insensitive for keywords.

    That means these are all the same:

    SELECT * FROM customer
    select * from customer
    SeLeCt * FrOm customer

    They all work.

    Most developers follow this convention:

    ✅ Keywords → UPPERCASE
    SELECT * FROM customer WHERE name LIKE '%john%'
    ✅ Table & column names → lowercase (usually)
    customer
    name


     ✔️ WHERE, LIKE → can be lowercase or uppercase (doesn’t matter)
    ✔️ 'john' → case sensitivity depends on database

    So:

    LIKE '%john%'

    can match:

    John
    JOHN
    john
    JoHn
     */



    // Goal: Find name where name is exactly something
    // write SQL first(try speak like human then write out its very similar)
    // SELECT * FROM customer WHERE name = 'John';
    @Query(value = "SELECT * FROM customer WHERE name = :name", nativeQuery = true)
    List<Customer> findByNameSql(@Param("name") String name);
    // Take String name as input and use it as a parameter in the SQL query.


    // Goal: Find by age greater than something
    // SQL
    // SELECT * FROM customer WHERE age > 20;
    @Query(value = "SELECT * FROM customer WHERE age > :age", nativeQuery = true)
    List<Customer> findAgeGreater(@Param("age") int age);


    // Goal: Do multiple conditions(name like "something" AND age > something)
    // SQL
    // SELECT * FROM customer WHERE name LIKE '%Yo' AND age > 20;
    @Query(value = "SELECT * FROM customer WHERE name LIKE :word% AND age > :age", nativeQuery = true)
    List<Customer> findNameLikeAndAgeGreater(@Param("word") String word, @Param("age") int age);


    // LIKE :word%  = starts with "word"
    // LIKE %:word = ends with "word"
//    @Query(value = "SELECT * FROM customer WHERE age > :age AND name LIKE CONCAT('%', :word, '%')", nativeQuery = true)
//    List<Customer> findNameLikeAndAgeGreater(@Param("word") String word, @Param("age") int age);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customer WHERE name = :name", nativeQuery = true)
    void deleteByNameSql(@Param("name") String name);
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
