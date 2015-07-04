/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz.nodes;

import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.Socket;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener for mouse events on circle buttons, for creating or removing connections.
 */
public class CircleMouseListener extends MouseAdapter {

  private final NodeGraph nodeGraph;

  //private boolean hasFocus = false;
  private SocketCirclePanel selectedCircle;

  public CircleMouseListener(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    SocketCirclePanel circle = (SocketCirclePanel) e.getComponent();


    if (circle == null) {
      selectedCircle = null;
    }

    //System.out.println("clicked: "+circle.getSocket());

    // logical conditions
    // 1) do we have a connection socket selected now?

    if(selectedCircle == null) {
      selectedCircle = circle;
      // steal focus; if nothing is selected later, then abandon connection
    } else if(selectedCircle.getSocket().getSocket().canConnectTo(circle.getSocket().getSocket())) {
      // connect
      nodeGraph.addConnection(selectedCircle.getSocket(), circle.getSocket());
      selectedCircle = null;
    } else {
      // we can't connect it, don't do anything
    }
  }

  public SocketCirclePanel getSelectedCircle() {
    return selectedCircle;
  }

  public void deselect() {
    selectedCircle = null;
  }
}
