package community.fides.bluepages.backend.utils;

public class EqualUtils {

    public static Boolean functionalEquals(final Object item1, final Object item2) {
        if ((item1 == null) && (item2 == null)) {
            return true;
        }
        if ((item1 == null) || (item2 == null)){
            return false;
        }
        return item1.equals(item2);
    }

}
