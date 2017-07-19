package duorourou.restful.test.utils.comparator.result;

public class FieldNotExistResult extends CompareResult<String> {

    private FieldNotExistResult(String fieldName) {
        super(fieldName);
    }

    public static FieldNotExistResult build(String fieldName) {
        return new FieldNotExistResult(fieldName);
    }
}
