/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.auto.value.AutoValue;
import com.icosilune.fn.FnType;

/**
 * Represents an input or output socket on a
 */
@AutoValue
public abstract class Socket {
  public enum SocketType {
    INPUT,
    OUTPUT,
  }

  public abstract String getName();
  public abstract FnType getType();
  public abstract SocketType getSocketType();

  public static Socket create(String name, FnType type, SocketType socketType) {
    return new AutoValue_Socket(name, type, socketType);
  }

  public boolean canConnectTo(Socket other) {
    Socket inputSocket;
    Socket outputSocket;

    if (getSocketType() == SocketType.INPUT && other.getSocketType() == SocketType.OUTPUT) {
      inputSocket = this;
      outputSocket = other;
    } else if(getSocketType() == SocketType.OUTPUT && other.getSocketType() == SocketType.INPUT) {
      inputSocket = other;
      outputSocket = this;
    } else {
      return false;
    }
    
    return inputSocket.getType().isAssignableFrom(outputSocket.getType());
  }
}
