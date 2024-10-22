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
        System.out.println("\n\nstarting server\n\n");
        // app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::insertAccountHandler);
        
        app.error(400, ctx -> {
            ctx.status(400);
        });
        // app.exception(NullPointerException.class, null)

        return app;
    }

    public void insertAccountHandler(Context ctx) throws JsonProcessingException {

        System.out.println("this is my print: ");
        System.out.println(ctx.body());

        // first convert user input into JSON using mapper
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        System.out.println("this is account: " + account);
        System.out.println("this is account user name: " + account.getUsername());

        // check to see that username was inputed
        if (account.getUsername() == "") {
            System.out.println("here1");
            ctx.status(400);
            return;
        }
        // check to see that password is at least 4 characters long
        if (account.getPassword().length() < 4) {
            System.out.println("here2");
            ctx.status(400);
            return;
        }
        // send the information to service
        Account addedAccount = this.accountService.insertAccount(account);

        if (addedAccount == null) {
            System.out.println("here3");
            ctx.status(400);
            return;
        }

        ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
        // return;
    }
}