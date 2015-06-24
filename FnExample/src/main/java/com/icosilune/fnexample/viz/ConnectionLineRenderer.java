/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author ashmore
 */
public class ConnectionLineRenderer {

  public void renderLine(Graphics2D g, Point2D in, Point2D out) {

    g.setColor(Color.red);
    // TODO: at some point in the future, draw a fancy curved line
    g.drawLine((int) in.getX(), (int) in.getY(), (int) out.getX(), (int) out.getY());
  }
}
