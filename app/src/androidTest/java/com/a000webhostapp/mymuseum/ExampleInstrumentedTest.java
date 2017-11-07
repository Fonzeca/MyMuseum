package com.a000webhostapp.mymuseum;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        System.out.println("aaaaaaaaaaaaaaaaaaaaa");
        Uri.Builder builder = new Uri.Builder();
        builder.path("/public_html/images/");
        builder.appendPath("Invento" + "Monalisa" + ".png");
        builder.query("BUSCAR");
        Uri uri = builder.build();
    
        Log.v("TESTTTTTT", uri.getPath());
		Log.v("TESTTTTTT", uri.getQuery());
    }
}
