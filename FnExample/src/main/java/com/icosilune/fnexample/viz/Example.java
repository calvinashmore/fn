/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
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
    graph.addNode(new FnNode(graph, new Fn_Multiply()));
    graph.addNode(new ConstantNode(graph, FnType.fromString("int"), 10));

    GraphPanel graphPanel = new GraphPanel(graph);

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
