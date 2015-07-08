/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui;

import com.icosilune.fn.ui.nodes.CircleMouseListener;
import com.icosilune.fn.ui.nodes.NodeContainerPanel;
import com.icosilune.fn.ui.nodes.NewNodeMenu;
import com.icosilune.fn.ui.nodes.SocketCirclePanel;
import com.google.common.base.Preconditions;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.Connection;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.NodeGraph.NodeGraphListener;
import com.icosilune.fn.nodes.NodeGraph.NodeChangeType;
import com.icosilune.fn.nodes.NodeGraph.ConnectionChangeType;
import com.icosilune.fn.ui.nodes.NodeFactory;
import com.icosilune.fn.ui.nodes.NodeFactoryImpl;
import com.icosilune.fn.ui.nodes.NodePanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLayeredPane;

/**
 *
 * @author ashmore
 */
public class GraphPanel extends JLayeredPane implements NodeGraphListener {

  private final NodeGraph nodeGraph;
  private final Map<AbstractNode, NodeContainerPanel> nodes = new HashMap<>();
  private final ConnectionRenderer connectionRenderer;
  private final CircleMouseListener circleMouseListener;
  private final NewNodeMenu newNodeMenu;
  private final NodeFactory nodeFactory;

  private int mouseX;
  private int mouseY;

  public GraphPanel(NodeGraph nodeGraph, NodeFactory nodeFactory) {
    this.nodeGraph = nodeGraph;
    this.connectionRenderer = new ConnectionRenderer();
    this.circleMouseListener = new CircleMouseListener(nodeGraph);
    this.nodeFactory = nodeFactory;
    this.newNodeMenu = new NewNodeMenu(nodeFactory);

    for(AbstractNode node : nodeGraph.getNodes()) {
      addNode(node);
    }

    nodeGraph.addListener(this);
    BackgroundMouseListener backgroundMouseListener = new BackgroundMouseListener();
    addMouseMotionListener(backgroundMouseListener);
    addMouseListener(backgroundMouseListener);
  }

  public CircleMouseListener getCircleMouseListener() {
    return circleMouseListener;
  }

  @Override
  public void nodeChanged(NodeGraph graph, AbstractNode node, NodeChangeType change) {
    if(change == NodeChangeType.ADDED) {
      addNode(node);
    } else if(change == NodeChangeType.REMOVED) {
      removeNode(node);
    }
  }

  @Override
  public void connectionChanged(NodeGraph graph, Connection node, ConnectionChangeType change) {
  }

  private void addNode(AbstractNode node) {
    if(nodes.get(node) == null) {
      addNode(node, nodeFactory.createPanelForNode(node));
    }
  }

  /**
   * publicly visible way to add node panels.
   */
  public void addNode(AbstractNode node, NodePanel nodePanel) {
    Preconditions.checkArgument(nodePanel.getNode() == node);
    NodeContainerPanel nodeContainerPanel = new NodeContainerPanel(circleMouseListener, node, nodePanel);
    add(nodeContainerPanel, JLayeredPane.DEFAULT_LAYER);
    nodeContainerPanel.setSize(nodeContainerPanel.getPreferredSize());
    nodes.put(node,  nodeContainerPanel);
  }

  private void removeNode(AbstractNode node) {
    NodeContainerPanel nodePanel = nodes.remove(node);
    remove(nodePanel);
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    // draw connections underneath
    for(AbstractNode node : nodes.keySet()) {
      for(Connection connection : node.getInputConnections().values()) {
        connectionRenderer.renderConnection(g2, connection, nodes.get(node), nodes.get(connection.getOutputNode()));
      }
    }

    if (circleMouseListener.getSelectedCircle() != null) {
      SocketCirclePanel selectedCircle = circleMouseListener.getSelectedCircle();

      Point graphPanelLocation = getLocationOnScreen();
      Point circleLocation = selectedCircle.getLocationOnScreen();
      g.drawLine((int)(circleLocation.getX() - graphPanelLocation.getX()),
              (int)(circleLocation.getY() - graphPanelLocation.getY()), mouseX, mouseY);
    }

    super.paint(g);
  }

  private class BackgroundMouseListener extends MouseAdapter {
    @Override
    public void mouseMoved(MouseEvent e) {
      mouseX = e.getX();
      mouseY = e.getY();
      repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      if(circleMouseListener.getSelectedCircle() != null) {
        circleMouseListener.deselect();
      }

      if(e.getClickCount() == 2) {
        newNodeMenu.show(GraphPanel.this, e.getX(), e.getY());
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
  }
}
