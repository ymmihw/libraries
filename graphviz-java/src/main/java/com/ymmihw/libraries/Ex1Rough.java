package com.ymmihw.libraries;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import java.io.File;
import java.io.IOException;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.rough.FillStyle;
import guru.nidi.graphviz.rough.Roughifyer;

public class Ex1Rough {
  public static void main(String[] args) throws IOException {
    final Graph g = graph("ex1").directed().with(
        graph().cluster().nodeAttr().with(Style.FILLED, Color.WHITE).graphAttr()
            .with(Style.FILLED, Color.LIGHTGREY, Label.of("process #1"))
            .with(node("a0").link(node("a1").link(node("a2")))),
        graph("x").cluster().nodeAttr().with(Style.FILLED).graphAttr()
            .with(Color.BLUE, Label.of("process #2"))
            .with(node("b0").link(node("b1").link(node("b2")))),
        node("start").with(Shape.M_DIAMOND).link("a0", "b0"),
        node("a0").with(Style.FILLED, Color.RED.gradient(Color.BLUE)).link("b1"),
        node("b1").link("a2"), node("a2").link("end"), node("b2").link("end"),
        node("end").with(Shape.M_SQUARE));

    Graphviz.fromGraph(g).processor(new Roughifyer().bowing(2).curveStepCount(6).roughness(1)
        .fillStyle(FillStyle.hachure().width(2).gap(5).angle(0)).font("*serif", "Comic Sans MS"))
        .render(Format.PNG).toFile(new File("example/ex1-rough.png"));
  }
}
