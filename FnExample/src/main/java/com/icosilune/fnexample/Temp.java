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
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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

    AbstractNode constant1 = new ConstantNode(graph, FnType.fromString("java.lang.Double"), 1.0);
    AbstractNode constant2 = new ConstantNode(graph, FnType.fromString("java.lang.Double"), 0.3);
    AbstractNode sumNode = new FnNode(graph, sum);

    sumNode.addInputConnection(constant1, "output", "x");
    sumNode.addInputConnection(constant2, "output", "y");

    constant1.evaluate(null);
    constant2.evaluate(null);
    sumNode.evaluate(null);

    Object output = sumNode.getOutput("out");

    System.out.println(output);
  }

//  public static class Foo {
//    public int doThing(List<String> inputs, int foo) {
//      return inputs.size();
//    }
//  }
//
//  public static void main(String args[]) {
//
//    Method doThing = null;
//    for(Method method : Foo.class.getMethods()) {
//      if(method.getName().equals("doThing")) {
//        doThing = method;
//        break;
//      }
//    }
//
////    doThing.getTypeParameters()
//
//    for(Parameter param : doThing.getParameters()) {
//      System.out.println("PARAM");
//      System.out.println(param);
//      Type type = param.getParameterizedType();
//      System.out.println(type);
//      System.out.println(type.getClass());
////      param.
//
//      if(type instanceof Class) {
//        Class type1 = (Class) type;
//        System.out.println("***" + type1.getName());
//      }
//
//      if(type instanceof ParameterizedType) {
//        ParameterizedType type1 = (ParameterizedType) type;
//        System.out.println("***");
//        System.out.println(type1.getOwnerType());
//        System.out.println(type1.getRawType());
//        for(Type typeArg : type1.getActualTypeArguments()) {
//          System.out.println("arg: "+typeArg);
//        }
//      }
//    }
//
//
////    System.out.println(doThing);
//
//  }
}
