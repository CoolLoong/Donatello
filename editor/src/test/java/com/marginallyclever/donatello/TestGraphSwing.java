package com.marginallyclever.donatello;

import com.marginallyclever.donatello.actions.GraphSaveAction;
import com.marginallyclever.nodegraphcore.DAO4JSONFactory;
import com.marginallyclever.nodegraphcore.Graph;
import com.marginallyclever.nodegraphcore.NodeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the GraphSwing elements.
 * @author Dan Royer
 * @since 2022-02-21
 */
public class TestGraphSwing {
    private static final Graph model = new Graph();

    @BeforeAll
    public static void beforeAll() {
        DonatelloRegistry r = new DonatelloRegistry();
        r.registerNodes();
        r.registerDAO();
    }

    @AfterAll
    public static void afterAll() {
        NodeFactory.clear();
        DAO4JSONFactory.clear();
    }

    /**
     * Reset
     */
    @BeforeEach
    public void beforeEach() {
        model.clear();
    }

    @Test
    public void testAddExtension() {
        GraphSaveAction actionSaveGraph = new GraphSaveAction("Save",null);
        assertEquals("test.graph",actionSaveGraph.addExtensionIfNeeded("test"));
        assertEquals("test.graph",actionSaveGraph.addExtensionIfNeeded("test.graph"));
    }
}
