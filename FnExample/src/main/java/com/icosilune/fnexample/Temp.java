/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample;

import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;

/**
 *
 * @author ashmore
 */
public class Temp {

  public void foo(Iterable<double[]> buh) {
  }

  public static void main(String args[]) {
    Sum_Fn sum = new Sum_Fn();

    NodeGraph graph = new NodeGraph();

    AbstractNode constant1 = new ConstantNode(graph, FnType.fromString("double"), 1.0);
    AbstractNode constant2 = new ConstantNode(graph, FnType.fromString("double"), 0.3);
    AbstractNode sumNode = new FnNode(graph, sum);

    sumNode.addInputConnection(constant1, "output", "x");
    sumNode.addInputConnection(constant2, "output", "y");

    constant1.evaluate(null);
    constant2.evaluate(null);
    sumNode.evaluate(null);

    Object output = sumNode.getOutput("out");

    System.out.println(output);
  }
}
