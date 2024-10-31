package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;

// how to send msg with status code
// is controller supposed to be doing all of this? why I ask is because service seems to not being too much

// TODO: do more edge cases



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
    ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        System.out.println("\n\nstarting server\n\n");

        app.post("register", this::insertAccountHandler);
        app.post("login", this::loginAccountHandler);
        app.post("messages", this::insertMessageHandler);
        app.get("messages", this::getMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesByUserHandler);

        app.error(400, ctx -> {
            ctx.status(400);
        });

        app.error(401, ctx -> {
            ctx.status(401);
        });

        return app;
    }

    public void insertAccountHandler(Context ctx) throws JsonProcessingException {
        // first convert user input into JSON using mapper
        Account account = this.mapper.readValue(ctx.body(), Account.class);

        // check to see that username was inputed
        if (account.getUsername() == "") {
            ctx.status(400);
            return;
        }

        // check to see that password is at least 4 characters long
        if (account.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }

        // send the information to service
        Account addedAccount = this.accountService.insertAccount(account);

        if (addedAccount == null) {
            ctx.status(400);
            return;
        }

        ctx.json(this.mapper.writeValueAsString(addedAccount)).status(200);
    }

    public void loginAccountHandler(Context ctx) throws JsonProcessingException {        
        Account account = this.mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = this.accountService.verifyAccountByUserName(account);

        if (verifiedAccount == null) {
            ctx.status(401);
            return;
        }

        ctx.json(this.mapper.writeValueAsString(verifiedAccount)).status(200);
    }

    public void insertMessageHandler(Context ctx) throws JsonProcessingException {
        Message message = this.mapper.readValue(ctx.body(), Message.class);

        if (message.getMessage_text() == "" || message.getMessage_text().length() > 255) {
            ctx.status(400);
            return;
        }

        Account account = this.accountService.verifyAccountById(message.getPosted_by());

        if (account == null) {
            ctx.status(400);
            return;
        }

        Message addedMessage = this.messageService.insertMessage(message);

        if (addedMessage == null) {
            ctx.status(400);
            return;
        }
        
        ctx.json(this.mapper.writeValueAsString(addedMessage)).status(200);
    }

    public void getMessagesHandler(Context ctx) throws JsonProcessingException {
        ArrayList<Message> messages = this.messageService.getMessages();
        ctx.json(this.mapper.writeValueAsString(messages)).status(200);
    }

    public void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        String messageId = ctx.pathParam("message_id");
        Message message = this.messageService.getMessageById(messageId);
        
        if (message == null) {
            ctx.json("").status(200);
            return;
        } 
        
        ctx.json(this.mapper.writeValueAsString(message)).status(200);
    }

    public void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        System.out.println("its here");
        String messageId = ctx.pathParam("message_id");
        Message message = this.messageService.deleteMessageById(messageId);

        if (message == null) {
            ctx.json("").status(200);
            return;
        }

        ctx.json(mapper.writeValueAsString(message)).status(200);
    }

    public void getMessagesByUserHandler(Context ctx) throws JsonProcessingException {
        String accountId = ctx.pathParam("account_id");
        System.out.println("this is account id" + accountId);
        ArrayList<Message> messages = this.messageService.getMessagesByUser(accountId);
        
        ctx.json(this.mapper.writeValueAsString(messages)).status(200);
    }
}