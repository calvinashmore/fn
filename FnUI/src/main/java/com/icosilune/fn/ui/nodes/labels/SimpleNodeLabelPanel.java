/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes.labels;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.ui.nodes.NodePanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author ashmore
 */
public class SimpleNodeLabelPanel extends NodePanel {

  public SimpleNodeLabelPanel(AbstractNode node) {
    super(node);
    setBorder(new LineBorder(Color.DARK_GRAY, 2));
    add(new JLabel(node.getName()));
  }

}
