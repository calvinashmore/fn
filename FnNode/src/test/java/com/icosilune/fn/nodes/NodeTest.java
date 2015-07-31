/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.Iterables;
import com.google.common.truth.Truth;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.Fn;
import com.icosilune.fn.FnIndex;
import com.icosilune.fn.FnType;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author ashmore
 */
@RunWith(JUnit4.class)
public class NodeTest {

  @Fn
  public static abstract class Sum extends AbstractFn {

    public double evaluate(double x, double y) {
      return x + y;
    }
  }

  private static final class FakeContextFactory implements EvaluationContextFactory {
    private static final EvaluationContext FAKE_EVALUATION_CONTEXT = new EvaluationContext() {
      @Override
      public Object getValue(String key) {
        return null;
      }
    };

    @Override
    public EvaluationContext get() {
      return FAKE_EVALUATION_CONTEXT;
    }
  }

  @Test
  public void testSum() {
    double in1 = 1.0;
    double in2 = 0.3;

    Fn_NodeTest_Sum sum = new Fn_NodeTest_Sum();
    NodeGraph graph = new NodeGraph(new FakeContextFactory());

    AbstractNode constant1 = new ConstantNode(graph, FnType.fromString("double"), in1);
    AbstractNode constant2 = new ConstantNode(graph, FnType.fromString("double"), in2);
    AbstractNode sumNode = new FnNode(graph, sum);

    sumNode.addInputConnection(constant1, "output", "x");
    sumNode.addInputConnection(constant2, "output", "y");

    constant1.evaluate(null);
    constant2.evaluate(null);
    sumNode.evaluate(null);

    Object output = sumNode.getOutput("out");
    Truth.assertThat(output).isEqualTo(in1 + in2);
  }

  @Test
  public void testIndex() {
    FnIndex index = new FnIndex.Builder()
            .addPackage("com.icosilune.fn.nodes")
            .build();
    // Note: if we add additional @Fn's to this package that contain "Sum", this test will break.
    Set<AbstractFn> searchByClassName = index.searchByClassName("Sum");
    Truth.assertThat(searchByClassName).hasSize(1);
    Truth.assertThat(Iterables.getOnlyElement(searchByClassName)).isInstanceOf(Sum.class);
  }
}
