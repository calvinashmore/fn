/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.NodeGraph;
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
    graph.addNode(new ConstantNode(graph, FnType.fromString("int"), 10));

    GraphPanel graphPanel = new GraphPanel(graph);

    graphPanel.setPreferredSize(new Dimension(500,500));

    frame.add(graphPanel);


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);



    graph.addNode(new ConstantNode(graph, FnType.fromString("String"), "oops"));
  }
}
