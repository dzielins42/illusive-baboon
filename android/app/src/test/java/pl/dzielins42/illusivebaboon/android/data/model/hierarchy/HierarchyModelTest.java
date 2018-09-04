package pl.dzielins42.illusivebaboon.android.data.model.hierarchy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HierarchyModelTest {

    private Gson mGson;

    @Before
    public void setUp() throws Exception {
        mGson = new GsonBuilder().setPrettyPrinting().create();
    }

    @After
    public void tearDown() throws Exception {
        mGson = null;
    }

    @Test
    public void name() throws Exception {
        Node node1 = Node.builder()
         .id("node1")
         .name("node1")
         .child(
          Node.builder()
           .id("node2")
           .name("node2")
           .reference(
            GeneratorReference.builder()
             .id("ref1")
             .name("ref1")
             .generatorId("ref1")
             .build()
            )
            .reference(
             GeneratorReference.builder()
              .id("ref2")
              .name("ref2")
              .generatorId("ref2")
              .build()
             )
             .build()
         )
         .child(
          Node.builder()
           .id("node3")
           .name("node3")
           .reference(
            GeneratorReference.builder()
             .id("ref3")
             .name("ref1")
             .generatorId("ref3")
             .build()
            )
            .build()
         )
         .child(
          Node.builder()
           .id("node4")
           .name("node4")
           .child(
            Node.builder()
             .id("node5")
             .name("node5")
             .reference(
              GeneratorReference.builder()
               .id("ref4")
               .name("ref4")
               .generatorId("ref4")
               .build()
             )
             .build()
           )
           .build()
         )
         .build();

        System.out.println(mGson.toJson(node1));
    }
}