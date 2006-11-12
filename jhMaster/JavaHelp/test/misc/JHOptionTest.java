/*
 * @(#)JHOptionTest.java	1.4 06/10/30
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

/*
 * JHOptionTest.java
 */

import javax.javahelp.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

/**
 * A demo that tests the JOptionPane optiontypes
 *
 */
public class JHOptionTest extends JPanel
{
    // This
    JHOptionTest opttest;

    // The Frame
    public static JFrame frame;

    // The width and height of the frame
    public static int WIDTH = 790;
    public static int HEIGHT = 550;
    public static int INITIAL_WIDTH = 400;
    public static int INITIAL_HEIGHT = 200;

    public Font defaultFont = new Font("Dialog", Font.PLAIN, 12);

    public JHOptionTest() {
	super(true); // double buffer

	opttest = this;
	setName("Main JHOptionTest Panel");
	setFont(defaultFont);
	setLayout(new BorderLayout());

	// Add a MenuBar
	add(createMenuBar(), BorderLayout.NORTH);

    }

    /*******************************************/
    /************ create components ************/
    /*******************************************/

    /**
     * MenuBar
     */
    Dialog aboutBox;
    JCheckBoxMenuItem cb;
    JRadioButtonMenuItem rb;

    JMenuBar createMenuBar() {
	// MenuBar
	JMenuBar menuBar = new JMenuBar();
	menuBar.getAccessibleContext().setAccessibleName("Swing menus");

	JMenuItem mi;

	// File Menu
	JMenu file = (JMenu) menuBar.add(new JMenu("File"));
        file.setMnemonic('F');
        mi = (JMenuItem) file.add(new JMenuItem("Exit"));
        mi.setMnemonic('x');
	mi.getAccessibleContext().setAccessibleDescription("Exit the JHOptionTest application");
	mi.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	}
	);

	createOptionsMenu(menuBar);
	return menuBar;
    }

    void createOptionsMenu(JMenuBar menuBar) {
	JMenu optionMenu = (JMenu)menuBar.add(new JMenu("Dialogs"));
        optionMenu.setMnemonic('D');
	JMenuItem item;
	item = new JMenuItem("Message Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(JHOptionTest.this, "Plain message");
	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Warning Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(JHOptionTest.this, "Example Warning",
				    "Warning", JOptionPane.WARNING_MESSAGE);
	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Confirmation Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int        result;
		result = JOptionPane.showConfirmDialog(JHOptionTest.this, "Is SWING cool?");
		if(result == JOptionPane.YES_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "All right!");
		else if(result == JOptionPane.NO_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "That is too bad, please send us email describing what you don't like and how we can change it.");
	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Input Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String          result;

		result = JOptionPane.showInputDialog(JHOptionTest.this, "Please enter your name:");
		if(result != null) {
		    Object[] message = new Object[2];
		    message[0] = "Thank you for using SWING ";
		    message[1] = result;
		    JOptionPane.showMessageDialog(JHOptionTest.this, message);
		}
	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Message Help Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int result;
		result = HelpUtilities.showMessageDialog(JHOptionTest.this,
							 "Plain message",
							 "Message",
							 HelpUtilities.DEFAULT_HELP_OPTION, 
							 JOptionPane.INFORMATION_MESSAGE,
							 null);
		if (result == HelpUtilities.HELP_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "Help NYI");
	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Confirmaion YES_NO_HELP Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int        result;
		result = HelpUtilities.showConfirmDialog(JHOptionTest.this,
						       "Is SWING cool?",
						       "Swing Confirmation",
						       HelpUtilities.YES_NO_HELP_OPTION);
		if(result == HelpUtilities.YES_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "All right!");
		else if(result == HelpUtilities.NO_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "That is too bad, please send us email describing what you don't like and how we can change it.");
		else if (result == HelpUtilities.HELP_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "Help NYI");

	    }
	});
	optionMenu.add(item);

	item = new JMenuItem("Confirmaion YES_NO_CANCEL_HELP Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int        result;
		result = HelpUtilities.showConfirmDialog(JHOptionTest.this,
						       "Is SWING cool?",
						       "Swing Confirmation",
						       HelpUtilities.YES_NO_CANCEL_HELP_OPTION);
		if(result == HelpUtilities.YES_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "All right!");
		else if(result == HelpUtilities.NO_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "That is too bad, please send us email describing what you don't like and how we can change it.");
		else if (result == HelpUtilities.HELP_OPTION)
		    JOptionPane.showMessageDialog(JHOptionTest.this, "Help NYI");

	    }
	});
	optionMenu.add(item);


	/* later when working
	item = new JMenuItem("Input Help Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Object          result;

		result = HelpUtilities.showInputDialog(JHOptionTest.this,
						     "Please enter your name:",
						     "Input",
						     HelpUtilities.OK_CANCEL_HELP_OPTION,
						     JOptionPane.QUESTION_MESSAGE,
						     null, null, null);
		if (result instanceof Integer) {
		    JOptionPane.showMessageDialog(JHOptionTest.this, "Help NYI");
		} else {
		    if(result != null) {
			Object[] message = new Object[2];
			message[0] = "Thank you for using SWING ";
			message[1] = (String) result;
			JOptionPane.showMessageDialog(JHOptionTest.this, message);
		    }
		}
	    }
	});
	optionMenu.add(item);
	*/

	item = new JMenuItem("Component Dialog");
	item.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Object[]      message = new Object[4];
                JComboBox cb = new JComboBox();
                cb.addItem("One");
                cb.addItem("Two");
                cb.addItem("Three");
		message[0] = "JOptionPane can contain any number of components, and any number options.";
		message[1] = new JButton("a button");
		message[2] = new JTextField("a text field");
                message[3] = cb;
                

		String[]      options = { "Option 1", "Option 2", "Option 3",
					  "Option 4" };
		JOptionPane.showOptionDialog(JHOptionTest.this, message, "Example", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
	    }
	});
	optionMenu.add(item);
    }
	

    public static void main(String[] args) {

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {System.exit(0);}
	};

	frame = new JFrame("JHOptionTest");
	frame.addWindowListener(l);

	JOptionPane.setRootFrame(frame);

	// show the frame
	frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setLocation(screenSize.width/2 - INITIAL_WIDTH/2,
			  screenSize.height/2 - INITIAL_HEIGHT/2);
	frame.show();

        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	JHOptionTest sw = new JHOptionTest();
	frame.getContentPane().removeAll();
	frame.getContentPane().setLayout(new BorderLayout());
	frame.getContentPane().add(sw, BorderLayout.CENTER);
	frame.setLocation(screenSize.width/2 - WIDTH/2,
			  screenSize.height/2 - HEIGHT/2);

	frame.setSize(WIDTH, HEIGHT);
	frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	frame.validate();
	frame.repaint();
    }


}

