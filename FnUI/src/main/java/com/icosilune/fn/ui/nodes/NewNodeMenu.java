/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.google.auto.value.AutoValue;
import com.icosilune.fn.AbstractFn;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ashmore
 */
public class NewNodeMenu extends JPopupMenu {

  public interface NodeKey {
  }

  @AutoValue
  public static abstract class FnNodeKey implements NodeKey {
    public abstract AbstractFn getFn();
    public static FnNodeKey create(AbstractFn fn) {
      return new AutoValue_NewNodeMenu_FnNodeKey(fn);
    }

    @Override
    public String toString() {
      return "Fn:"+getFn();
    }

    public static List<FnNodeKey> fromInstances(Collection<AbstractFn> fns) {
      return fns.stream().map(FnNodeKey::create).collect(Collectors.toList());
    }
  }

  private JList list;
  private NodeFactoryFactory nodeFactoryFactory;

  public NewNodeMenu(Iterable<? extends NodeKey> nodeKeys, NodeFactoryFactory nodeFactoryFactory) {
    //add(new JLabel("eep"));

    // what is one of those list views that is a drop down that lets you search by typing?
    // I want to be able to start typing something and have it show up reasonably

//    JButton button;
//    add(button = new JButton());
//    button.addActionListener(e -> {
//      setVisible(false);
//    });

    DefaultListModel listModel = new DefaultListModel();
    for (NodeKey node: nodeKeys) {
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
