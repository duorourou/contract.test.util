package duorourou.restful.test.utils.comparator.response.body;

public class DefaultBodyComparator extends BodyComparator {

    @Override
    public boolean fieldCheckingAssertion(String currentPath, String currentFieldName) {
        return true;
    }
}
