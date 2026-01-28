package mx.ine.procprimerinsa.util;

import java.util.List;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UISelectItem;
import jakarta.faces.component.UISelectItems;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("objectGenericConverter")
@Scope("request")
public class ObjectGenericConverter implements Converter {
		
		   @Override
		    public Object getAsObject(final FacesContext arg0, final UIComponent arg1, final String objectString) {
		        if (objectString == null) {
		            return null;
		        }
		 
		        return fromSelect(arg1, objectString);
		    }
		 
		   private String serialize(final Object object) {
		        if (object == null) {
		            return null;
		        }
		        return object.getClass() + "@" + object.hashCode();
		    }
		 
		   private Object fromSelect(final UIComponent currentcomponent, final String objectString) {
		 
		        if (currentcomponent.getClass() == UISelectItem.class) {
		            final UISelectItem item = (UISelectItem) currentcomponent;
		            final Object value = item.getValue();
		            final Object itemValue 	= item.getItemValue();
		            if (objectString.equals(serialize(value)))
		                return value;
		            else if(objectString.equals(serialize(itemValue)))
		            	return itemValue;
		        }
		 
		        if (currentcomponent.getClass() == UISelectItems.class) {
		            final UISelectItems items = (UISelectItems) currentcomponent;
		            final List<Object> elements = (List<Object>) items.getValue();
		            for (final Object element : elements) {
		                if (objectString.equals(serialize(element))) {
		                    return element;
		                }
		            }
		        }
		 
		 
		        if (!currentcomponent.getChildren().isEmpty()) {
		            for (final UIComponent component : currentcomponent.getChildren()) {
		                final Object result = fromSelect(component, objectString);
		                if (result != null) {
		                    return result;
		                }
		            }
		        }
		        return null;
		    }
		 
		    @Override
		    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object object) {
		        return serialize(object);
		    }
		 
		}