/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.Connection;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.NodeGraph.NodeGraphListener;
import com.icosilune.fn.nodes.NodeGraph.NodeChangeType;
import com.icosilune.fn.nodes.NodeGraph.ConnectionChangeType;
import com.icosilune.fn.nodes.SinkNode;
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
  private final Map<AbstractNode, NodePanel> nodes = new HashMap<>();
  private final ConnectionRenderer connectionRenderer;
  private final CircleMouseListener circleMouseListener;

  private int mouseX;
  private int mouseY;

  public GraphPanel(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;
    this.connectionRenderer = new ConnectionRenderer();
    this.circleMouseListener = new CircleMouseListener(nodeGraph);

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
    NodePanel nodePanel = new NodePanel(this, node);
    add(nodePanel, JLayeredPane.DEFAULT_LAYER);
    nodePanel.setSize(nodePanel.getPreferredSize());
    nodes.put(node, nodePanel);
  }

  private void removeNode(AbstractNode node) {
    NodePanel nodePanel = nodes.remove(node);
    // ??
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
    }
  }
}
