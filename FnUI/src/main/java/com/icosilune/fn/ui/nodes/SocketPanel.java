/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.Socket;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ashmore
 */
public class SocketPanel extends JPanel {

  private final Socket socket;
  private final SocketCirclePanel socketCirclePanel;

  public SocketPanel(CircleMouseListener circleListener, AbstractNode node, Socket socket) {
    this.socket = socket;

    // depending on input or output, add the label to the left or right

    add(new JLabel(socket.getName()));

    socketCirclePanel = new SocketCirclePanel(circleListener, NodeGraph.NodeAndSocket.create(node, socket));
    add(socketCirclePanel);
    setOpaque(false);
  }

  /**
   * Gets the circle center with respect to the graph that contains the node.
   * Note that this involves going several layers deep.
   */
  public Point2D getCircleCenter() {

    Point2D circleLocation = socketCirclePanel.getLocation();
    Point2D location = getLocation();
    Point2D parentLocation = getParent().getLocation();
    Point2D parentParentLocation = getParent().getParent().getLocation();

    return new Point2D.Double(
            circleLocation.getX() + location.getX() + parentLocation.getX() + parentParentLocation.getX() + socketCirclePanel.getWidth()/2,
            circleLocation.getY() + location.getY() + parentLocation.getY() + parentParentLocation.getY() + socketCirclePanel.getHeight()/2);
  }

}
