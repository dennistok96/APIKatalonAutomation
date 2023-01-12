import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable as GlobalVariable
import org.assertj.core.api.Assertions as Assertions
import org.openqa.selenium.Keys as Keys

int page = 1

List<TestObjectProperty> perPageParam = new ArrayList()

perPageParam.add(1)

perPageParam.add(5)

perPageParam.add(20)

for (int i = 0; i < perPageParam.size(); i++) {
    RequestObject requestObject = findTestObject('Object Repository/API/GetDataBySizeParameter', [('page') : page, ('per_page') : perPageParam.get(
                i)])

    TestObjectProperty header = new TestObjectProperty('Accept', ConditionType.EQUALS, 'application/json')

    requestObject.setHttpHeaderProperties(Arrays.asList(header))

    ResponseObject responseObject = WS.sendRequest(requestObject)

    System.out.println(responseObject.getResponseText())

    JsonSlurper parser = new JsonSlurper()

    def responseParser = parser.parseText(responseObject.getResponseBodyContent())


    Assertions.assertThat(responseObject.getStatusCode()).isEqualTo(200)

    Assertions.assertThat(responseParser.results.size()).isEqualTo(perPageParam.get(i))
}

