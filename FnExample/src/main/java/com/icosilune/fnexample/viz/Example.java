/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fnexample.simple.Fn_Multiply;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author ashmore
 */
public class Example {

  public static void main(String args[]) {
    JFrame frame = new JFrame("womp womp");

    NodeGraph graph = new NodeGraph();
    NodeGraphEvaluatorPanel graphPanel = new NodeGraphEvaluatorPanel(graph);

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


//    FnIndex index = new FnIndex.Builder().addPackage("com.icosilune.fnexample.simple").build();
//    AbstractFn sum = Iterables.getOnlyElement(index.searchByClassName("Sum"));
//    graph.addNode(new FnNode(graph, sum));
    //graph.addNode(new ConstantNode(graph, FnType.fromString("String"), "oops"));
//    graph.addNode(new FnNode(graph, new Fn_Multiply()));
  }
}
