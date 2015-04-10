/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Visual element for a node
 */
public class NodePanel extends JPanel {

  private final AbstractNode node;

  private final Map<String, SocketPanel> inputSocketPanels = new HashMap<>();
  private final Map<String, SocketPanel> outputSocketPanels = new HashMap<>();

  public NodePanel(AbstractNode node) {
    this.node = node;

    setOpaque(false);

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    // input sockets
    JPanel inputSocketsPanel = new JPanel();
    inputSocketsPanel.setOpaque(false);
    add(inputSocketsPanel);
    inputSocketsPanel.setLayout(new BoxLayout(inputSocketsPanel, BoxLayout.Y_AXIS));
    for(Socket inputSocket : node.getInputSockets().values()) {
      SocketPanel socketPanel = new SocketPanel(inputSocket);
      inputSocketPanels.put(inputSocket.getName(), socketPanel);
      inputSocketsPanel.add(socketPanel);
    }

    // node itself
    // note that moving the node label moves the whole NodePanel
    NodeLabelPanel nodeLabelPanel = new NodeLabelPanel(node);
    add(nodeLabelPanel);
    DragHandler dragHandler = new DragHandler(this);
    nodeLabelPanel.addMouseListener(dragHandler);
    nodeLabelPanel.addMouseMotionListener(dragHandler);

    // output sockets
    JPanel outputSocketsPanel = new JPanel();
    outputSocketsPanel.setOpaque(false);
    add(outputSocketsPanel);
    outputSocketsPanel.setLayout(new BoxLayout(outputSocketsPanel, BoxLayout.Y_AXIS));
    for(Socket outputSocket : node.getOutputSockets().values()) {
      SocketPanel socketPanel = new SocketPanel(outputSocket);
      outputSocketPanels.put(outputSocket.getName(), socketPanel);
      outputSocketsPanel.add(socketPanel);
    }
  }

  public SocketPanel getInputSocket(String name) {
    return inputSocketPanels.get(name);
  }

  public SocketPanel getOutputSocket(String name) {
    return outputSocketPanels.get(name);
  }
}