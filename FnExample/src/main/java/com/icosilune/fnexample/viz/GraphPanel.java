/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fnexample.viz.nodes.CircleMouseListener;
import com.icosilune.fnexample.viz.nodes.NodeContainerPanel;
import com.icosilune.fnexample.viz.nodes.NewNodeMenu;
import com.icosilune.fnexample.viz.nodes.SocketCirclePanel;
import com.google.common.base.Preconditions;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.Connection;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.NodeGraph.NodeGraphListener;
import com.icosilune.fn.nodes.NodeGraph.NodeChangeType;
import com.icosilune.fn.nodes.NodeGraph.ConnectionChangeType;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fnexample.viz.nodes.NodeFactoryFactory;
import com.icosilune.fnexample.viz.nodes.NodePanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
  private final NodeFactoryFactory nodeFactoryFactory;

  private int mouseX;
  private int mouseY;

  public GraphPanel(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;
    this.connectionRenderer = new ConnectionRenderer();
    this.circleMouseListener = new CircleMouseListener(nodeGraph);
    this.newNodeMenu = new NewNodeMenu();
    this.nodeFactoryFactory = new NodeFactoryFactory(nodeGraph, circleMouseListener);

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
      addNode(node, nodeFactoryFactory.createPanelForNode(node));
    }
  }

  /**
   * publicly visible way to add node panels.
   */
  public void addNode(AbstractNode node, NodePanel nodePanel) {
    Preconditions.checkArgument(nodePanel.getNode() == node);
//    Preconditions.checkArgument(nodePanel.getGraphPanel() == this);
//    add(nodePanel, JLayeredPane.DEFAULT_LAYER);
//    nodePanel.setSize(nodePanel.getPreferredSize());
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
