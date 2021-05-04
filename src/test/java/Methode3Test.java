import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.support.SoapUIException;

@RunWith(Parameterized.class)
public class Methode3Test {

    private String testCaseName;
    private static String soapuiProjectName = "src/test/resources/NOAA2-soapui-project.xml";

    public Methode3Test(String testCaseName) {
        this.testCaseName = testCaseName;
    }   

    @Parameters(name="{0}")
    public static Collection<String[] > getTestCases() throws XmlException, IOException, SoapUIException {
        final ArrayList<String[]>  testCases = new ArrayList<String[]>();
        WsdlProject soapuiProject = new WsdlProject(soapuiProjectName);
        WsdlTestSuite wsdlTestSuite = soapuiProject.getTestSuiteByName("TestSuite 2");
        List<TestCase> testCaseStrings = wsdlTestSuite.getTestCaseList();

        for (TestCase ts : testCaseStrings) {
            if (!ts.isDisabled()) {
                testCases.add(new String[] { ts.getName() });
            }
        }
        return testCases;
    }

    @Test
    public void testSoapUITestCase() throws XmlException, IOException, SoapUIException {
        System.out.println("Nom du cas de test SoapUI : " + testCaseName);
        assertTrue(true);
        assertTrue(runSoapUITestCase(this.testCaseName));
    }

    public static boolean runSoapUITestCase(String testCase) throws XmlException, IOException, SoapUIException {
        TestRunner.Status exitValue = TestRunner.Status.INITIALIZED;
        WsdlProject soapuiProject = new WsdlProject(soapuiProjectName);
        WsdlTestSuite testSuite = soapuiProject.getTestSuiteByName("TestSuite 2");
        if (testSuite == null) {
            System.err.println("runner soapUI, la suite de test est null : " + testSuite);
            return false;
        }
        WsdlTestCase soapuiTestCase = testSuite.getTestCaseByName(testCase);
        if (soapuiTestCase == null) {
            System.err.println("runner soapUI, le cas de test est null : " + testCase);
            return false;
        }
        soapuiTestCase.setDiscardOkResults(true);
        WsdlTestCaseRunner runner = soapuiTestCase.run(new PropertiesMap(), false);
        exitValue = runner.getStatus();

        System.out.println("cas de test soapUI terminé ('" + testSuite + "':'" + testCase + "') : " + exitValue);
        if (exitValue == TestRunner.Status.FINISHED) {
            return true;
        } else {
            return false;
        }
    }
}