/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.ImmutableMap;
import com.icosilune.fn.FnType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Note: These tests really should be for FnType.
 * @author ashmore
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {

  private static final String IN_SOCKET = "IN";
  private static final String OUT_SOCKET = "OUT";

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Mock private AbstractNode inputNode;
  @Mock private AbstractNode outputNode;

  @Test
  public void testConnection_happyPath() {
    configureNodes("String", "String");
    Connection.create(inputNode, outputNode, IN_SOCKET, OUT_SOCKET);
  }

  @Test
  public void testConnection_happyPath_primitives() {
    configureNodes("double", "double");
    Connection.create(inputNode, outputNode, IN_SOCKET, OUT_SOCKET);
  }

  @Test
  public void testConnection_unassignable() {
    configureNodes("int", "java.lang.String");
    thrown.expect(IllegalArgumentException.class);
    Connection.create(inputNode, outputNode, IN_SOCKET, OUT_SOCKET);
  }

  @Test
  public void testConnection_subclasses() {
    configureNodes("java.util.List", "java.util.ArrayList");
    Connection.create(inputNode, outputNode, IN_SOCKET, OUT_SOCKET);
  }

  @Test
  public void testConnection_subclasses_unassignable() {
    configureNodes("java.util.ArrayList", "java.util.List");
    thrown.expect(IllegalArgumentException.class);
    Connection.create(inputNode, outputNode, IN_SOCKET, OUT_SOCKET);
  }

  private void configureNodes(String inType, String outType) {
    Socket inSocket = Socket.create(IN_SOCKET, FnType.fromString(inType), Socket.SocketType.INPUT);
    Socket outSocket = Socket.create(OUT_SOCKET, FnType.fromString(outType), Socket.SocketType.OUTPUT);
    Mockito.when(inputNode.getInputSockets()).thenReturn(ImmutableMap.of(IN_SOCKET, inSocket));
    Mockito.when(outputNode.getOutputSockets()).thenReturn(ImmutableMap.of(OUT_SOCKET, outSocket));
  }
}
