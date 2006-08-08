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
 *   Nov 16, 2005 (Kilian Thiel): created
 */
package org.knime.base.node.mine.sota.dialog;

import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.knime.base.node.mine.sota.SotaConfigKeys;
import org.knime.base.node.mine.sota.SotaManager;
import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * 
 * @author Kilian Thiel, University of Konstanz
 */
public class SotaSettingsPanel extends JPanel {
    private final double m_learningrateINCR = 0.01;

    private final double m_variabilityINCR = 0.01;

    private final double m_resourceINCR = 0.01;

    private final double m_errorINCR = 0.01;

    private JSpinner m_jsLearningRateWinner;

    private JSpinner m_jsLearningRateSister;

    private JSpinner m_jsLearningRateAncestor;

    private JSpinner m_jsVariability;

    private JSpinner m_jsResource;

    private JSpinner m_jsError;

    private JCheckBox m_jchbUseVariability;

    private NodeLogger m_logger;

    private JComboBox m_jcbDistance;

    private int m_width = 8;

    /**
     * Constructor.
     * 
     * @param logger the NodeLogger object to use for logging
     */
    public SotaSettingsPanel(final NodeLogger logger) {
        super(new GridLayout(8, 2));

        m_logger = logger;

        m_jsLearningRateWinner = new JSpinner(new SpinnerNumberModel(
                SotaManager.LR_WINNER, SotaManager.LR_WINNER_MIN,
                SotaManager.LR_WINNER_MAX, m_learningrateINCR));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor)m_jsLearningRateWinner
                .getEditor();
        editor.getTextField().setColumns(m_width);

        m_jsLearningRateSister = new JSpinner(new SpinnerNumberModel(
                SotaManager.LR_SISTER, SotaManager.LR_SISTER_MIN,
                SotaManager.LR_SISTER_MAX, m_learningrateINCR));
        editor = (JSpinner.DefaultEditor)m_jsLearningRateSister.getEditor();
        editor.getTextField().setColumns(m_width);

        m_jsLearningRateAncestor = new JSpinner(new SpinnerNumberModel(
                SotaManager.LR_ANCESTOR, SotaManager.LR_ANCESTOR_MIN,
                SotaManager.LR_ANCESTOR_MAX, m_learningrateINCR));
        editor = (JSpinner.DefaultEditor)m_jsLearningRateAncestor.getEditor();
        editor.getTextField().setColumns(m_width);

        m_jsVariability = new JSpinner(new SpinnerNumberModel(
                SotaManager.MIN_VARIABILITY, SotaManager.MIN_VARIABILITY_MIN,
                SotaManager.MIN_VARIABILITY_MAX, m_variabilityINCR));
        editor = (JSpinner.DefaultEditor)m_jsVariability.getEditor();
        editor.getTextField().setColumns(m_width);

        m_jsResource = new JSpinner(new SpinnerNumberModel(
                SotaManager.MIN_RESOURCE, SotaManager.MIN_RESOURCE_MIN,
                SotaManager.MIN_RESOURCE_MAX, m_resourceINCR));
        editor = (JSpinner.DefaultEditor)m_jsResource.getEditor();
        editor.getTextField().setColumns(m_width);

        m_jsError = new JSpinner(new SpinnerNumberModel(SotaManager.MIN_ERROR,
                SotaManager.MIN_ERROR_MIN, SotaManager.MIN_ERROR_MAX,
                m_errorINCR));
        editor = (JSpinner.DefaultEditor)m_jsError.getEditor();
        editor.getTextField().setColumns(m_width);

        m_jchbUseVariability = new JCheckBox();
        m_jchbUseVariability.setSelected(SotaManager.USE_VARIABILITY);

        m_jcbDistance = new JComboBox();
        m_jcbDistance.addItem(SotaManager.EUCLIDEAN_DIST);
        m_jcbDistance.addItem(SotaManager.COS_DIST);
        m_jcbDistance.setSelectedItem(SotaManager.EUCLIDEAN_DIST);

        add(new JLabel("Winner learningrate"));
        add(m_jsLearningRateWinner);

        add(new JLabel("Sister learningrate"));
        add(m_jsLearningRateSister);

        add(new JLabel("Ancestor learningrate"));
        add(m_jsLearningRateAncestor);

        add(new JLabel("Minimal variability"));
        add(m_jsVariability);

        add(new JLabel("Minimal resource"));
        add(m_jsResource);

        add(new JLabel("Minimal error"));
        add(m_jsError);

        add(new JLabel("Use variability"));
        add(m_jchbUseVariability);

