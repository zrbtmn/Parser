package Parser.UI;

public class ParameterPair {

    private String key;
    private String value;

    public ParameterPair(String key, String value){
        this.key = key;
        this.value = value;
    }

    String getKey(){
        return key;
    }

    String getValue() {
        return value;
    }

    <X> X getClassifiedValue(Class<X> xClass) throws InstantiationException {
        try {
            return xClass.getDeclaredConstructor(String.class).newInstance(value);
        } catch (Exception ex) {
            throw new InstantiationException(String.format("Impossible to cast \"%s\" in %s", value, xClass.getName()));
        }
    }
}