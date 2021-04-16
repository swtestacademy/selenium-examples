package junit4listeners;

import java.util.Date;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class MyExecutionListener extends RunListener {

    //Start and End time of the tests
    long startTime;
    long endTime;

    @Override
    public void testRunStarted(Description description) {
        //Start time of the tests
        startTime = new Date().getTime();
        //Print the number of tests before the all tests execution.
        System.out.println("Tests started! Number of Test case: " + description.testCount() + "\n");
    }

    @Override
    public void testRunFinished(Result result) {
        //End time of the tests
        endTime = new Date().getTime();
        //Print the below lines when all tests are finished.
        System.out.println("Tests finished! Number of test case: " + result.getRunCount());
        long elapsedSeconds = (endTime - startTime) / 1000;
        System.out.println("Elapsed time of tests execution: " + elapsedSeconds + " seconds");
    }

    @Override
    public void testStarted(Description description) {
        //Write the test name when it is started.
        System.out.println(description.getMethodName() + " test is starting...");
    }

    @Override
    public void testFinished(Description description) {
        //Write the test name when it is finished.
        System.out.println(description.getMethodName() + " test is finished...\n");
    }

    @Override
    public void testFailure(Failure failure) {
        //Write the test name when it is failed.
        System.out.println(failure.getDescription().getMethodName() + " test FAILED!!!");
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
        Ignore ignore = description.getAnnotation(Ignore.class);
        String ignoreMessage = String.format(
            "@Ignore test method '%s()': '%s'",
            description.getMethodName(), ignore.value());
        System.out.println(ignoreMessage + "\n");
    }
}
