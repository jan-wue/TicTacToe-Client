package com.jawue;


import java.net.URI;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.shared.message.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class TicTacToeClient extends WebSocketClient {
  public BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
  public TicTacToeClient(URI serverUri, Draft draft) {
    super(serverUri, draft);
  }

  public ObjectMapper mapper = new ObjectMapper();
  public TicTacToeClient(URI serverURI) {
    super(serverURI);
  }


  public TicTacToeClient(URI serverUri, Map<String, String> httpHeaders) {
    super(serverUri, httpHeaders);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    //send("Hello, it is me. Mario :)");
    //System.out.println("opened connection");
    // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
  }

  @Override
  public void onMessage(String message) {
    try {
      Message messageObject = mapper.readValue(message, Message.class);
      messages.add(messageObject);
    } catch(Exception error) {
      System.err.println(error.getMessage());
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    // The close codes are documented in class org.java_websocket.framing.CloseFrame
    System.out.println(
        "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
            + reason);
  }

  @Override
  public void onError(Exception ex) {
    ex.printStackTrace();
    // if the error is fatal then onClose will be called additionally
  }

  public Message nextMessageBlocking() {
    try {
      return messages.take();
    } catch (Exception error) {
      System.err.println(error.getMessage());
    }
    return null;
  }
  public void sendMessage(Message message) {
    try {
      this.send(mapper.writeValueAsString(message));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }





}
