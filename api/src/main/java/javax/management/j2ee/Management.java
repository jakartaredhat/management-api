/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.management.j2ee;

import java.util.Set;

import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.AttributeList;
import javax.management.Attribute;
import javax.management.MBeanInfo;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.AttributeNotFoundException;
import javax.management.ReflectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.IntrospectionException;

import java.rmi.RemoteException;

/**
 * The Management interface provides the APIs to navigate and manipulate
 * managed objects. The Jakarta EE Management EJB component (MEJB) must implement
 * this as its remote interface.
 *
 * @author Hans Hrasna
 */
public interface Management extends javax.ejb.EJBObject {

    /**
     * Gets the names of managed objects controlled by the MEJB. This method
     * enables any of the following to be obtained: The names of all managed objects,
     * the names of a set of managed objects specified by pattern matching on the
     * <CODE>ObjectName</CODE>, a specific managed object name (equivalent to
     * testing whether a managed object is registered). When the object name is
     * null or no domain and key properties are specified, all objects are selected.
     * It returns the set of Jakarta EEObjectNames for the managed objects selected.
     *
     * @param name The object name pattern identifying the managed objects to be retrieved. If
     * null or no domain and key properties are specified, all the managed objects registered will be retrieved.
     *
     * @return  A set containing the ObjectNames for the managed objects selected.
     * If no managed object satisfies the query, an empty set is returned.
     *
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    Set queryNames(ObjectName name, QueryExp query) throws RemoteException;

    /**
     * Checks whether a managed object, identified by its object name, is already registered
     * with the MEJB.
     *
     * @param name The object name of the managed object to be checked.
     *
     * @return  True if the managed object is already registered in the MEJB, false otherwise.
     *
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    boolean isRegistered(ObjectName name) throws RemoteException;

    /**
     * Returns the number of managed objects registered in the MEJB.
     *
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    Integer getMBeanCount() throws RemoteException;

    /**
     * This method discovers the attributes and operations that a managed object exposes
     * for management.
     *
     * @param name The name of the managed object to analyze
     *
     * @return  An instance of <CODE>MBeanInfo</CODE> allowing the retrieval of all attributes and operations
     * of this managed object.
     *
     * @exception IntrospectionException An exception occurs during introspection.
     * @exception InstanceNotFoundException The managed object specified is not found.
     * @exception ReflectionException An exception occurred when trying to perform reflection on a managed object
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    MBeanInfo getMBeanInfo(ObjectName name) throws IntrospectionException, InstanceNotFoundException, ReflectionException, RemoteException;

    /**
     * Gets the value of a specific attribute of a named managed object. The managed object
     * is identified by its object name.
     *
     * @param name The object name of the managed object from which the attribute is to be retrieved.
     * @param attribute A String specifying the name of the attribute to be
     * retrieved.
     *
     * @return  The value of the retrieved attribute.
     *
     * @exception AttributeNotFoundException The attribute specified is not accessible in the managed object.
     * @exception MBeanException  Wraps an exception thrown by the managed object's getter.
     * @exception InstanceNotFoundException The managed object specified is not registered in the MEJB.
     * @exception ReflectionException An exception occurred when trying to invoke the getAttribute method of a Dynamic MBean
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    Object getAttribute(ObjectName name, String attribute) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, RemoteException;

    /**
     * Enables the values of several attributes of a named managed object. The managed object
     * is identified by its object name.
     *
     * @param name The object name of the managed object from which the attributes are
     * retrieved.
     * @param attributes A list of the attributes to be retrieved.
     *
     * @return The list of the retrieved attributes.
     *
     * @exception InstanceNotFoundException The managed object specified is not registered in the MEJB.
     * @exception ReflectionException An exception occurred when trying to invoke the getAttributes method of a Dynamic MBean.
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    AttributeList getAttributes(ObjectName name, String[] attributes) throws InstanceNotFoundException, ReflectionException, RemoteException;

    /**
     * Sets the value of a specific attribute of a named managed object. The managed object
     * is identified by its object name.
     *
     * @param name The name of the managed object within which the attribute is to be set.
     * @param attribute The identification of the attribute to be set and the value it is to be set to.
     *
     * @exception InstanceNotFoundException The managed object specified is not registered in the MEJB.
     * @exception AttributeNotFoundException The attribute specified is not accessible in the managed object.
     * @exception InvalidAttributeValueException The value specified for the attribute is not valid.
     * @exception MBeanException Wraps an exception thrown by the managed object's setter.
     * @exception ReflectionException An exception occurred when trying to invoke the setAttribute method of a Dynamic MBean.
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    void setAttribute(ObjectName name, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, RemoteException;

    /**
     * Sets the values of several attributes of a named managed object. The managed object is
     * identified by its object name.
     *
     * @param name The object name of the managed object within which the attributes are to
     * be set.
     * @param attributes A list of attributes: The identification of the
     * attributes to be set and  the values they are to be set to.
     *
     * @return  The list of attributes that were set, with their new values.
     *
     * @exception InstanceNotFoundException The managed object specified is not registered in the MEJB.
     * @exception ReflectionException An exception occurred when trying to invoke the setAttributes method of a Dynamic MBean.
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     *
     */
    AttributeList setAttributes(ObjectName name, AttributeList attributes) throws InstanceNotFoundException, ReflectionException, RemoteException;

    /**
     * Invokes an operation on a managed object.
     *
     * @param name The object name of the managed object on which the method is to be invoked.
     * @param operationName The name of the operation to be invoked.
     * @param params An array containing the parameters to be set when the operation is
     * invoked
     * @param signature An array containing the signature of the operation. The class objects will
     * be loaded using the same class loader as the one used for loading the managed object on which the operation was invoked.
     *
     * @return  The object returned by the operation, which represents the result of invoking the operation on the
     * managed object specified.
     *
     * @exception InstanceNotFoundException The managed object specified is not registered in the MEJB.
     * @exception MBeanException  Wraps an exception thrown by the managed object's invoked method.
     * @exception ReflectionException  Wraps a <CODE>java.lang.Exception</CODE> thrown while trying to invoke the method.
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    Object invoke(ObjectName name, String operationName, Object[] params, String[] signature) throws  InstanceNotFoundException, MBeanException, ReflectionException, RemoteException;

    /**
     * Returns the default domain name of this MEJB.
     * @exception RemoteException A communication exception occurred during the execution of a remote method call
     */
    String getDefaultDomain() throws RemoteException;

   /**
    * Returns the listener registry implementation for this MEJB. The listener registry implements the methods
    * that enable clints to add and remove event notification listeners managed objects
    * @return An implementation of <CODE>javax.management.j2ee.ListenerRegistration</CODE>
    *
    * @exception RemoteException A communication exception occurred during the execution of a remote method call
    */
    ListenerRegistration getListenerRegistry() throws RemoteException;

}
