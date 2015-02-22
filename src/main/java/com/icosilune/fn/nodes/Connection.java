/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

/**
 *
 * @author ashmore
 */
@AutoValue
public abstract class Connection {
  public abstract AbstractNode getInputNode();
  public abstract AbstractNode getOutputNode();
  public abstract Socket getInputSocket();
  public abstract Socket getOutputSocket();

  public static Connection create(AbstractNode inNode, AbstractNode outNode, String inSocketName, String outSocketName) {

    Socket inSocket = inNode.getInputSockets().get(inSocketName);
    Socket outSocket = outNode.getOutputSockets().get(outSocketName);

    Preconditions.checkNotNull(inSocket, "node %s does not contain input socket %s", inNode, inSocketName);
    Preconditions.checkNotNull(outSocket, "node %s does not contain input socket %s", outNode, outSocketName);
    Preconditions.checkArgument(inSocket.getSocketType() == Socket.SocketType.INPUT);
    Preconditions.checkArgument(outSocket.getSocketType() == Socket.SocketType.OUTPUT);
    // this needs to be an "is assignable from" check.
    Preconditions.checkArgument(inSocket.getType().equals(outSocket.getType()));

    return new AutoValue_Connection(inNode, outNode, inSocket, outSocket);
  }
}
