package com.example.financeapp.user;

import com.example.financeapp.modules.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserTest {

    @Autowired
    private JacksonTester<User> tester;

    @Test
    void testSerialization() throws IOException {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        User user = new User("name", "surname", "email@mail.com",
                12345, "street", "12", "12345", "city", "country", "TAX123TestWerzahltschonSteuern",
                "01.02.1995" );
        JsonContent<User> content = tester.write(user);
        assertThat(content).hasJsonPathStringValue("userId", uuid.toString());

    }
}
