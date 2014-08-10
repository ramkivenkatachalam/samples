package org.ramki.order;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for org.ramki.order.HashOrderStore
 */
public class HashOrderStoreTest {
    private HashOrderStore store = HashOrderStore.getInstance();

    @Before
    public void init() {
        store.clear();
    }
    
    @Test
    public void testStatsInit() throws Exception {
        Map<String, Number> stats = store.getStats();
        Assert.assertEquals("NEW not zero", 0, stats.get(OrderUpdate.STATUS_NEW));
        Assert.assertEquals("COOKING not zero", 0, stats.get(OrderUpdate.STATUS_COOKING));
        Assert.assertEquals("OrderUpdate.STATUS_DELIVERING not zero", 0, stats.get(OrderUpdate.STATUS_DELIVERING));
        Assert.assertEquals("deliveredCount not zero", 0, stats.get(OrderUpdate.STATUS_DELIVERED));
        Assert.assertEquals("canceledCount not zero", 0, stats.get(OrderUpdate.STATUS_CANCELED));
        Assert.assertEquals("refundedCount not zero", 0, stats.get(OrderUpdate.STATUS_REFUNDED));
        Assert.assertEquals("AMOUNT not zero", 0, stats.get("AMOUNT"));
    }

    /**
     *  exhaustively check all state transitions to make sure valid ones passes and invalid ones fails
     */
    @Test
    public void testCheckStateTransition() {
        // create a map of all valid state transitions
        Map<String, Collection<String>> transitions = new HashMap<String, Collection<String>>();
        transitions.put(OrderUpdate.STATUS_NEW, Arrays.asList(OrderUpdate.STATUS_COOKING, OrderUpdate.STATUS_CANCELED));
        transitions.put(OrderUpdate.STATUS_COOKING, Arrays.asList(OrderUpdate.STATUS_DELIVERING, OrderUpdate.STATUS_CANCELED));
        transitions.put(OrderUpdate.STATUS_DELIVERING, Arrays.asList(OrderUpdate.STATUS_DELIVERED, OrderUpdate.STATUS_CANCELED));
        transitions.put(OrderUpdate.STATUS_DELIVERED, Arrays.asList(OrderUpdate.STATUS_REFUNDED));
        transitions.put(OrderUpdate.STATUS_REFUNDED, Arrays.asList(new String[]{}));
        transitions.put(OrderUpdate.STATUS_CANCELED, Arrays.asList(new String[]{}));

        OrderUpdate prevUpdate = new OrderUpdate(100, 100, OrderUpdate.STATUS_NEW, 20);
        OrderUpdate currUpdate = new OrderUpdate(100, 200, OrderUpdate.STATUS_NEW, 20);

        for (String key : transitions.keySet()) {
            // make sure all invalid state transitions throw exception
            for (String allvalue : OrderUpdate.VALID_STATUS) {
                if (!transitions.get(key).contains(allvalue)) {
                    prevUpdate.setStatus(key);
                    currUpdate.setStatus(allvalue);
                    try {
                        store.checkStateTransition(prevUpdate, currUpdate);
                        throw new AssertionError("Invalid transition " + key + " -> " + allvalue + " passes");
                    } catch (OrderUpdateException e) {
                        System.out.println("Invalid transition " + key + " -> " + allvalue);
                    }

                }
            }
            // make sure all valid transition passes
            for (String value : transitions.get(key)) {
                prevUpdate.setStatus(key);
                currUpdate.setStatus(value);
                store.checkStateTransition(prevUpdate, currUpdate);
            }
        }
    }

    /**
     *  Test that complete duplicate update fails
     */
    @Test(expected = OrderUpdateException.class)
    public void testCheckUpdateDuplicateUpdate1() {
        OrderUpdate update1 = new OrderUpdate(200, 100, OrderUpdate.STATUS_NEW, 10);
        store.processUpdate(update1);
        store.checkUpdate(update1);
    }

    /**
     *  Test that duplicate updateID fails
     */
    @Test(expected = OrderUpdateException.class)
    public void testCheckUpdateDuplicateUpdate2() {
        OrderUpdate update1 = new OrderUpdate(200, 100, OrderUpdate.STATUS_NEW, 10);
        store.processUpdate(update1);
        OrderUpdate update2 = new OrderUpdate(200, 100, OrderUpdate.STATUS_COOKING, null);
        store.checkUpdate(update2);
    }

    /**
     * Make sure Out of Order Update fails
     */
    @Test(expected = OrderUpdateException.class)
    public void testCheckUpdateOutOfOrderUpdate() {
        OrderUpdate update1 = new OrderUpdate(200, 100, OrderUpdate.STATUS_NEW, 10);
        store.processUpdate(update1);
        OrderUpdate update2 = new OrderUpdate(200, 99, OrderUpdate.STATUS_COOKING, null);
        store.checkUpdate(update2);
    }

