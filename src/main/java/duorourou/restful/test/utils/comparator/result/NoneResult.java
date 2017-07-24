package duorourou.restful.test.utils.comparator.result;

public class NoneResult extends CompareResult {
    private static final NoneResult NONE_RESULT = new NoneResult();

    public static CompareResult instance() {
        return NONE_RESULT;
    }

    @Override
    public String toString() {
        return null;
    }
}
