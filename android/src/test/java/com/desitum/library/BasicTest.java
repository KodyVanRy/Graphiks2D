package com.desitum.library;

import com.desitum.library.view.View;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kodyvanry on 7/3/17.
 */

public class BasicTest {

    @Test
    public void testMy() {
        assertNotNull(new View(null, null));
    }
}
