/*
 * @(#)HtmlTest.java	1.2 06/10/30
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.tree.*;
import javax.swing.undo.*;
import java.awt.*;
import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

/**
 * Simple wrapper around JEditorPane to browse html.
 * Useful to browse the javadoc for swing... 
 * java HtmlTest file:/path-to-swing-docs/packages.html
 */
public class HtmlTest {

    public static void main(String[] args) {
	Properties props =  System.getProperties();
	props.put("http.proxyHost", "webcache1.eng");
	props.put("http.proxyPort", "8080");

	if (args.length != 1) {
	    System.err.println("need URL argument");
	    System.exit(1);
	}
	try {
	    URL u = new URL(args[0]);
	    JEditorPane html = new JEditorPane(u);
	    html.setEditable(false);
	    html.addHyperlinkListener(new Hyperactive());
	    html.setBackground(Color.white);
	    JScrollPane scroller = new JScrollPane();
	    JViewport vp = scroller.getViewport();
	    vp.add(html);
	    vp.setBackingStoreEnabled(true);

	    JFrame f = new JFrame("testing");
	    f.getContentPane().setLayout(new BorderLayout());
	    f.getContentPane().add("Center", scroller);
	    f.pack();
	    f.setSize(600, 600);
	    f.setVisible(true);

	    JFrame elems = new JFrame("elements");
	    Container fContentPane = elems.getContentPane();
	    fContentPane.setLayout(new BorderLayout());
	    fContentPane.add(new ElementTreePanel(html));
	    elems.pack();
	    elems.show();
	} catch (MalformedURLException e) {
	    System.err.println("Bad url");
	    System.exit(1);
	} catch (IOException ioe) {
	    System.err.println("IOException: " + ioe.getMessage());
	    System.exit(1);
	}
    }

    static class Hyperactive implements HyperlinkListener {

	/**
	 * Notification of a change relative to a 
	 * hyperlink.
	 */
        public void hyperlinkUpdate(HyperlinkEvent e) {
	    System.err.println("link event: " + e);
	    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		JEditorPane html = (JEditorPane) e.getSource();
		if (e instanceof HTMLFrameHyperlinkEvent) {
		    HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
		    HTMLDocument doc = (HTMLDocument)html.getDocument();
		    doc.processHTMLFrameHyperlinkEvent(evt);
		} else {
		    try {
			html.setPage(e.getURL());
		    } catch (Throwable t) {
			t.printStackTrace();
		    }
		}
	    }
	}
    }

/**
 * Displays a tree showing all the elements in a text Document. Selecting
 * a node will result in reseting the selection of the JTextComponent.
 *
 * @author Scott Violet
 * @version 1.1 04/28/98
 */
static class ElementTreePanel extends JPanel implements DocumentListener, TreeSelectionListener {
    /** Tree showing the documents element structure. */
    protected JTree             tree;
    /** Text component showing elemenst for. */
    protected JTextComponent    editor;
    /** Model for the tree. */
    protected DefaultTreeModel  treeModel;

    public ElementTreePanel(JTextComponent editor) {
	this.editor = editor;

	Document document = editor.getDocument();

	// Create the tree.
	treeModel = new DefaultTreeModel((TreeNode)document.
					 getDefaultRootElement());
	tree = new JTree(treeModel) {
	    public String convertValueToText(Object value, boolean selected,
					     boolean expanded, boolean leaf,
					     int row, boolean hasFocus) {
		Element        e = (Element)value;
		AttributeSet   as = e.getAttributes().copyAttributes();
		String         asString;

		if(as != null) {
		    StringBuffer       retBuffer = new StringBuffer("[");
		    Enumeration        names = as.getAttributeNames();
	    
		    while(names.hasMoreElements()) {
			Object        nextName = names.nextElement();

			if(nextName != StyleConstants.ResolveAttribute) {
			    retBuffer.append(" ");
			    retBuffer.append(nextName);
			    retBuffer.append("=");
			    retBuffer.append(as.getAttribute(nextName));
			}
		    }
		    retBuffer.append(" ]");
		    asString = retBuffer.toString();
		}
		else
		    asString = "[ ]";

		if(e.isLeaf())
		    return "LEAF: " + e.getName() + " Attributes: " + asString;
		return "Branch: " + e.getName() + " Attributes: " +
 		        asString;
	    }
	};
	tree.addTreeSelectionListener(this);
	// become a listener on the document to update the tree.
	document.addDocumentListener(this);

	// configure the panel and frame containing it.
	setLayout(new BorderLayout());
	add(new JScrollPane(tree), BorderLayout.CENTER);

	// Add a label above tree to describe what is being shown
	JLabel     label = new JLabel("Elements that make up the current document", SwingConstants.CENTER);

	label.setFont(new Font("Dialog", Font.BOLD, 14));
	add(label, BorderLayout.NORTH);

	setPreferredSize(new Dimension(400, 400));
    }


