package org.ramki.order;

import junit.framework.Assert;
import org.junit.Test;
import org.ramki.order.OrderUpdate;

/**
 * Tests for org.ramki.order.OrderUpdate
 */
public class OrderUpdateTest {
    @Test
    public void testValidate() throws Exception {
        Assert.assertTrue("valid update object failing validate", new OrderUpdate(100, 100, "NEW", 50).validate());
        Assert.assertFalse("update object with missing order ID", new OrderUpdate(null, 100, "NEW", 50).validate());
        Assert.assertFalse("update object with missing update ID", new OrderUpdate(100, null, "NEW", 50).validate());
        Assert.assertTrue("update object with null amount", new OrderUpdate(100, 200, "COOKING", null).validate());
        Assert.assertFalse("update object with null amount for NEW", new OrderUpdate(100, 200, "NEW", null).validate());

        // validate all good states
        for (String s : OrderUpdate.VALID_STATUS) {
            Assert.assertTrue("update object in " + s, new OrderUpdate(100, 200, s, 20).validate());
        }
        // validate invalid state
        Assert.assertFalse("update object with missing state", new OrderUpdate(100, 200, null, 50).validate());
        Assert.assertFalse("update object with invalid state", new OrderUpdate(100, 200, "ABC", 50).validate());
    }
}
