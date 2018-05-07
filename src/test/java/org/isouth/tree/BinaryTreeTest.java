package org.isouth.tree;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BinaryTreeTest {
    @Test
    public void testTree() {
        Tree<Object> tree = new BinaryTree<>();
        tree.add(6, "6");
        tree.add(6, "6.1");
        tree.add(3, "3");
        tree.add(8, "8");
        tree.add(7, "7");
        tree.add(9, "9");
        tree.add(2, "2");
        tree.add(10, "10");
        Object r1 = tree.get(9);
        assertEquals("9", r1);
        List<Object> results = tree.rangeSearch(3, 9);
        System.out.println(results);
        tree.delete(8);
        Object r2 = tree.get(8);
        assertNull(r2);
        System.out.println(tree.toString());
    }
}
