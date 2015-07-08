/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ashmore
 */
public class NewNodeMenu extends JPopupMenu {

  private JList list;
  private NodeFactory nodeFactory;

  public NewNodeMenu(NodeFactory nodeFactory) {
    //add(new JLabel("eep"));

    // what is one of those list views that is a drop down that lets you search by typing?
    // I want to be able to start typing something and have it show up reasonably

//    JButton button;
//    add(button = new JButton());
//    button.addActionListener(e -> {
//      setVisible(false);
//    });

    DefaultListModel listModel = new DefaultListModel();
    for (NodeFactoryImpl.NodeKey node: nodeFactory.getNodeKeys()) {
      listModel.addElement(node);
    }

    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    list.addListSelectionListener(e -> {
      System.out.println(e.getFirstIndex());
    });

    add(list);
  }
}
