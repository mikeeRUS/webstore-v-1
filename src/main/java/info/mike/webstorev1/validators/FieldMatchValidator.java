package info.mike.webstorev1.validators;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();

    }
    /*
    org.springframework.beans.BeanWrapper is an interface and normally is not used directly.
    It is used by BeanFactory and DataBinder. BeanWrapper plays with java beans to manipulate it.
    It sets and gets value from java beans by the method BeanWrapper.setPropertyValue
    and BeanWrapper.getPropertyValue.
     */

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue =  new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = ( new BeanWrapperImpl(value).getPropertyValue(fieldMatch));

        boolean isValid = fieldValue == null && fieldMatchValue == null || fieldValue != null && fieldValue.equals(fieldMatchValue);

        if(!isValid){ //do wyjasnienia ;<
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode((fieldMatch)).addConstraintViolation();
        } //else {
            //return false;

        return isValid;
        }

    }
