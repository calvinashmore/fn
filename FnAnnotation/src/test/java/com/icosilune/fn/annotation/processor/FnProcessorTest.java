/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.annotation.processor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

import javax.tools.JavaFileObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.testing.compile.JavaFileObjects;
import com.icosilune.fn.processor.FnProcessor;

/**
 *
 * @author ashmore
 */
@RunWith(JUnit4.class)
public class FnProcessorTest {

  @Test
  public void testSimpleFn() {
    JavaFileObject javaFileObject = JavaFileObjects.forSourceLines(
        "foo.bar.Add",
        "package foo.bar;",
        "",
        "import com.icosilune.fn.AbstractFn;",
        "import com.icosilune.fn.Fn;",
        "import java.util.List;",
        "",
        "@Fn",
        "public abstract class Add extends AbstractFn {",
        "  public double evaluate(double x, double y) {",
        "    return x + y;",
        "  }",
        "",
//        "  public static Baz create(int buh) {",
//        "    return new AutoValue_Baz(buh);",
//        "  }",
        "}");
    JavaFileObject expectedOutput = JavaFileObjects.forSourceLines(
        "foo.bar.Add_Fn",
//        "hello"
        "package foo.bar;",
        "class Fn_Add {}"
//        "",
//        "import javax.annotation.Generated;",
//        "",
//        "@Generated(\"" + FnProcessor.class.getName() + "\")",
//        "final class AutoValue_Baz extends Baz {",
//        "  private final int buh;",
//        "",
//        "  AutoValue_Baz(int buh) {",
//        "    this.buh = buh;",
//        "  }",
//        "",
//        "  @Override public int buh() {",
//        "    return buh;",
//        "  }",
//        "",
//        "  @Override public String toString() {",
//        "    return \"Baz{\"",
//        "        + \"buh=\" + buh",
//        "        + \"}\";",
//        "  }",
//        "",
//        "  @Override public boolean equals(Object o) {",
//        "    if (o == this) {",
//        "      return true;",
//        "    }",
//        "    if (o instanceof Baz) {",
//        "      Baz that = (Baz) o;",
//        "      return (this.buh == that.buh());",
//        "    }",
//        "    return false;",
//        "  }",
//        "",
//        "  @Override public int hashCode() {",
//        "    int h = 1;",
//        "    h *= 1000003;",
//        "    h ^= buh;",
//        "    return h;",
//        "  }",
//        "}"
    );

    FnProcessor fnProcessor = new FnProcessor();

    assertAbout(javaSource())
        .that(javaFileObject)
        .processedWith(fnProcessor)
        .compilesWithoutError()
            ;
//        .and()
//        .generatesSources(expectedOutput);

  }

  @Test
  public void testNestedFn() {
    JavaFileObject javaFileObject = JavaFileObjects.forSourceLines(
        "foo.bar.Misc",
        "package foo.bar;",
        "",
        "import com.icosilune.fn.AbstractFn;",
        "import com.icosilune.fn.Fn;",
        "import java.util.List;",
        "",
        "public class Misc {",
        "  @Fn",
        "  public static abstract class Add extends AbstractFn {",
        "    public double evaluate(double x, double y) {",
        "      return x + y;",
        "    }",
        "  }",
        "",
        "}");

    FnProcessor fnProcessor = new FnProcessor();

    assertAbout(javaSource())
        .that(javaFileObject)
        .processedWith(fnProcessor)
        .compilesWithoutError()
            ;

  }
}
