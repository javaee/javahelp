/*
 * @(#)Calculator.java	1.2 06/10/30
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility. 
 */

/**
 * This is a template.  You may modify this file.
 *
 * Runtime vendor: SunSoft, Inc.
 * Runtime version: 0.5
 *
 * Visual vendor: SunSoft, Inc.
 * Visual version: 0.5
 */


import sunsoft.jws.visual.rt.base.AttributeManager;
import sunsoft.jws.visual.rt.base.Group;
import sunsoft.jws.visual.rt.base.Root;
import sunsoft.jws.visual.rt.base.Shadow;
import sunsoft.jws.visual.rt.base.Message;
import sunsoft.jws.visual.rt.base.Registry;
import sunsoft.jws.visual.rt.shadow.java.awt.*;
import java.awt.*;


public class Calculator extends Group {
  static final int OP_NONE = 0;
  static final int OP_ADD = 1;
  static final int OP_SUBTRACT = 2;
  static final int OP_MULTIPLY = 3;
  static final int OP_DIVIDE = 4;

  private CalculatorRoot gui;
  int operator;
  boolean pendingDelete;
  boolean decimalSeen;
  double fraction;
  double value;
  double accumulator;

  public Calculator() {
    // Add custom attributes here
	addComponentAttributes();
	clear();
  }

  public Root initRoot() {
    gui = new CalculatorRoot(this);
    addAttributeForward(gui.getMainChild());
    return gui;
  }

 public boolean action(Message msg, Event evt, Object what){
	if (msg.target == gui.b0)
		number(0);
	else if (msg.target == gui.b1)
		number(1);
	else if (msg.target == gui.b2)
		number(2);
	else if (msg.target == gui.b3)
		number(3);
	else if (msg.target == gui.b4)
		number(4);
	else if (msg.target == gui.b5)
		number(5);
	else if (msg.target == gui.b6)
		number(6);
	else if (msg.target == gui.b7)
		number(7);
	else if (msg.target == gui.b8)
		number(8);
	else if (msg.target == gui.b9)
		number(9);
	else if (msg.target == gui.clear)
		clear();
	else if (msg.target == gui.clearEntry)
		clearEntry();
	else if (msg.target == gui.plus)
		binary(OP_ADD);
	else if (msg.target == gui.equal)
		equal();
	else if (msg.target == gui.minus)
		binary(OP_SUBTRACT);
	else if (msg.target == gui.multiply)
		binary(OP_MULTIPLY);
	else if (msg.target == gui.divide)
		binary(OP_DIVIDE);
	else if (msg.target == gui.plusMinus)
		invertSign();
	else if (msg.target == gui.decimal)
		insertDecimal();
	else
		return false;
	gui.display.set("text", new Double(value).toString());
	return true;
  }

  void number(int x) {
	if (pendingDelete){
		clearEntry();
		pendingDelete = false;
	}
	if (decimalSeen){
		value += (double)x * fraction;
		fraction *= 0.1;
	} else
		value = value * 10.0 + (double)x;
  }

  void clear() {
	value = 0.0;
	accumulator = 0.0;
	operator = OP_NONE;
	decimalSeen = false;
  }

  void clearEntry() {
	value = 0.0;
	decimalSeen = false;
  }

  void binary(int op) {
	foldValue();
	operator = op;
  }

  void invertSign() {
	value = -value;
  }

  void insertDecimal() {
	if (pendingDelete){
		clearEntry();
		pendingDelete = false;
	}
	decimalSeen = true;
	fraction = 0.1;
  }

  void equal() {
	foldValue();
	operator = OP_NONE;
  }

  void foldValue() {
	if (operator == OP_NONE)
		accumulator = value;
	else if (operator == OP_ADD)
		accumulator += value;
	else if (operator == OP_SUBTRACT)
		accumulator -= value;
	else if (operator == OP_MULTIPLY)
		accumulator *= value;
	else if (operator == OP_DIVIDE)
		accumulator /= value;
	value = accumulator;
	pendingDelete = true;
  }
}
