import org.junit.Test;

import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class Methode1Test {
	
	@Test
	public void test() throws Exception {
	    SoapUITestCaseRunner testCaseRunner = new SoapUITestCaseRunner();
	    testCaseRunner.setProjectFile("src/test/resources/NOAA2-soapui-project.xml");
	    try {
	        testCaseRunner.run();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