    /**
     * check that we reject orders that dont start in NEW
     */
    @Test(expected = OrderUpdateException.class)
    public void testCheckUpdateMissingNew() {
        store.checkUpdate(new OrderUpdate(200, 99, OrderUpdate.STATUS_COOKING, null));
    }

    
    /**
     * try a simple state transition and check stats
     */
    @Test
    public void testProcessUpdate1() {
        store.processUpdate(new OrderUpdate(200, 100, OrderUpdate.STATUS_NEW, 10));
        store.processUpdate(new OrderUpdate(200, 101, OrderUpdate.STATUS_COOKING, 10));
        Map<String, Number> stats = store.getStats();
        Assert.assertEquals("Bad AMOUNT", 10, stats.get("AMOUNT"));
        Assert.assertEquals("Bad NEW", 0, stats.get(OrderUpdate.STATUS_NEW));
        Assert.assertEquals("COOKING not zero", 1, stats.get(OrderUpdate.STATUS_COOKING));
        Assert.assertEquals("deliveringCount not zero", 0, stats.get(OrderUpdate.STATUS_DELIVERING));
        Assert.assertEquals("deliveredCount not zero", 0, stats.get(OrderUpdate.STATUS_DELIVERED));
        Assert.assertEquals("canceledCount not zero", 0, stats.get(OrderUpdate.STATUS_CANCELED));
        Assert.assertEquals("refundedCount not zero", 0, stats.get(OrderUpdate.STATUS_REFUNDED));
    }

    /**
     * try all state transitions and check stats
     */
    @Test
    public void testProcessUpdate2() {
        store.processUpdate(new OrderUpdate(1, 1, OrderUpdate.STATUS_NEW, 1));

        store.processUpdate(new OrderUpdate(2, 1, OrderUpdate.STATUS_NEW, 2));
        store.processUpdate(new OrderUpdate(2, 2, OrderUpdate.STATUS_COOKING, null));

        store.processUpdate(new OrderUpdate(3, 1, OrderUpdate.STATUS_NEW, 3));
        store.processUpdate(new OrderUpdate(3, 2, OrderUpdate.STATUS_COOKING, null));
        store.processUpdate(new OrderUpdate(3, 3, OrderUpdate.STATUS_DELIVERING, null));

        store.processUpdate(new OrderUpdate(4, 1, OrderUpdate.STATUS_NEW, 4));
        store.processUpdate(new OrderUpdate(4, 2, OrderUpdate.STATUS_COOKING, null));
        store.processUpdate(new OrderUpdate(4, 3, OrderUpdate.STATUS_DELIVERING, null));
        store.processUpdate(new OrderUpdate(4, 4, OrderUpdate.STATUS_DELIVERED, null));

        store.processUpdate(new OrderUpdate(5, 1, OrderUpdate.STATUS_NEW, 5));
        store.processUpdate(new OrderUpdate(5, 2, OrderUpdate.STATUS_COOKING, null));
        store.processUpdate(new OrderUpdate(5, 3, OrderUpdate.STATUS_DELIVERING, null));
        store.processUpdate(new OrderUpdate(5, 4, OrderUpdate.STATUS_DELIVERED, null));
        store.processUpdate(new OrderUpdate(5, 5, OrderUpdate.STATUS_REFUNDED, null));

        store.processUpdate(new OrderUpdate(6, 1, OrderUpdate.STATUS_NEW, 6));
        store.processUpdate(new OrderUpdate(6, 2, OrderUpdate.STATUS_CANCELED, 1));

        store.processUpdate(new OrderUpdate(7, 1, OrderUpdate.STATUS_NEW, 7));
        store.processUpdate(new OrderUpdate(7, 2, OrderUpdate.STATUS_COOKING, null));
        store.processUpdate(new OrderUpdate(7, 3, OrderUpdate.STATUS_CANCELED, null));

        store.processUpdate(new OrderUpdate(8, 1, OrderUpdate.STATUS_NEW, 8));
        store.processUpdate(new OrderUpdate(8, 2, OrderUpdate.STATUS_COOKING, null));
        store.processUpdate(new OrderUpdate(8, 3, OrderUpdate.STATUS_DELIVERING, null));
        store.processUpdate(new OrderUpdate(8, 4, OrderUpdate.STATUS_CANCELED, null));

        Map<String, Number> stats = store.getStats();
        Assert.assertEquals("Bad AMOUNT", 9, stats.get("AMOUNT"));
        Assert.assertEquals("Bad NEW", 1, stats.get(OrderUpdate.STATUS_NEW));
        Assert.assertEquals("COOKING not zero", 1, stats.get(OrderUpdate.STATUS_COOKING));
        Assert.assertEquals("DELIVERING not zero", 1, stats.get(OrderUpdate.STATUS_DELIVERING));
        Assert.assertEquals("deliveredCount not zero", 1, stats.get(OrderUpdate.STATUS_DELIVERED));
        Assert.assertEquals("canceledCount not zero", 3, stats.get(OrderUpdate.STATUS_CANCELED));
        Assert.assertEquals("refundedCount not zero", 1, stats.get(OrderUpdate.STATUS_REFUNDED));
    }

}
