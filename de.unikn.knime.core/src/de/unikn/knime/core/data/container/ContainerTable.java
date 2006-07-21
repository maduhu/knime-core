/* 
 * -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 * 
 * Copyright, 2003 - 2006
 * Universitaet Konstanz, Germany.
 * Lehrstuhl fuer Angewandte Informatik
 * Prof. Dr. Michael R. Berthold
 * 
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner.
 * -------------------------------------------------------------------

 */
package de.unikn.knime.core.data.container;

import java.io.File;
import java.io.IOException;

import de.unikn.knime.core.data.DataTable;
import de.unikn.knime.core.data.DataTableSpec;
import de.unikn.knime.core.data.RowIterator;
import de.unikn.knime.core.node.CanceledExecutionException;
import de.unikn.knime.core.node.ExecutionMonitor;
import de.unikn.knime.core.node.NodeSettingsWO;
import de.unikn.knime.core.node.BufferedDataTable.KnowsRowCountTable;

/**
 * Class implementing the <code>DataTable</code> interface and using a buffer
 * from a <code>DataContainer</code> as data source. This class doesn't do 
 * functional things. It only provides the <code>DataTable</code> methods.
 * 
 * <p>We split it from the <code>Buffer</code> implementation as a buffer is
 * dynamic in size. This table should only be used when the buffer has been
 * fixed.
 * @author Bernd Wiswedel, University of Konstanz
 */
public class ContainerTable implements DataTable, KnowsRowCountTable {
    
    /** To read the data from. */
    private final Buffer m_buffer;
    
    /**
     * Create new Table based on a Buffer. This constructor is called from
     * <code>DataContainer.getTable()</code>.
     * @param buffer To read data from.
     * @see DataContainer#getTable()
     */
    ContainerTable(final Buffer buffer) {
        assert (buffer != null);
        m_buffer = buffer;
    }

    /**
     * @see de.unikn.knime.core.data.DataTable#getDataTableSpec()
     */
    public DataTableSpec getDataTableSpec() {
        return m_buffer.getTableSpec();
    }

    /**
     * @see de.unikn.knime.core.data.DataTable#iterator()
     */
    public RowIterator iterator() {
        return m_buffer.iterator();
    }
    
    /**
     * @see KnowsRowCountTable#getRowCount()
     */
    public int getRowCount() {
        return m_buffer.size();
    }

    /** Get reference to buffer.
     * @return The buffer backing this object.
     */
    Buffer getBuffer() {
        return m_buffer;
    }

    /**
     * Do not call this method! Internal use!
     * @see KnowsRowCountTable#saveToFile(
     * File, NodeSettingsWO, ExecutionMonitor)
     */
    public void saveToFile(final File f, final NodeSettingsWO settings, 
            final ExecutionMonitor exec) throws IOException, 
            CanceledExecutionException {
        m_buffer.saveToFile(f, exec);
    }
    
    /**
     * Do not call this method! It's used internally to delete temp files. 
     * Any iteration on the table will fail!
     * @see KnowsRowCountTable#clear()
     */
    public void clear() {
        m_buffer.clear();
    }

}