        add(new JLabel("Distance metric"));
        add(m_jcbDistance);
    }

    /**
     * Method loadSettingsFrom.
     * 
     * @param settings the NodeSettings object of the containing NodeDialogPane
     * @param specs the DataTableSpec[] of the containing NodeDialogPane
     */
    public void loadSettingsFrom(final NodeSettingsRO settings,
            final DataTableSpec[] specs) {
        assert (settings != null && specs != null);

        try {
            m_jsLearningRateWinner.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_LR_WINNER)));
        } catch (InvalidSettingsException e1) {
            m_logger.debug("Invalid Settings", e1);
        }

        try {
            m_jsLearningRateSister.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_LR_SISTER)));
        } catch (InvalidSettingsException e2) {
            m_logger.debug("Invalid Settings", e2);
        }

        try {
            m_jsLearningRateAncestor.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_LR_ANCESTOR)));
        } catch (InvalidSettingsException e3) {
            m_logger.debug("Invalid Settings", e3);
        }

        try {
            m_jsError.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_MIN_ERROR)));
        } catch (InvalidSettingsException e4) {
            m_logger.debug("Invalid Settings", e4);
        }

        try {
            m_jsVariability.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_VARIABILITY)));
        } catch (InvalidSettingsException e5) {
            m_logger.debug("Invalid Settings", e5);
        }

        try {
            m_jsResource.getModel().setValue(
                    new Double(settings
                            .getDouble(SotaConfigKeys.CFGKEY_RESOURCE)));
        } catch (InvalidSettingsException e6) {
            m_logger.debug("Invalid Settings", e6);
        }

        try {
            m_jchbUseVariability.setSelected(settings
                    .getBoolean(SotaConfigKeys.CFGKEY_USE_VARIABILITY));
        } catch (InvalidSettingsException e7) {
            m_logger.debug("Invalid Settings", e7);
        }

        try {
            m_jcbDistance.setSelectedItem(settings
                    .getString(SotaConfigKeys.CFGKEY_USE_DISTANCE));
        } catch (InvalidSettingsException e8) {
            m_logger.debug("Invalid Settings", e8);
        }
    }

    /**
     * Saves all settings to settings object.
     * 
     * @param settings object to store settings in
     */
    public void saveSettingsTo(final NodeSettingsWO settings) {
        assert (settings != null);

        settings.addDouble(SotaConfigKeys.CFGKEY_LR_WINNER,
                getWinnerLearningrate());
        settings.addDouble(SotaConfigKeys.CFGKEY_LR_SISTER,
                getSisterLearningrate());
        settings.addDouble(SotaConfigKeys.CFGKEY_LR_ANCESTOR,
                getAncestorLearningrate());

        settings.addDouble(SotaConfigKeys.CFGKEY_MIN_ERROR, getMinimumError());
        settings.addDouble(SotaConfigKeys.CFGKEY_VARIABILITY,
                getMinimumVariability());
        settings
                .addDouble(SotaConfigKeys.CFGKEY_RESOURCE, getMinimumResource());

        settings.addBoolean(SotaConfigKeys.CFGKEY_USE_VARIABILITY,
                isUseVariability());

        settings.addString(SotaConfigKeys.CFGKEY_USE_DISTANCE,
                getDistanceMetric());
    }

    /**
     * Get selected value of Winner learningrate.
     * 
     * @return the selected value of Winner learningrate
     */
    private double getWinnerLearningrate() {
        try {
            m_jsLearningRateWinner.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsLearningRateWinner
                .getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Get selected value of Sister learningrate.
     * 
     * @return the selected value of Sister learningrate
     */
    private double getSisterLearningrate() {
        try {
            m_jsLearningRateSister.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsLearningRateSister
                .getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Get selected value of Ancestor learningrate.
     * 
     * @return the selected value of Ancestor learningrate
     */
    private double getAncestorLearningrate() {
        try {
            m_jsLearningRateAncestor.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsLearningRateAncestor
                .getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Get selected value of minimum error.
     * 
     * @return the selected value of minimum error
     */
    private double getMinimumError() {
        try {
            m_jsError.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsError.getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Get selected value of minimum variability.
     * 
     * @return the selected value of minimum variability
     */
    private double getMinimumVariability() {
        try {
            m_jsVariability.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsVariability.getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Get selected value of minimum resource.
     * 
     * @return the selected value of minimum resource
     */
    private double getMinimumResource() {
        try {
            m_jsResource.commitEdit();
        } catch (ParseException e) {
            // if the spinner has the focus, the currently edited value
            // might not be commited. Now it is!
        }
        SpinnerNumberModel snm = (SpinnerNumberModel)m_jsResource.getModel();
        return snm.getNumber().doubleValue();
    }

    /**
     * Returns true if use-variability checkbox is selected, else false.
     * 
     * @return True if use-variability checkbox is selected, else false
     */
    private boolean isUseVariability() {
        return m_jchbUseVariability.isSelected();
    }

    /**
     * Returns the selected distance metric.
     * 
     * @return The selected distance metric.
     */
    private String getDistanceMetric() {
        return (String)m_jcbDistance.getSelectedItem();
    }
}
