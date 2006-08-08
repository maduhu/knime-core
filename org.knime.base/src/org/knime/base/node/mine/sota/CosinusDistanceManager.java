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
 *   Jan 12, 2006 (Kilian Thiel): created
 */
package org.knime.base.node.mine.sota;

import org.knime.core.data.DataRow;

/**
 * 
 * @author Kilian Thiel, University of Konstanz
 */
public class CosinusDistanceManager implements DistanceManager {
    private double m_offset;

    private boolean m_fuzzy;

    /**
     * Creates new instance of CosinusDistanceManager with given offset and
     * fuzzy flag. If fuzzy is set <code>true</code>, only fuzzy columns are
     * considered to compute distance, if <code>false</code> only number
     * columns.
     * 
     * @param offset offset to use for distance calculation
     * @param fuzzy if <code>true</code> only fuzzy data is respected, if
     *            <code>false</code> only number data
     */
    public CosinusDistanceManager(final double offset, final boolean fuzzy) {
        m_offset = offset;
        m_fuzzy = fuzzy;
    }

    /**
     * @see DistanceManager#getDistance(DataRow, SotaTreeCell)
     */
    public double getDistance(final DataRow row, final SotaTreeCell cell) {
        return Distances.getCosinusDistance(row, cell, m_offset, m_fuzzy);
    }

    /**
     * @see DistanceManager#getDistance(DataRow, DataRow)
     */
    public double getDistance(final DataRow row1, final DataRow row2) {
        return Distances.getCosinusDistance(row1, row2, m_offset, m_fuzzy);
    }
}
