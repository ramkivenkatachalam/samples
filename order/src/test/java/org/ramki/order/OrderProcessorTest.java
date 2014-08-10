package org.ramki.order;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Tests for org.ramki.order.OrderProcessor
 */
public class OrderProcessorTest {

    @Test
    public void testParseUpdateGoodInput() throws IOException {
        String updateString =
                "{\"orderId\": 100, \"status\": \"NEW\", \"updateId\": 287, \"amount\": 20} \n" +
                        "{\"orderId\": 100, \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7} \n" +
                        "{\"orderId\": 100, \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7, \"bad value\": 274974 } \n" +
                        "{\"orderId\": 101, \"status\": \"NEW\", \"updateId\": 289, \"amount\": 13}\n" +
                        "{\"orderId\": 100, \"status\": \"DELIVERING\", \"updateId\": 294}\n" +
                        "{\"orderId\": 102, \"status\": \"NEW\", \"updateId\": 291, \"amount\": 17}\n" +
                        "{\"orderId\": 101, \"status\": \"CANCELED\", \"updateId\": 290}";
        ByteArrayInputStream is = new ByteArrayInputStream(updateString.getBytes());

        // read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        OrderProcessor processor = new OrderProcessor();
        String line;
        while ((line = br.readLine()) != null) {
            OrderUpdate update = processor.parseUpdate(line);
            Assert.assertTrue(update.validate());
        }
    }

    @Test
    public void testParseUpdateBadInput() throws IOException {
        String updateString =
                "{} \n" +
                        "{\"orderId\": , \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7}\n" +
                        "{\"orderId\": 10.5, \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7}\n" +
                        "{\"orderId\": 100, \"updateId\": 287, \"amount\": 20} \n" +
                        "{\"orderId\": 100, \"status\": \"NEW\",  \"amount\": 20} \n" +
                        "{\"orderId\": 100, \"status\": \"NEW\", \"updateId\": 287} \n" +
                        "{\"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7}";

        ByteArrayInputStream is = new ByteArrayInputStream(updateString.getBytes());

        // read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        OrderProcessor processor = new OrderProcessor();
        String line;
        while ((line = br.readLine()) != null) {
            OrderUpdate update = processor.parseUpdate(line);
            Assert.assertFalse(update.validate());
        }
    }


    @Test
    public void testProcessOrderStream() throws Exception {
        String updateString =
                "{\"orderId\": 100, \"status\": \"NEW\", \"updateId\": 287, \"amount\": 20} \n" +
                        "{\"orderId\": 100, \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7} \n" +
                        "{\"orderId\": 100, \"status\": \"COOKING\", \"updateId\": 289, \"cookTime\": 7} \n" +
                        "{\"orderId\": 101, \"status\": \"NEW\", \"updateId\": 289, \"amount\": 13}\n" +
                        "{\"orderId\": 100, \"status\": \"DELIVERING\", \"updateId\": 294}\n" +
                        "{\"orderId\": 102, \"status\": \"NEW\", \"updateId\": 291, \"amount\": 17}\n" +
                        "{\"orderId\": 101, \"status\": \"CANCELED\", \"updateId\": 290}";
        ByteArrayInputStream is = new ByteArrayInputStream(updateString.getBytes());

        // read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        OrderProcessor processor = new OrderProcessor();
        processor.processOrderStream(br);
    }


}
