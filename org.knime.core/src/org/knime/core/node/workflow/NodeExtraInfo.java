/* 
 * -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 *
 * Copyright, 2003 - 2006
 * University of Konstanz, Germany.
 * Chair for Bioinformatics and Information Mining
 * Prof. Dr. Michael R. Berthold
 *
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner or
 * as specified in the license file distributed with this product.
 *
 * If you have any questions please contact the copyright holder:
 * website: www.knime.org
 * email: contact@knime.org
 * -------------------------------------------------------------------
 * 
 * History
 *   18.03.2005 (mb): created
 *   09.01.2006 (mb): clean up for code review
 */
package org.knime.core.node.workflow;


/**
 * Interface for an object that's held within a NodeContainer and allows loading
 * and saving of information. Usually such objects will hold information about
 * the Node's position in some layout.
 * 
 * @author M. Berthold, University of Konstanz
 */
public interface NodeExtraInfo extends ExtraInfo {

    /**
     * Checks if all information for this extra info is set properly.
     * 
     * @return true if infos are set properly
     */
    public boolean isFilledProperly();

    /**
     * Changes the position according to the given moving distance.
     * 
     * @param moveDist the distance to change position
     */
    public void changePosition(final int moveDist);

    /**
     * @see Object#clone()
     */
    public Object clone() throws CloneNotSupportedException;
}
