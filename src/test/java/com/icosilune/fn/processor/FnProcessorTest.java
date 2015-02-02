/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.processor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

import javax.tools.JavaFileObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.testing.compile.JavaFileObjects;

/**
 *
 * @author ashmore
 */
@RunWith(JUnit4.class)
public class FnProcessorTest {

  @Test
  public void testSimpleFn() {
    JavaFileObject javaFileObject = JavaFileObjects.forSourceLines(
        "foo.bar.Baz",
        "package foo.bar;",
        "",
        "import com.icosilune.fn.Fn;",
        "",
        "@Fn",
        "public abstract class Baz {",
        "  public abstract int buh();",
        "",
//        "  public static Baz create(int buh) {",
//        "    return new AutoValue_Baz(buh);",
//        "  }",
        "}");
    JavaFileObject expectedOutput = JavaFileObjects.forSourceLines(
        "foo.bar.Fn_Baz",
//        "hello"
        "package foo.bar;",
        "class Fn_Baz {}"
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

//    assertAbout(javaSource())
//        .that(javaFileObject)
//        .processedWith(new FnProcessor())
//        .compilesWithoutError()
//        .and()
//        .generatesSources(expectedOutput);
  }
}
