package Controller;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.jetty.websocket.core.internal.messages.MessageReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler); // #1
        // app.post("/login", this::postLoginHandler); // #2
        app.post("/messages", this::postMessageHandler); // #3
        app.get("/messages", this::getAllMessagesHandler); // #4
        // app.get("/messages/{message_id}", this::getMessageHandler);// #5
        // app.delete("/messages/{message_id}", this::deleteMessageHandler)// #6
        // app.patch("/messages/{message_id}", this::patchMessageHandler);// #7
        // app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);// #8
        return app;
    }

    // /**
    //  * This is an example handler for an example endpoint.
    //  * @param context The Javalin Context object manages information about both the HTTP request and response.
    //  */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    // #1
    private void postAccountHandler(Context ctx) throws JsonProcessingException {

    }

    // #3
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    // #4
    private void getAllMessagesHandler(Context ctx) throws SQLException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }


}