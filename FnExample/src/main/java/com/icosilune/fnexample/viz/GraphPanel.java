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
import java.awt.Graphics;
import java.awt.Graphics2D;
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

  public GraphPanel(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;

    for(AbstractNode node : nodeGraph.getNodes()) {
      addNode(node);
    }

    nodeGraph.addListener(this);
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
    NodePanel nodePanel = new NodePanel(node);
    add(nodePanel, JLayeredPane.DEFAULT_LAYER);
    nodePanel.setSize(nodePanel.getPreferredSize());
    nodes.put(node, nodePanel);
  }

  private void removeNode(AbstractNode node) {
    NodePanel nodePanel = nodes.remove(node);
  }

//  @Override
//  protected void paintChildren(Graphics g) {
//    Graphics2D g2 = (Graphics2D) g;
//
//    AffineTransform oldTransform = g2.getTransform();
//
//    g2.transform(childTransform);
//
//    super.paintChildren(g);
//    g2.setTransform(oldTransform);
//  }
}
