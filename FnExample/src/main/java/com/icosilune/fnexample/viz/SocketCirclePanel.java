/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.Socket;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author ashmore
 */
public class SocketCirclePanel extends JPanel {

  private final Socket socket;

  // ******** We can maybe look this up from the graph- ie, have a map<Socket, Connection>
  private boolean connected = false;

  public SocketCirclePanel(Socket socket) {
    this.socket = socket;
    setPreferredSize(new Dimension(10,10));
    setOpaque(false);
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }


  @Override
  public void paint(Graphics g) {

    g.setColor(Color.BLACK);

    g.drawOval(0, 0, 9, 9);

    if(!connected) {
      g.setColor(Color.GRAY);
    }

    g.fillOval(2, 2, 5, 5);
  }
}
