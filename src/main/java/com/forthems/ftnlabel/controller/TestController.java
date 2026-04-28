package com.forthems.ftnlabel.controller;


import com.forthems.ftnlabel.model.User;
import com.forthems.ftnlabel.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Controller = waiter(delivers the food)
// receives and handles http request/creates api
// a returned object becomes JSON automatically.
@RestController
public class TestController {

    private final UserService userService;
    // This creates a variable to store the service.
    // final cuz only set it once and never changed.

    public TestController(UserService userService) { // “This controller needs a UserService”
        // Spring does this behind the scenes:
        // UserService service = new UserService();
        //TestController controller = new TestController(service);
        // Spring creates object → passes into constructor → you use it
        /*
        Any Spring-managed class

        Anything with:

        Example:
        @Service
        @RestController
        @Component
        @Repository

        We can use constructor injection
         */
        this.userService = userService;
    }
    // This is called constructor injection.
    // Spring automatically gives your controller a UserService object.

    @GetMapping("/hello")
    public String firstAPI() {
        return "My first controller";
    }

    @GetMapping(path = "/age")
    public User showAge() {
        return userService.showAgeUser();
        /*
        If the controller returns an object
        → Jackson uses getters
        → Converts to JSON
        → Sends all fields (if filed not set then its null)
         */
    }

    @PostMapping(path = "/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /*
     Instead of using dto:
     We use @PostMapping(path = "/save", produces = "application/json...)
     // *** seems to be only formatting for the JASON created
     sout get.Name directly to check.
     */
}
