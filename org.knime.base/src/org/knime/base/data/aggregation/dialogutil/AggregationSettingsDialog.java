/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 * Created on 18.12.2012 by koetter
 */
package org.knime.base.data.aggregation.dialogutil;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.KNIMEConstants;
import org.knime.core.node.NodeSettings;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.port.database.aggregation.AggregationFunction;

/**
 * {@link JDialog} that displays and allows the editing of the additional parameters of an
 * {@link AggregationFunction}.
 * @author Tobias Koetter, KNIME.com, Zurich, Switzerland
 * @since 2.11
 */
public class AggregationSettingsDialog extends JDialog {

    /**This is the first version of the dialog.*/
    private static final long serialVersionUID = 1L;
    private final AggregationFunction m_method;


    /**
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param method the aggregation method the parameters should be edited
     * @param spec the table spec of the table the method will be applied to
     * @throws NotConfigurableException if the input spec does not satisfies all requirements
     */
    public AggregationSettingsDialog(final Frame owner, final AggregationFunction method,
                                      final DataTableSpec spec) throws NotConfigurableException {
        this(owner, true, " Aggregation Settings", method, spec);
    }

    /**
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless.
     * @param title  the {@code String} to display in the dialog's
     *     title bar
     * @param method the aggregation method the parameters should be edited
     * @param spec the table spec of the table the method will be applied to
     * @throws NotConfigurableException if the input spec does not satisfies all requirements
     */
    public AggregationSettingsDialog(final Frame owner, final boolean modal, final String title,
                                      final AggregationFunction method,
                                      final DataTableSpec spec) throws NotConfigurableException {
        super(owner, title, modal);
        if (method == null) {
            throw new NullPointerException("method must not be null");
        }
        if (!method.hasOptionalSettings()) {
            throw new IllegalArgumentException("Aggregation method has no optional settings");
        }
        if (spec == null) {
            throw new NullPointerException("spec must not be null");
        }
        m_method = method;
        if (KNIMEConstants.KNIME16X16 != null) {
            setIconImage(KNIMEConstants.KNIME16X16.getImage());
        }

        //save the initial settings to restore them on cancel and to initialize the dialog
        final NodeSettings initialSettings = new NodeSettings("tmp");
        m_method.saveSettingsTo(initialSettings);
        //load the default settings including the input table spec
        //to initialize the dialog panel
        m_method.loadSettingsFrom(initialSettings, spec);

        final JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        rootPanel.add(m_method.getSettingsPanel(), gc);

        //buttons
        gc.anchor = GridBagConstraints.LINE_END;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.ipadx = 20;
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets = new Insets(0, 10, 10, 0);
        final JButton okButton = new JButton("OK");
        final ActionListener okActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (validateSettings()) {
                    closeDialog();
                }
            }
        };
        okButton.addActionListener(okActionListener);
        rootPanel.add(okButton, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.weightx = 0;
        gc.ipadx = 10;
        gc.gridx = 1;
        gc.insets = new Insets(0, 5, 10, 10);
        final JButton cancelButton = new JButton("Cancel");
        final ActionListener cancelActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                onCancel(initialSettings);
            }
        };
        cancelButton.addActionListener(cancelActionListener);
        rootPanel.add(cancelButton, gc);
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent we) {
                //handle all window closing events triggered by none of
                //the given buttons
                onCancel(initialSettings);
            }
        });
        setMinimumSize(new Dimension(240, 135));
        pack();
    }

    private boolean validateSettings() {
        try {
            m_method.validate();
            return true;
        } catch (InvalidSettingsException e) {
            //show the error message
            JOptionPane.showMessageDialog(this, e.getMessage(),
                              "Invalid Settings", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * @param initialSettings the initial settings
     */
    private void onCancel(final NodeSettingsRO initialSettings) {
        //reset the settings
        try {
            m_method.loadValidatedSettings(initialSettings);
        } catch (InvalidSettingsException e) {
            //this should not happen
        }
        closeDialog();
    }

    /**
     * @param dialog the dialog to close
     */
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
}