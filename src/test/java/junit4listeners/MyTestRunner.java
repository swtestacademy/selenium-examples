package junit4listeners;

import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class MyTestRunner extends BlockJUnit4ClassRunner {

    public MyTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        System.out.println("Executing run()");
        //Add Listener. This will register our JUnit Listener.
        notifier.addListener(new MyExecutionListener());

        //Get each test notifier
        EachTestNotifier testNotifier = new EachTestNotifier(notifier,
            getDescription());
        try {
            //In order capture testRunStarted method
            //at the very beginning of the test run, we will add below code.
            //Invoke here the run testRunStarted() method
            notifier.fireTestRunStarted(getDescription());
            Statement statement = classBlock(notifier);
            statement.evaluate();
        }
        catch (AssumptionViolatedException e) {
            testNotifier.fireTestIgnored();
        }
        catch (StoppedByUserException e) {
            throw e;
        }
        catch (Throwable e) {
            testNotifier.addFailure(e);
        }
    }
}