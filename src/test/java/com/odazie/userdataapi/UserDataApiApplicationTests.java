package com.odazie.userdataapi;

import com.odazie.userdataapi.data.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDataApiApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }


    @Test
    public void testGetAllUsers(){
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(getRootUrl()+"users", HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testGetUserById(){
        User user = testRestTemplate.getForObject(getRootUrl() +"/users/1", User.class );
        System.out.println(user.getFirstName());

        Assert.assertNotNull(user);

    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmailAddress("admin@gmail.com");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");

        ResponseEntity<User> postResponse = testRestTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }


    @Test
    public void testUpdateUser() {
        int id = 1;
        User user = testRestTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        user.setFirstName("admin1");
        user.setLastName("admin2");

        testRestTemplate.put(getRootUrl() + "/users/" + id, user);

        User updatedUser = testRestTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        Assert.assertNotNull(updatedUser);
    }

    @Test
    public void testDeletePost() {
        int id = 2;
        User user = testRestTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        Assert.assertNotNull(user);

        testRestTemplate.delete(getRootUrl() + "/users/" + id);

        try {
            user = testRestTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
