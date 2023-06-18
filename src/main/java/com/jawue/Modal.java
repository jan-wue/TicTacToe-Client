package com.jawue;

import codedraw.CodeDraw;
import codedraw.Event;
import com.jawue.milkyway.Button;
import com.jawue.milkyway.GuiObject;
import com.jawue.milkyway.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Modal extends GuiObject {
 private List<Button> options = new ArrayList<>();
 private Label label;


  public Modal(List<Button> options, Double width, Double height, String text, CodeDraw codeDraw) {
    this.options = options;
    this.setWidth(width);
    this.setHeight(height);
    double xModal = codeDraw.getCanvasPositionX() + 50;
    double yModal = codeDraw.getCanvasPositionY() + 50;
    this.setX(xModal);
    this.setY(yModal);
    this.label = new Label(xModal, yModal, this.getWidth(), 50.0, text);
    int index = 0;
    int lastIndex = index;
    double buttonX = xModal + 20;
    for(Button button : options) {
      button.setY(yModal + 100);
      button.setWidth(100.00);
      button.setHeight(80.0);
      if(index != lastIndex) {
        buttonX += button.getWidth() + 20.0;
      }
      button.setX(buttonX);
      lastIndex = index;
      index++;
    }
  }
  @Override
  public void draw(CodeDraw cd) {
    cd.setColor(Color.BLACK);
    cd.drawRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    this.label.draw(cd);
    for(Button button : options) {
      button.draw(cd);
    }

  }

  @Override
  public void handleEvent(Integer mouseX, Integer mouseY, Event event) {
    for(Button button : options) {
      button.handleEvent(mouseX, mouseY, event);
    }
  }

}
