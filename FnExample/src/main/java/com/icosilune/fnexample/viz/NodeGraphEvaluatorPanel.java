/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.NodeGraph;
import java.awt.BorderLayout;
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

  public NodeGraphEvaluatorPanel(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;

    graphPanel = new GraphPanel(nodeGraph);

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
    //nodeGraph...
    nodeGraph.step();
  }
}
