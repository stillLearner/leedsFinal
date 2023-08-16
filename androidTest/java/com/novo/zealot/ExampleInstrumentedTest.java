package com.novo.zealot;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.DB.SPSaveLogin;
import com.novo.zealot.DB.ZealotDBOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.novo.zealot", appContext.getPackageName());
    }

    @Test
    public void testDatabaseOperations() {
        // Get the application context
        Context appContext = InstrumentationRegistry.getTargetContext();

        // Create database helper
        ZealotDBOpenHelper dbOpenHelper = new ZealotDBOpenHelper(appContext, "test_db", null, 1);

        // Create a RunRecord object
        RunRecord record = new RunRecord();
        // Set the values for the record's fields, for example:
        record.setUuid(UUID.randomUUID().toString());
        record.setDate("2022-08-15");
        record.setDistance(5.0);
        record.setDuration(300);
        record.setAvgSpeed(10.0);
        record.setStartTime(new Date());
        record.setEndTime(new Date());

        // Test insertion
        assertTrue(dbOpenHelper.addRecord(record));

        // Test query
        List<RunRecord> records = dbOpenHelper.queryRecord();
        assertNotNull(records);
        assertFalse(records.isEmpty());

        // Test record content
        RunRecord retrievedRecord = records.get(0);
        assertEquals(record.getUuid(), retrievedRecord.getUuid());
        assertEquals(record.getDate(), retrievedRecord.getDate());
        // You can continue comparing other fields

        // Test deleting all data
        dbOpenHelper.deleteAllData();
        records = dbOpenHelper.queryRecord();
        assertTrue(records.isEmpty());

        // Close the database
        dbOpenHelper.close();
    }

    private Context appContext;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testSaveAndRetrieveUserInfo() {
        String username = "testUser";
        String password = "testPassword";
        String again = "testAgain";
        String gy = "testGy";
        String dy = "testDy";
        String age = "testAge";
        String sg = "testSg";
        String tz = "testTz";
        String xt = "testXt";

        // Save the user info
        SPSaveLogin.saveUserInfo(appContext, username, password, again, gy, dy, age, sg, tz, xt);

        // Retrieve the user info
        0.Map<String, String> retrievedUserInfo = SPSaveLogin.getUserInfo(appContext);

        // Verify the retrieved user info
        assertEquals(username, retrievedUserInfo.get(SPSaveLogin.KEY_USERNAME));
        assertEquals(password, retrievedUserInfo.get(SPSaveLogin.KEY_PASSWORD));
        assertEquals(again, retrievedUserInfo.get(SPSaveLogin.KEY_AGAIN));
        assertEquals(gy, retrievedUserInfo.get(SPSaveLogin.KEY_GY));
        assertEquals(dy, retrievedUserInfo.get(SPSaveLogin.KEY_DY));
        assertEquals(age, retrievedUserInfo.get(SPSaveLogin.KEY_AGE));
        assertEquals(sg, retrievedUserInfo.get(SPSaveLogin.KEY_SG));
        assertEquals(tz, retrievedUserInfo.get(SPSaveLogin.KEY_TZ));
        assertEquals(xt, retrievedUserInfo.get(SPSaveLogin.KEY_XT));
    }
}
