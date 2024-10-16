package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public void SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/login", this::insertAccountHandler);

        return app;
    }

    public void insertAccountHandler(Context ctx) throws JsonProcessingException{

        // first convert user input into JSON using mapper
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        // check to see that username was inputed
        if (account.getUsername() == "") {
            ctx.status(400).result("Error: Username cannot be blank");
        }
        // check to see that password is at least 4 characters long
        if (account.getPassword().length() < 4) {
            ctx.status(400).result("Error: Password must be at least 4 characters");
        }
        // send the information to service
        Account addedAccount = this.accountService.insertAccount(account);

        if (addedAccount == null) { // also input something where user is not unique, but if it's something else return somemthing else
            ctx.status(400).result("Error: Account with that username already exists");
        }

        ctx.json(mapper.writeValueAsString(addedAccount));
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}