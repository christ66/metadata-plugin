/*
 *  The MIT License
 *
 *  Copyright 2011 Sony Ericsson Mobile Communications. All rights reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.sonyericsson.hudson.plugins.metadata.model.values;

import com.sonyericsson.hudson.plugins.metadata.model.MetaDataJobProperty;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link TreeStructureUtil}.
 *
 * @author Robert Sandell &lt;robert.sandell@sonyericsson.com&gt;
 */
public class TreeStructureUtilTest {

    /**
     * Tests {@link TreeStructureUtil#addValue(MetaDataValueParent, String, String, String...)}.
     *
     * @throws Exception if so.
     */
    @Test
    public void testAddStringValue() throws Exception {
        TreeNodeMetaDataValue root = new TreeNodeMetaDataValue("root");
        boolean result = TreeStructureUtil.addValue(root, "value", "description", "one", "two", "three");
        assertTrue(result);
        TreeNodeMetaDataValue one = (TreeNodeMetaDataValue)root.getChildValue("one");
        assertNotNull(one);
        assertSame(root, one.getParent());
        TreeNodeMetaDataValue two = (TreeNodeMetaDataValue)one.getChildValue("two");
        assertNotNull(two);
        assertSame(one, two.getParent());
        AbstractMetaDataValue three = two.getChildValue("three");
        assertNotNull(three);
        assertSame(two, three.getParent());
        assertEquals("value", three.getValue());
        assertEquals("description", three.getDescription());
    }

    /**
     * Tests {@link TreeStructureUtil#addValue(MetaDataValueParent, String, String, String...)}.
     *
     * @throws Exception if so.
     */
    @Test
    public void testAddStringValueToJobProperty() throws Exception {
        MetaDataJobProperty root = new MetaDataJobProperty();
        boolean result = TreeStructureUtil.addValue(root, "value", "description", "one", "two");
        assertTrue(result);
        TreeNodeMetaDataValue one = (TreeNodeMetaDataValue)root.getChildValue("one");
        assertNotNull(one);
        assertSame(root, one.getParent());
        AbstractMetaDataValue two = one.getChildValue("two");
        assertNotNull(two);
        assertSame(one, two.getParent());
        assertEquals("value", two.getValue());
        assertEquals("description", two.getDescription());
    }

    /**
     * Tests {@link TreeStructureUtil#addValue(MetaDataValueParent, String, String, String...)}. With null description.
     *
     * @throws Exception if so.
     */
    @Test
    public void testAddStringValueNoDescription() throws Exception {
        TreeNodeMetaDataValue root = new TreeNodeMetaDataValue("root");
        boolean result = TreeStructureUtil.addValue(root, "value", null, "one");
        assertTrue(result);
        assertNotNull(root.getChildValue("one"));
        assertNull(root.getChildValue("one").getDescription());
    }

    /**
     * Test {@link TreeStructureUtil#createPath(String, String, String...)}.
     */
    @Test
    public void testCreatePath() {
        TreeNodeMetaDataValue root = TreeStructureUtil.createPath("value", "description", "one", "two");
        assertEquals("one", root.getName());
        assertNotNull(root.getChildValue("two"));
        assertSame(root, root.getChildValue("two").getParent());
        assertEquals("value", root.getChildValue("two").getValue());
        assertEquals("description", root.getChildValue("two").getDescription());
    }

    /**
     * Test {@link TreeStructureUtil#createPath(String, String, String...)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreatePathNoParent() {
        TreeStructureUtil.createPath("value", "description", "one");
    }
}
