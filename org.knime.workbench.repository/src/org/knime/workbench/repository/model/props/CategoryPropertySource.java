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
 *   16.03.2005 (georg): created
 */
package org.knime.workbench.repository.model.props;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import org.knime.workbench.repository.model.Category;

/**
 * Property Source for a "Category" object.
 * 
 * @author Florian Georg, University of Konstanz
 */
public class CategoryPropertySource implements IPropertySource {
    private static final PropertyDescriptor NAME_DESC = new PropertyDescriptor(
            "name", "Name");

    private static final PropertyDescriptor DESC_DESC = new PropertyDescriptor(
            "description", "Description");

    private static final PropertyDescriptor ID_DESC = new PropertyDescriptor(
            "id", "ID");

    private static final PropertyDescriptor ICON_DESC = new PropertyDescriptor(
            "icon", "Icon");

    private static final PropertyDescriptor CAT_DESC = new PropertyDescriptor(
            "category", "Category path");

    private static final PropertyDescriptor AFTER_DESC = new PropertyDescriptor(
            "after", "After");

    private static final String CATEGORY_INFO = "Info";

    private static final String CATEGORY_PATH = "Path";

    private Category m_category;

    static {
        NAME_DESC.setCategory(CATEGORY_INFO);
        DESC_DESC.setCategory(CATEGORY_INFO);

        ICON_DESC.setCategory(CATEGORY_INFO);

        CAT_DESC.setCategory(CATEGORY_PATH);
        ID_DESC.setCategory(CATEGORY_PATH);

        AFTER_DESC.setCategory(CATEGORY_PATH);
    }

    /**
     * Constructs a new property source for the category.
     * 
     * @param model The category that is wrapped by this property source
     */
    public CategoryPropertySource(final Category model) {
        m_category = model;
    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue() {
        return this;
    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#
     *      getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {ID_DESC, NAME_DESC, DESC_DESC,
                CAT_DESC, ICON_DESC, AFTER_DESC};
    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#
     *      getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(final Object id) {
        if ("name".equals(id)) {
            return m_category.getName();
        }
        if ("id".equals(id)) {
            return m_category.getID();
        }
        if ("description".equals(id)) {
            return m_category.getDescription();
        }
        if ("category".equals(id)) {
            return m_category.getPath();
        }
        if ("icon".equals(id)) {
            return m_category.getIconDescriptor();
        }
        if ("after".equals(id)) {
            return m_category.getIconDescriptor();
        }

        // this should not happen
        assert false : "Unknown property id: " + id;
        return null;
    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#
     *      isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(final Object id) {
        return false;
    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#
     *      resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(final Object id) {

    }

    /**
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#
     *      setPropertyValue(java.lang.Object, java.lang.Object)
     */
    public void setPropertyValue(final Object id, final Object value) {
        // TODO Auto-generated method stub
    }
}
