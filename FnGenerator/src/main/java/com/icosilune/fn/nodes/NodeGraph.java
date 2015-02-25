/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.icosilune.fn.EvaluationContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ashmore
 */
public class NodeGraph {

  private List<AbstractNode> allNodes = new ArrayList<>();

  // add and remove
  // connect and disconnect.

  private List<AbstractNode> contextDependentNodes = new ArrayList<>();

  // ?? what is an output node, really?
  private List<AbstractNode> outputNodes = new ArrayList<>();

  // *******
  // Here's what we want to do:
  // the evaluation context updates, then the input nodes update, and propagate their values downward.

  // do not keep context, rather we evaluate the entire graph with a context
  //private EvaluationContext context;

  void evaluateGraph(EvaluationContext context) {}

  void onNodeUpdated(AbstractNode node) {
    // ???
  }
}
