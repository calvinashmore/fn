/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.icosilune.fn.Fn;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 *
 * @author ashmore
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.icosilune.fn.Fn")
public class FnProcessor extends AbstractProcessor {

  private Multimap<String, String> packageToGeneratedTypes = HashMultimap.create();

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Fn.class);

    for(TypeElement type : Iterables.filter(elements, TypeElement.class)) {
      processType(type);
    }

    if(roundEnv.processingOver()) {
      // generate indices.
      for(String packageName : packageToGeneratedTypes.keySet()) {
        generateIndex(packageName, packageToGeneratedTypes.get(packageName));
      }
    }

    // supposedly we should return false, but dunno?
    return true;
  }

  private void processType(TypeElement type) {

    String packageName = packageNameOf(type);
    writeSourceFile(packageName + "." + getGeneratedClassName(type), generateSource(type), type);
    packageToGeneratedTypes.put(packageName, getGeneratedClassName(type));
  }

  private String getGeneratedClassName(TypeElement type) {
    return type.getSimpleName()+"_Fn";
  }

  private String generateSource(TypeElement type) {
    // later what we're going to do is build something that maps the type (or perhaps just name)
    // of an AbstractFn to the class generated by this.

    Types typeUtils = processingEnv.getTypeUtils();
    if(!typeUtils.asElement(type.getSuperclass()).getSimpleName().contentEquals("AbstractFn")) {
      throw new ProcessorException("type "+type.getSimpleName()+" must extend AbstractFn.");
    }

    StringBuilder sb = new StringBuilder();
    sb.append("package "+packageNameOf(type)+";\n");
    sb.append("\n");
    // add all imports used by the underlying type?

    List<String> imports = ImmutableList.of(
        "com.google.common.collect.ImmutableMap",
        "java.util.Map",
        "com.icosilune.fn.FnType",
        "com.icosilune.fn.EvaluationContext");

    for(String s : imports) {
      sb.append("import "+s+";\n");
    }

    sb.append("\n");
    sb.append("public class "+getGeneratedClassName(type)+" extends "+type.getSimpleName()+" {\n");

    // implement getInputTypes
    ExecutableElement evaluateMethod = getMethod(type, "evaluate");
    if(evaluateMethod == null) {
      throw new ProcessorException("type "+type.getSimpleName()+" must have a method \"evaluate\"");
    }

    TypeMirror returnType = evaluateMethod.getReturnType();
    sb.append("  private static final String OUT1 = \"out\";\n");
    sb.append("  private static final ImmutableMap<String, FnType> OUTPUT_TYPES = \n");
    sb.append("      ImmutableMap.of(OUT1, FnType.fromString(\""+returnType+"\"));\n");
    sb.append("  @Override\n");
    sb.append("  public Map<String, FnType> getOutputTypes() {return OUTPUT_TYPES;}\n");
    sb.append("\n");

    sb.append("  private static final ImmutableMap<String, FnType> INPUT_TYPES = \n");
    sb.append("      ImmutableMap.<String, FnType>builder()\n");
    for(VariableElement var : evaluateMethod.getParameters()) {
      sb.append("      .put(\""+var.getSimpleName()+"\", FnType.fromString(\""+var.asType()+"\"))\n");
    }
    sb.append("      .build();\n");
    sb.append("  @Override\n");
    sb.append("  public Map<String, FnType> getInputTypes() {return INPUT_TYPES;}\n");
    sb.append("\n");
    sb.append("  @Override\n");
    sb.append("  public Map<String, Object> evaluateWrapper(\n");
    sb.append("          EvaluationContext context, Map<String, Object> args) {\n");

    for(int i=0;i<evaluateMethod.getParameters().size();i++) {
      VariableElement var = evaluateMethod.getParameters().get(i);
//    for(VariableElement var : evaluateMethod.getParameters()) {
      sb.append("    "+var.asType()+" in"+i+" = ("+var.asType()+") args.get(\""+var.getSimpleName()+"\");\n");
    }
    sb.append("    return ImmutableMap.of(OUT1, (Object) super.evaluate(");
    for(int i=0;i<evaluateMethod.getParameters().size();i++) {
      if(i>0) {
        sb.append(", ");
      }
      sb.append("in"+i);
    }
    sb.append("));\n");
    sb.append("  }\n");
    sb.append("}\n");

    return sb.toString();
  }

  @Nullable
  public ExecutableElement getMethod(TypeElement type, String methodName) {
    for(Element element : type.getEnclosedElements()) {
      if(element.getSimpleName().contentEquals(methodName) && element instanceof ExecutableElement) {
        return (ExecutableElement) element;
      }
    }
    return null;
  }

  static String packageNameOf(TypeElement type) {
    while (true) {
      Element enclosing = type.getEnclosingElement();
      if (enclosing instanceof PackageElement) {
        return ((PackageElement) enclosing).getQualifiedName().toString();
      }
      type = (TypeElement) enclosing;
    }
  }

  private void writeSourceFile(String className, String text, TypeElement... originatingTypes) {
    try {
      JavaFileObject sourceFile =
          processingEnv.getFiler().createSourceFile(className, originatingTypes);
      try (Writer writer = sourceFile.openWriter()) {
        writer.write(text);



        System.out.println("***** GENERATED "+className+".java");
        System.out.println(text);
        System.out.println();
      }
    } catch (IOException e) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
          "Could not write generated class " + className + ": " + e);
    }
  }

  private void generateIndex(String packageName, Collection<String> classes) {
    StringBuilder indexText = new StringBuilder();

    indexText.append("package "+packageName+";\n");
    indexText.append("\n");
    indexText.append("import com.google.common.collect.ImmutableClassToInstanceMap;\n");
    indexText.append("\n");
    indexText.append("public class Fn_Index {\n");
    indexText.append("  public static final ImmutableClassToInstanceMap INSTANCES = ImmutableClassToInstanceMap.builder()\n");
    for(String instanceClass : classes) {
      indexText.append("      .put("+instanceClass+".class, new "+instanceClass+"())\n");
    }
    indexText.append("      .build();\n");
    indexText.append("}\n");

    writeSourceFile(packageName+".Fn_Index", indexText.toString());
  }
}
