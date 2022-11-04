package model.server;

import org.example.services.StorageService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageServiceTests {

    @Test
    public void singletonTest() {
        StorageService s1 = StorageService.StorageService("/home/usr");
        StorageService s2 = StorageService.StorageService("/home/usr");
        assertEquals(s1,s2);
    }

    @Test
    public void cantModifyPath() {
        StorageService s1 = StorageService.StorageService("/home/usr");
        assertThrows(IllegalArgumentException.class, () -> {
            StorageService s2 = StorageService.StorageService("/home/usr2");
        });
        assertEquals(s1.getPath(), "/home/usr");
    }
}
