/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui;

import com.icosilune.fn.ui.nodes.SocketPanel;
import com.icosilune.fn.ui.nodes.NodeContainerPanel;
import com.icosilune.fn.nodes.Connection;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author ashmore
 */
public class ConnectionRenderer {

  private final ConnectionLineRenderer connectionLineRenderer = new ConnectionLineRenderer();

  void renderConnection(Graphics2D g, Connection connection, NodeContainerPanel inputNodePanel, NodeContainerPanel outputNodePanel) {

    SocketPanel inputSocketPanel = inputNodePanel.getInputSocket(connection.getInputSocket().getName());
    SocketPanel outputSocketPanel = outputNodePanel.getOutputSocket(connection.getOutputSocket().getName());

    Point2D inputCircleCenter = inputSocketPanel.getCircleCenter();
    Point2D outputCircleCenter = outputSocketPanel.getCircleCenter();
    connectionLineRenderer.renderLine(g, inputCircleCenter, outputCircleCenter);
  }
}
