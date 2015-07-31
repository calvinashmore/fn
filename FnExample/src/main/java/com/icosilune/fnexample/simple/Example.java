/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.simple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.EvaluationContextFactory;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fn.ui.NodeGraphEvaluatorPanel;
import com.icosilune.fn.ui.nodes.NodeFactory;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author ashmore
 */
public class Example {

  private static class TimestampContextFactory implements EvaluationContextFactory {

    private static final class TimestampContext implements EvaluationContext {

      private final double timestampSeconds;

      public TimestampContext(double timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
      }

      @Override
      public Object getValue(String key) {
        if("timestamp".equals(key)) {
          return timestampSeconds;
        }
        return null;
      }
    }

    private final long initialTimeMillis;

    public TimestampContextFactory() {
      this.initialTimeMillis = System.currentTimeMillis();
    }

    @Override
    public EvaluationContext get() {
      long timestampDiff = System.currentTimeMillis() - initialTimeMillis;
      double timestampSeconds = ((double) timestampDiff) / 1000;
      return new TimestampContext(timestampSeconds);
    }
  }

  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("womp womp");

    NodeGraph graph = new NodeGraph(new TimestampContextFactory());

    List<AbstractFn> fns = ImmutableList.copyOf(Iterables.concat(
            ImmutableList.of(
                    new TimestampFn()),
            Fn_Index.INSTANCES.values()));


    // need to add constants & sinks
    List<NodeFactory.NodeKey> nodeKeys = new ArrayList<>();
    nodeKeys.add(NodeFactory.ConstantNodeKey.create(FnType.fromString("double"), 0.0));
    nodeKeys.add(NodeFactory.SinkNodeKey.create(FnType.fromString("double")));
    nodeKeys.addAll(NodeFactory.FnNodeKey.fromInstances(fns));
    NodeGraphEvaluatorPanel graphPanel = new NodeGraphEvaluatorPanel(graph, nodeKeys);

//    AbstractNode node1 = new FnNode(graph, new Fn_Multiply());
//    AbstractNode node2 = new ConstantNode(graph, FnType.fromString("double"), 10.0);
//    AbstractNode node3 = new ConstantNode(graph, FnType.fromString("double"), 10.0);
//    AbstractNode sink = new SinkNode(graph, FnType.fromString("double"));
//    graph.addNode(node1);
//    graph.addNode(node2);
//    graph.addNode(node3);
//    graph.addNode(sink);
//
//    graph.addConnection(node1, node2, "output", "x");
//    graph.addConnection(node1, node2, "output", "y");
//    graph.addConnection(sink, node1, "out", "in");

    graphPanel.setPreferredSize(new Dimension(500,500));

    frame.add(graphPanel);


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
