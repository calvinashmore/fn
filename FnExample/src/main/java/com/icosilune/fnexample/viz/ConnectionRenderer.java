/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.Connection;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author ashmore
 */
public class ConnectionRenderer {

  // can do straight lines
  // would love to have fancy curved lines

  void renderConnection(Graphics2D g, Connection connection, NodePanel inputNodePanel, NodePanel outputNodePanel) {

    SocketPanel inputSocketPanel = inputNodePanel.getInputSocket(connection.getInputSocket().getName());
    SocketPanel outputSocketPanel = outputNodePanel.getOutputSocket(connection.getOutputSocket().getName());

    g.setColor(Color.red);
    Point2D inputCircleCenter = inputSocketPanel.getCircleCenter();
    Point2D outputCircleCenter = outputSocketPanel.getCircleCenter();
    g.drawLine((int) inputCircleCenter.getX(), (int) inputCircleCenter.getY(),
            (int) outputCircleCenter.getX(), (int) outputCircleCenter.getY());
  }
}
