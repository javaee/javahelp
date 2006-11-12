/*
 * @(#)ClassTree.java	1.6 06/10/30
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

/*
 * @(#) ClassTree.java 1.6 - last change made 10/30/06
 *
 * (c) 1997-1998 Sun Microsystems, Inc.  All rights reserved.  Use is
 * subject to license terms. Sun, Sun Microsystems, the Sun Logo, Solaris,
 * Java, the Java Coffee Cup Logo, and JavaHelp are trademarks or registered
 * trademarks of Sun Microsystems, Inc. in  the U.S. and other countries.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package sunw.demo.doclet;

import com.sun.javadoc.*;
import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Build Class Hierarchy for all the Classes. This class builds the Class
 * Tree and the Interface Tree separately.
 *
 * @see java.util.HashMap
 * @see java.util.List
 * @see sun.tools.javadoc.Type
 * @see sun.tools.javadoc.ClassDoc
 * @author Atul M Dambalkar
 * @author Roger D. Brinkley
 * @version	1.6	10/30/06
 */

public class ClassTree { 
    
    /**
     * List of baseclasses. Contains only java.lang.Object. Can be used to get
     * the mapped listing of sub-classes.
     */
    private List baseclasses = new ArrayList();

    /** 
    * Mapping for each Class with their SubClasses 
    */
    private Map subclasses = new HashMap();

    /**
     * List of base-interfaces. Contains list of all the interfaces who do not
     * have super-interfaces. Can be used to get the mapped listing of
     * sub-interfaces.
     */
    private List baseinterfaces = new ArrayList();

    /** 
    * Mapping for each Interface with their SubInterfaces 
    */
    private Map subinterfaces = new HashMap();

    /** 
    * Mapping for each Interface with classes who implement it.
    */
    private Map implementingclasses = new HashMap();

    /**
     * Constructor. Build the Tree using the RootDoc of this Javadoc.
     * 
     * @param root RootDoc of the Document.
     */
    public ClassTree(RootDoc root) {
        buildTree(root.classes());
    } 

    /**
     * Constructor. Build the tree for the given array of classes.
     *
     * @param classes Array of classes.
     */
    public ClassTree(ClassDoc[] classes) {
        buildTree(classes);
    }
        
    /**
     * Generate mapping for the sub-classes for every class in this run. 
     * Return the sub-class list for java.lang.Object which will be having 
     * sub-class listing for itself and also for each sub-class itself will
     * have their own sub-class lists. 
     *
     * @param classes all the classes in this run.
     */
    private void buildTree(ClassDoc[] classes) {
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].isClass()) {
                processClass(classes[i]);
            } else {    // this is an interface.
                processInterface(classes[i]);
                List list  = (List)implementingclasses.get(classes[i]);
                if (list != null) {
                    Collections.sort(list);
                }
            }
        }

        Collections.sort(baseinterfaces);
        for (Iterator it = subinterfaces.values().iterator(); it.hasNext(); ) {
            Collections.sort((List)it.next());
        }
        for (Iterator it = subclasses.values().iterator(); it.hasNext(); ) {
            Collections.sort((List)it.next());
        }
    }

    /**
     * For the class passed map it to it's own sub-class listing.
     * For the Class passed, get the super class, 
     * if superclass is non null, (it is not "java.lang.Object")
     *    get the "value" from the hashmap for this key Class
     *    if entry not found create one and get that.
     *    add this Class as a sub class in the list 
     *    Recurse till hits java.lang.Object Null SuperClass.
     * 
     * @param cd class for which sub-class mapping to be generated. 
     */
    private void processClass(ClassDoc cd) {
        ClassDoc superclass = cd.superclass();      
        if (superclass != null) {   
            if (!add(subclasses, superclass, cd)) {
                return;
            } else {
                processClass(superclass);      // Recurse
            }
        } else {     // cd is java.lang.Object, add it once to the list 
            if (!baseclasses.contains(cd)) {
                baseclasses.add(cd);
            }
        } 
        ClassDoc[] intfacs = cd.interfaces();
        for (int i = 0; i < intfacs.length; i++) {
            add(implementingclasses, intfacs[i], cd);    
        }
    }

    /**
     * For the interface passed get the interfaces which it extends, and then
     * put this interface in the sub-interface list of those interfaces. Do it
     * recursively. If a interface doesn't have super-interface just attach
     * that interface in the list of all the baseinterfaces.
     *
     * @param cd Interface under consideration.
     */
    private void processInterface(ClassDoc cd) {
        ClassDoc[] intfacs = cd.interfaces();
        if (intfacs.length > 0) {
            for (int i = 0; i < intfacs.length; i++) {
                if (!add(subinterfaces, intfacs[i], cd)) {
                    return;
                } else {
                    processInterface(intfacs[i]);   // Recurse
                }
            }
        } else {   
            // we need to add all the interfaces who do not have
            // super-interfaces to baseinterfaces list to traverse them
            if (!baseinterfaces.contains(cd)) {
                baseinterfaces.add(cd);
            }
        }
    }

    /**
     * Adjust the Class Tree. Add the class interface  in to it's super-class' 
     * or super-interface's sub-interface list.
     *
     * @param map the entire map.
     * @param superclass java.lang.Object or the super-interface.
     * @param cd sub-interface to be mapped.
     * @returns boolean true if class added, false if class already processed.
     */
    private boolean add(Map map, ClassDoc superclass, ClassDoc cd) {
        List list = (List)map.get(superclass);
        if (list == null) {
            list = new ArrayList();
            map.put(superclass, list); 
        }     
        if (list.contains(cd)) {
            return false; 
        } else {
            list.add(cd);
        }
        return true;
    }    

    /** 
     * From the map return the list of sub-classes or sub-interfaces. If list
     * is null create a new one and return it.
     *
     * @param map The entire map.
     * @param cd class for which the sub-class list is requested.
     * @returns List Sub-Class list for the class passed.
     */
    private List get(Map map, ClassDoc cd) {
        List list = (List)map.get(cd);
        if (list == null) {
            return new ArrayList();
        }
        return list;
    }

    /**
     *  Return the sub-class list for the class passed.
     */ 
    public List subclasses(ClassDoc cd) {
        return get(subclasses, cd);
    }

    /**
     *  Return the sub-interface list for the interface passed.
     */ 
    public List subinterfaces(ClassDoc cd) {
        return get(subinterfaces, cd);
    }

    /**
     *  Return the list of classes which implement the interface passed.
     */ 
    public List implementingclasses(ClassDoc cd) {
        return get(implementingclasses, cd);
    }

    /**
     *  Return the sub-class/interface list for the class/interface passed.
     */ 
    public List subs(ClassDoc cd) {
        return get(cd.isInterface()? subinterfaces: subclasses, cd);
    }

    /**
     *  Return the base-classes list. This will have only one element namely
     *  classdoc for java.lang.Object, since this is the base class for all the
     *  classes.
     */ 
    public List baseclasses() {
        return baseclasses;
    }

    /**
     *  Return the list for the base interfaces. This is the list of interfaces
     *  which do not have super-interface.
     */ 
    public List baseinterfaces() {
        return baseinterfaces;
    }
}
