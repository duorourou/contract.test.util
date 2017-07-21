package duorourou.restful.test.utils;

import duorourou.restful.test.utils.assertion.Assertion;
import duorourou.restful.test.utils.cases.TestCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private static final int INVOKE_METHOD_INDEX = 2;
    private static final String TEST_CASES_PATH = "src/test/resources/test-cases";
    private static final TestCases TEST_CASES = TestCases.build(TEST_CASES_PATH);

    public BaseTest() {
    }

    public void executeAssertion(String caseName) {

    }

    public void executeAssertion() {
        String methodName = Thread.currentThread().getStackTrace()[INVOKE_METHOD_INDEX].getMethodName();
        execute(methodName);
    }

    public void executeAssertion(List<String> includeFieldsOrPaths) {

    }

    public void executeAssertion(String caseName, List<String> includeFieldsOrPaths) {

    }

    private void execute(String caseName) {
        new Assertion().assertion(TEST_CASES.getCase(caseName));
    }
}
