/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz.nodes;

import com.google.auto.value.AutoValue;
import com.icosilune.fn.nodes.AbstractNode;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ashmore
 */
public class NewNodeMenu extends JPopupMenu {

  @AutoValue
  public abstract static class NodeKey {

  }

  private JList list;

  public NewNodeMenu() {
    //add(new JLabel("eep"));

    // what is one of those list views that is a drop down that lets you search by typing?
    // I want to be able to start typing something and have it show up reasonably

//    JButton button;
//    add(button = new JButton());
//    button.addActionListener(e -> {
//      setVisible(false);
//    });

    DefaultListModel listModel = new DefaultListModel();
    listModel.addElement("hello");
    listModel.addElement("wello");

    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    list.addListSelectionListener(e -> {
      System.out.println(e.getFirstIndex());
    });

    add(list);

  }

}
