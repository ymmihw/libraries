package com.ymmihw.libraries;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;
import java.io.File;
import java.io.IOException;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Label.Justification;
import guru.nidi.graphviz.attribute.LinkAttr;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

public class Ex2 {
  public static void main(String[] args) throws IOException {
    Node main = node("main").with(Label.html("<b>main</b><br/>start"), Color.rgb("1020d0").font()),
        init = node(Label.markdown("**_init_**")), execute = node("execute"),
        compare = node("compare").with(Shape.RECTANGLE, Style.FILLED, Color.hsv(.7, .3, 1.0)),
        mkString =
            node("mkString").with(Label.lines(Justification.LEFT, "make", "a", "multi-line")),
        printf = node("printf");

    Graph g = graph("example2").directed().with(
        main.link(to(node("parse").link(execute)).with(LinkAttr.weight(8)),
            to(init).with(Style.DOTTED), node("cleanup"),
            to(printf).with(Style.BOLD, Label.of("100 times"), Color.RED)),
        execute.link(graph().with(mkString, printf), to(compare).with(Color.RED)),
        init.link(mkString));

    Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("example/ex2.png"));
  }
}