    // DocumentListener

    /**
     * Gives notification that there was an insert into the document.  The 
     * given range bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e) {
	updateTree(e);
    }

    /**
     * Gives notification that a portion of the document has been 
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e) {
	updateTree(e);
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e) {
	updateTree(e);
    }

    // TreeSelectionListener

    /** 
      * Called whenever the value of the selection changes.
      * @param e the event that characterizes the change.
      */
    public void valueChanged(TreeSelectionEvent e) {
	JTree       tree = getTree();

	if(tree.getSelectionCount() == 1) {
	    TreePath      selPath = tree.getSelectionPath();
	    Element       selElement = (Element)selPath.getLastPathComponent();

	    getEditor().select(selElement.getStartOffset(),
			       selElement.getEndOffset());
	}
    }

    // Local methods

    /**
     * @return tree showing elements.
     */
    protected JTree getTree() {
	return tree;
    }

    /**
     * @return JTextComponent showing elements for.
     */
    protected JTextComponent getEditor() {
	return editor;
    }

    /**
     * @return root element of editor.
     */
    protected Element getRootElement() {
	return getEditor().getDocument().getDefaultRootElement();
    }

    /**
     * @return TreeModel implementation used to represent the elements.
     */
    public DefaultTreeModel getTreeModel() {
	return treeModel;
    }

    /**
     * Updates the tree based on the event type. This will invoke either
     * updateTree with the root element, or handleChange.
     */
    protected void updateTree(DocumentEvent event) {
	updateTree(event, getRootElement());
    }

    /**
     * Creates TreeModelEvents based on the DocumentEvent and messages
     * the treemodel. This recursively invokes this method with children
     * elements.
     * @param event indicates what elements in the tree hierarchy have
     * changed.
     * @param element Current element to check for changes against.
     */
    protected void updateTree(DocumentEvent event, Element element) {
        DocumentEvent.ElementChange ec = event.getChange(element);

        if (ec != null) {
	    Element[]       removed = ec.getChildrenRemoved();
	    Element[]       added = ec.getChildrenAdded();
	    int             startIndex = ec.getIndex();

	    // Check for removed.
	    if(removed != null && removed.length > 0) {
		int[]            indices = new int[removed.length];

		for(int counter = 0; counter < removed.length; counter++) {
		    indices[counter] = startIndex + counter;
		}
		getTreeModel().nodesWereRemoved((TreeNode)element, indices,
						removed);
	    }
	    // check for added
	    if(added != null && added.length > 0) {
		int[]            indices = new int[added.length];

		for(int counter = 0; counter < added.length; counter++) {
		    indices[counter] = startIndex + counter;
		}
		getTreeModel().nodesWereInserted((TreeNode)element, indices);
	    }
        }
	else if(!element.isLeaf()) {
	    // If the event is a CHANGE, then need to forward to all
	    // elements in effected range.
	    // Otherwise, only forward to element at the offset of the
	    // event.
	    if(event.getType() == DocumentEvent.EventType.CHANGE) {
		int        startIndex = element.getElementIndex
		                       (event.getOffset());
		int        endIndex = Math.min(element.getElementCount() - 1,
					       element.getElementIndex
				     (event.getOffset() + event.getLength()));

		if(startIndex != -1 && endIndex != -1) {
		    for(int counter = startIndex; counter <= endIndex;
			counter++) {
			updateTree(event, element.getElement(counter));
		    }
		}
	    }
	    else {
		// Element hasn't changed, find the child in the effected
		// region and forward.
		int       childIndex = element.getElementIndex
		                       (event.getOffset());

		if(childIndex >= 0 && childIndex < element.getElementCount())
		    updateTree(event, element.getElement(childIndex));
	    }
	}
	else {
	    // Element is a leaf, assume it changed
	    getTreeModel().nodeChanged((TreeNode)element);
	}
    }
}

}
