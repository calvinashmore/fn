/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author ashmore
 */
public class HorizontalSliderLabelPanel extends NodeLabelPanel {

  private static final int MIN_SLIDER_VALUE = 0;
  private static final int MAX_SLIDER_VALUE = 100;
  private final double minValue;
  private final double maxValue;

  public HorizontalSliderLabelPanel(ConstantNode node, double minValue, double maxValue) {
    super(node);
    this.minValue = minValue;
    this.maxValue = maxValue;

    JSlider slider = new JSlider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE);
    add(slider);

    slider.addChangeListener(e -> valueChanged(slider.getValue()));
  }

  private void valueChanged(int sliderValue) {
    float ratio = ((float)sliderValue - MIN_SLIDER_VALUE) / (MAX_SLIDER_VALUE - MIN_SLIDER_VALUE);
    double newValue = ratio * (maxValue - minValue) + minValue;

    ConstantNode node = (ConstantNode) getNode();
    node.setValue(newValue);
  }
}
