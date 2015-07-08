/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui;

import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.ui.nodes.NodeFactory;
import com.icosilune.fn.ui.nodes.NodeFactoryImpl;
import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ashmore
 */
public class NodeGraphEvaluatorPanel extends JPanel {

  private final GraphPanel graphPanel;
  private final NodeGraph nodeGraph;

  public NodeGraphEvaluatorPanel(NodeGraph nodeGraph, Collection<AbstractFn> fns) {
    this.nodeGraph = nodeGraph;

    NodeFactory nodeFactory = new NodeFactoryImpl(nodeGraph, NodeFactory.FnNodeKey.fromInstances(fns));
    graphPanel = new GraphPanel(nodeGraph, nodeFactory);

    setLayout(new BorderLayout());
    add(graphPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    JButton stepButton = new JButton("step");
    stepButton.addActionListener(ae -> {step();});
    buttonPanel.add(stepButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void step() {
    nodeGraph.step();
  }
}
