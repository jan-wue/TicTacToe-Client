package com.jawue.shared.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerMoveMessage.class, name = "playerMoveMessage" ),
        @JsonSubTypes.Type(value = RequestMoveMessage.class, name = "requestMoveMessage" ),
        @JsonSubTypes.Type(value = MoveResultMessage.class, name = "moveResultMessage" ),
        @JsonSubTypes.Type(value = ConnectMessage.class, name = "connectMessage" ),
        @JsonSubTypes.Type(value = GameResultMessage.class, name = "gameResultMessage" ),
        @JsonSubTypes.Type(value = WaitForOtherPlayerMessage.class, name = "waitForOtherPlayerMessage" ),
        @JsonSubTypes.Type(value = PlayAgainMessage.class, name = "playAgainstMessage" ),
        @JsonSubTypes.Type(value = GameStartsMessage.class, name = "gameStartsMessage" ),
        @JsonSubTypes.Type(value = GameFinishedMessage.class, name = "gameFinishedMessage" ),
        @JsonSubTypes.Type(value = NewGameStartsMessage.class, name = "newGameStartsMessage" ),
})

public abstract class  Message {

}
