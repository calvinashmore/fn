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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

  private final JButton runButton;
  private final JButton stepButton;

  // non null if the graph is running continuously.
  private ScheduledExecutorService executor;

  public NodeGraphEvaluatorPanel(NodeGraph nodeGraph, Collection<? extends NodeFactory.NodeKey> nodeKeys) {
    this.nodeGraph = nodeGraph;

    NodeFactory nodeFactory = new NodeFactoryImpl(nodeGraph, nodeKeys);
    graphPanel = new GraphPanel(nodeGraph, nodeFactory);

    setLayout(new BorderLayout());
    add(graphPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

    stepButton = new JButton("step");
    stepButton.addActionListener(ae -> {step();});
    buttonPanel.add(stepButton);

    runButton = new JButton("run");
    runButton.addActionListener(ae -> {toggleRun();});
    buttonPanel.add(runButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void step() {
    nodeGraph.step();
  }

  private void toggleRun() {
    if(executor != null) {
      executor.shutdown();
      runButton.setText("run");
      stepButton.setEnabled(true);
      executor = null;
    } else {
      executor = Executors.newSingleThreadScheduledExecutor();
      executor.scheduleAtFixedRate(()->{step();}, 0, 10, TimeUnit.MILLISECONDS);
      runButton.setText("stop");
      stepButton.setEnabled(false);
    }
  }
}
