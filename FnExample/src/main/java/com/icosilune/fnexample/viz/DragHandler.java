/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *
 * @author ashmore
 */
public class DragHandler extends MouseAdapter {
  private final JComponent component;
  private int lastX;
  private int lastY;

  public DragHandler(JComponent component) {
    this.component = component;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    lastX = e.getX();
    lastY = e.getY();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    int dx = e.getX() - lastX;
    int dy = e.getY() - lastY;

    Point currentLocation = component.getLocation();
    component.setLocation(currentLocation.x + dx, currentLocation.y + dy);
  }

}
