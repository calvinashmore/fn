/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.simple;

import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fn.ui.NodeGraphEvaluatorPanel;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author ashmore
 */
public class Example {

  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("womp womp");

    NodeGraph graph = new NodeGraph();
    NodeGraphEvaluatorPanel graphPanel = new NodeGraphEvaluatorPanel(graph, Fn_Index.INSTANCES.values());

    AbstractNode node1 = new FnNode(graph, new Fn_Multiply());
    AbstractNode node2 = new ConstantNode(graph, FnType.fromString("double"), 10.0);
    AbstractNode node3 = new ConstantNode(graph, FnType.fromString("double"), 10.0);
    AbstractNode sink = new SinkNode(graph, FnType.fromString("double"));
    graph.addNode(node1);
    graph.addNode(node2);
    graph.addNode(node3);
    graph.addNode(sink);

    graph.addConnection(node1, node2, "output", "x");
    graph.addConnection(node1, node2, "output", "y");
    graph.addConnection(sink, node1, "out", "in");

    graphPanel.setPreferredSize(new Dimension(500,500));

    frame.add(graphPanel);


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
