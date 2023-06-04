package put_requests;
import base_urls.DummyRestApiBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.DummyRestApiDataPojo;
import pojos.DummyRestApiResponsePojo;
import utils.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Put02 extends DummyRestApiBaseUrl {
        /*
    Given
         https://dummy.restapiexample.com/api/v1/update/21
    And
       Request body: {
                        "employee_name": "Ali Can",
                        "employee_salary": 111111,
                        "employee_age": 23,
                        "profile_image": "Perfect image"
                     }
    When
        User sends PUT request
    Then
        Status code should be 200
    And
        Response body should be like the following:
                    {
                        "status": "success",
                        "data": {
                            "employee_name": "Ali Can",
                            "employee_salary": 111111,
                            "employee_age": 23,
                            "profile_image": "Perfect image"
                        },
                        "message": "Successfully! Record has been updated."
                    }

     */

    @Test
    public void put02() {
        //Set the url
        spec.pathParams("first","update", "second", 21);

        //Set the expected data
        DummyRestApiDataPojo expectedData = new DummyRestApiDataPojo("Ali Can", 111111,23,"Perfect image");
        System.out.println("expectedData = " + expectedData);

        //Send the request and get the response
        Response response = given(spec).contentType(ContentType.JSON).body(expectedData).put("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        DummyRestApiResponsePojo actualData = ObjectMapperUtils.convertJsonToJava(response.asString(), DummyRestApiResponsePojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        //Put requestte ne gönderildiyse response'ta onu anu aramalıyız.
        assertEquals(expectedData.getEmployee_name(), actualData.getData().getEmployee_name());
        assertEquals(expectedData.getEmployee_salary(), actualData.getData().getEmployee_salary());
        assertEquals(expectedData.getEmployee_age(), actualData.getData().getEmployee_age());
        assertEquals(expectedData.getProfile_image(), actualData.getData().getProfile_image());

        //Tüm body assert edilecekse
        assertEquals("success",actualData.getStatus());
        assertEquals("Successfully! Record has been updated.", actualData.getMessage());

    }
}