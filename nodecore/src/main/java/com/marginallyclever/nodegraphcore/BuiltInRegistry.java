package com.marginallyclever.nodegraphcore;

import com.marginallyclever.nodegraphcore.json.*;
import com.marginallyclever.nodegraphcore.type.FourNumberArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Registers built-in {@link Node}s to the {@link NodeFactory}.
 * Registers built-in types with the JSON DAO factory.
 *
 * @author Dan Royer
 * @since 2022-02-01
 */
public class BuiltInRegistry implements NodeRegistry, DAORegistry {
    private static final Logger logger = LoggerFactory.getLogger(BuiltInRegistry.class);

    public String getName() {
        return "Built-in";
    }

    /**
     * Register nodes
     */
    public void registerNodes() throws GraphException {
        logger.info("Registering core nodes");
        NodeFactory.registerAllNodesInPackage("com.marginallyclever.nodegraphcore.nodes.custom");
    }

    /**
     * Register DAOs.
     */
    @Override
    public void registerDAO() {
        logger.info("Registering core DAOs");
        DAO4JSONFactory.registerDAO(Rectangle.class, new RectangleDAO4JSON());
        DAO4JSONFactory.registerDAO(String.class, new StringDAO4JSON());
        DAO4JSONFactory.registerDAO(Double.class, new DoubleDAO4JSON());
        DAO4JSONFactory.registerDAO(Integer.class, new IntegerDAO4JSON());
        DAO4JSONFactory.registerDAO(Number.class, new NumberDAO4JSON());
        DAO4JSONFactory.registerDAO(Boolean.class, new BooleanDAO4JSON());
        DAO4JSONFactory.registerDAO(Object.class, new ObjectDAO4JSON());
        DAO4JSONFactory.registerDAO(Void.TYPE, new VoidDAO4JSON());
        DAO4JSONFactory.registerDAO(FourNumberArray.class, new FourNumberArrayDAO4JSON());
    }
}
