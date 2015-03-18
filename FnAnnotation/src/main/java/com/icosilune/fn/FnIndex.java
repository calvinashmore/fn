/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ashmore
 */
public class FnIndex {
  private final ImmutableClassToInstanceMap<AbstractFn> instances;

  private FnIndex(ImmutableClassToInstanceMap<AbstractFn> instances) {
    this.instances = instances;
  }

  public Set<AbstractFn> getByReturnType(FnType returnType) {
    return instances.values().stream()
        .filter(fn -> fn.getOutputTypes().values().stream().anyMatch(returnType::isAssignableFrom))
        .collect(Collectors.toSet());
  }

  public Set<AbstractFn> searchByClassName(String name) {
    return instances.values().stream()
        .filter(fn -> fn.getClass().getCanonicalName().contains(name))
        .collect(Collectors.toSet());
  }

  public static class Builder {
    private static final String INDEX_CLASS_NAME = "Fn_Index";
    private static final String INSTANCES_NAME = "INSTANCES";
    private final ClassToInstanceMap<AbstractFn> allFns = MutableClassToInstanceMap.create();

    public FnIndex build() {
      return new FnIndex(ImmutableClassToInstanceMap.copyOf(allFns));
    }

    public Builder addPackage(String packageName) throws IllegalArgumentException {
      try {
        Class<?> packageIndexClass = Class.forName(packageName+"."+INDEX_CLASS_NAME);
        Field instancesField = packageIndexClass.getDeclaredField(INSTANCES_NAME);
        ImmutableClassToInstanceMap<AbstractFn> instances = (ImmutableClassToInstanceMap<AbstractFn>) instancesField.get(null); // static access
        allFns.putAll(instances);
        return this;
      } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | SecurityException ex ) {
        Logger.getLogger(FnIndex.class.getName()).log(Level.SEVERE, null, ex);
        throw new IllegalArgumentException("Could not load fn package "+packageName, ex);
      }
    }
  }
}
