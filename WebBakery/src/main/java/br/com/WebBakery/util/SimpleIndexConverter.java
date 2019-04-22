package br.com.WebBakery.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@FacesConverter("generic")
public class SimpleIndexConverter implements Converter {

    private int index = -1;

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.
     * FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            SelectItem selectedItem = this.getSelectedItemByIndex(component,
                                                                  Integer.parseInt(value));
            if (selectedItem != null) {
                this.index = -1;
                return selectedItem.getValue();
            }
        }
        this.index = -1;
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.
     * FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        if (this.index == -1) {
            this.index++;

            String retorno = String
                    .valueOf(value != null ? this.getIndexOfSelectedItem(component, value) : 0);

            if (this.index == (this.getSelectItems(component).size() - 1)) {
                this.index = -1;
            }

            return retorno;
        } else {
            this.index++;

            String retorno = String.valueOf(this.index);

            if (this.index == (this.getSelectItems(component).size() - 1)) {
                this.index = -1;
            }

            return retorno;
        }
    }

    /**
     * Obtem o indice do valor selecionado
     * 
     * @param component
     * @param value
     * @return
     *
     * @author TECBMJNA
     * @created 16/06/2010 18:04:09
     * @version 1.0.0.0
     */
    protected int getIndexOfSelectedItem(UIComponent component, Object value) {
        List<SelectItem> items = this.getSelectItems(component);

        for (SelectItem selectItem : items) {
            if (value.equals(selectItem.getValue())) {
                return items.indexOf(selectItem);
            }
        }

        return -1;
    }

    /**
     * Obtem o SelecItem de acordo com a opção selecionada pelo usuário
     */
    protected SelectItem getSelectedItemByIndex(UIComponent component, int index) {

        List<SelectItem> items = this.getSelectItems(component);
        int size = items.size();

        if (index > -1 && size > index) {
            return items.get(index);
        }

        return null;
    }

    protected List<SelectItem> getSelectItems(UIComponent component) {

        List<SelectItem> items = new ArrayList<SelectItem>();
        int childCount = component.getChildCount();
        if (childCount == 0) {
            return items;
        }

        List<UIComponent> children = component.getChildren();
        for (UIComponent child : children) {
            if (child instanceof UISelectItem) {
                this.addSelectItem((UISelectItem) child, items);
            } else if (child instanceof UISelectItems) {
                this.addSelectItems((UISelectItems) child, items);
            }
        }

        return items;
    }

    protected void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {

        boolean isRendered = uiItem.isRendered();
        if (!isRendered) {
            items.add(null);
            return;
        }

        Object value = uiItem.getValue();
        SelectItem item;

        if (value instanceof SelectItem) {
            item = (SelectItem) value;
        } else {
            Object itemValue = uiItem.getItemValue();
            String itemLabel = uiItem.getItemLabel();
            // JSF throws a null pointer exception for null values and labels,
            // which is a serious problem at design-time.
            item = new SelectItem(itemValue,
                                  itemLabel,
                                  uiItem.getItemDescription(),
                                  uiItem.isItemDisabled());
        }

        items.add(item);
    }

    @SuppressWarnings("unchecked")
    protected void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {

        boolean isRendered = uiItems.isRendered();
        if (!isRendered) {
            items.add(null);
            return;
        }

        Object value = uiItems.getValue();
        if (value instanceof SelectItem) {
            items.add((SelectItem) value);
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            for (int i = 0; i < array.length; i++) {
                if (array[i] instanceof SelectItemGroup) {
                    this.resolveAndAddItems((SelectItemGroup) array[i], items);
                } else {
                    items.add((SelectItem) array[i]);
                }
            }
        } else if (value instanceof Collection) {
            Iterator<SelectItem> iter = ((Collection<SelectItem>) value).iterator();
            SelectItem item;
            while (iter.hasNext()) {
                item = iter.next();
                if (item instanceof SelectItemGroup) {
                    this.resolveAndAddItems((SelectItemGroup) item, items);
                } else {
                    items.add(item);
                }
            }
        } else if (value instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                Object label = entry.getKey();
                SelectItem item = new SelectItem(entry.getValue(),
                                                 label == null ? (String) null : label.toString());
                if (item instanceof SelectItemGroup) {
                    this.resolveAndAddItems((SelectItemGroup) item, items);
                } else {
                    items.add(item);
                }
            }
        }
    }

    protected void resolveAndAddItems(SelectItemGroup group, List<SelectItem> items) {
        for (SelectItem item : group.getSelectItems()) {
            if (item instanceof SelectItemGroup) {
                this.resolveAndAddItems((SelectItemGroup) item, items);
            } else {
                items.add(item);
            }
        }
    }

}
