/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.Socket;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ashmore
 */
public class SocketPanel extends JPanel {

  private final Socket socket;

  public SocketPanel(Socket socket) {
    this.socket = socket;

    // depending on input or output, add the label to the left or right

    add(new JLabel(socket.getName()));

    add(new SocketCirclePanel(socket));
    setOpaque(false);
  }


}
