package com.standup.tortoise.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jiang Biao 2019/5/29
 */

public class ReadSourceUtils {

    public static InputStream getInputStream(Class<?> basePathClazz, String resourceName) throws IOException {

        return basePathClazz.getResource(resourceName).openStream();

    }



}
