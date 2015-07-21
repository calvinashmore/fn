/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.icosilune.fn.nodes.AbstractNode;
import java.awt.Component;
import java.awt.Point;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ashmore
 */
public class NewNodeMenu extends JPopupMenu {

  public interface NewNodeHook {
    public void newNode(AbstractNode node, NodePanel nodePanel, Point location);
  }

  private Point lastPoint;

  @Override
  public void show(Component invoker, int x, int y) {
    super.show(invoker, x, y);
    lastPoint = new Point(x, y);
  }

  public NewNodeMenu(NodeFactory nodeFactory, NewNodeHook newNodeHook) {

    // what is one of those list views that is a drop down that lets you search by typing?
    // I want to be able to start typing something and have it show up reasonably
    DefaultListModel listModel = new DefaultListModel();
    for (NodeFactoryImpl.NodeKey node: nodeFactory.getNodeKeys()) {
      listModel.addElement(node);
    }

    JList list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    add(list);

    JButton button;
    add(button = new JButton("add"));
    button.addActionListener(e -> {
      if(list.getSelectedValue() != null) {
        NodeFactoryImpl.NodeKey nodeKey = (NodeFactoryImpl.NodeKey) list.getSelectedValue();
        NodeFactory.NodeAndPanel newNode = nodeFactory.createNode(nodeKey);
        newNodeHook.newNode(newNode.getNode(), newNode.getNodePanel(), lastPoint);
        setVisible(false);
      }
    });
  }
}
