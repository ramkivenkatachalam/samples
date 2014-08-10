package org.ramki.order;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A singleton Store that implements OrderStore using in memory hash table.
 * NOTE: We need only the lastUpdate for computing stats and checking validity of the next update.
 * --- For every order, only the LAST VALID update is stored. 
 * NOTE: This simple implementation of org.ramki.order.OrderStore uses an in memory hash to store all the lastUpdate of all orders.
 * If the order list is too large we will want to use a DB/disk to store the data.
 */
public class HashOrderStore implements OrderStore {
    private static Logger logger = Logger.getLogger(OrderProcessor.class.getCanonicalName());
    private Map<Integer, OrderUpdate> updates;
    private static final HashOrderStore store = new HashOrderStore();

    private HashOrderStore() {
        this.updates = new HashMap<Integer, OrderUpdate>(100);
    }

    public static HashOrderStore getInstance() {
        return store;
    }

    /**
     * Inserts a valid order into store after validation/biz rules check.
     *
     * @param update - next order update to process
     */
    @Override
    public void processUpdate(OrderUpdate update) {
        try {
            checkUpdate(update);
            updateOrder(update);
        } catch (OrderUpdateException e) {
            logger.warning("Exception in HashOrderStore.processUpdate " + e);
        }
    }

    /**
     * checks for all the biz rules of order update
     * 1. Update object is legal
     * 2. Order state in NEW state
     * 3. Update ID is monotonically increasing
     * 4. Legal state transition
     *
     * @param update - next update to process
     * @throws OrderUpdateException if biz checks fail
     */
    void checkUpdate(OrderUpdate update) throws OrderUpdateException {
        if (update == null || !update.validate())
            throw new OrderUpdateException("Invalid update object");

        OrderUpdate lastUpdate = updates.get(update.getOrderId());
        if (lastUpdate == null) {
            if (OrderUpdate.STATUS_NEW.compareTo(update.getStatus()) != 0)
                throw new OrderUpdateException("Starting state not NEW for " + update);
        } else {
            if (lastUpdate.getUpdateId().equals(update.getUpdateId()))
                throw new OrderUpdateException("Duplicate update " + update);
            if (lastUpdate.getUpdateId() > update.getUpdateId())
                throw new OrderUpdateException("Out of order update " + update);
            checkStateTransition(lastUpdate, update);
        }
    }

    /**
     * helper method to verify that order state transition is legal. If you add new states or change (allow/disallow)
     * transitions update the test method in OrderSToreTest as well.
     *
     * @param lastUpdate - the previous legal update seen by the store
     * @param update     - the current update received
     */
    void checkStateTransition(OrderUpdate lastUpdate, OrderUpdate update) {
        if (OrderUpdate.STATUS_NEW.equals(update.getStatus()))
            throw new OrderUpdateException("Duplicate new state");
        if (OrderUpdate.STATUS_COOKING.equals(update.getStatus()) && !OrderUpdate.STATUS_NEW.equals(lastUpdate.getStatus()))
            throw new OrderUpdateException("Only NEW can change to COOKING");
        if (OrderUpdate.STATUS_DELIVERING.equals(update.getStatus()) && !OrderUpdate.STATUS_COOKING.equals(lastUpdate.getStatus()))
            throw new OrderUpdateException("Only COOKING can change to DELIVERING");
        if (OrderUpdate.STATUS_DELIVERED.equals(update.getStatus()) && !OrderUpdate.STATUS_DELIVERING.equals(lastUpdate.getStatus()))
            throw new OrderUpdateException("Only DELIVERING can change to DELIVERED");
        if (OrderUpdate.STATUS_REFUNDED.equals(update.getStatus()) && !OrderUpdate.STATUS_DELIVERED.equals(lastUpdate.getStatus()))
            throw new OrderUpdateException("Only DELIVERED can change to REFUNDED");
        if (OrderUpdate.STATUS_CANCELED.equals(update.getStatus()) &&
                (OrderUpdate.STATUS_DELIVERED.equals(lastUpdate.getStatus()) ||
                        OrderUpdate.STATUS_CANCELED.equals(lastUpdate.getStatus()) ||
                        OrderUpdate.STATUS_REFUNDED.equals(lastUpdate.getStatus())))
            throw new OrderUpdateException("Only NOT(DELIVERED || CANCELLED || REFUNDED) can change to CANCELLED");


    }

    /**
     * The reason why we don't just replace the most recent update as last update
     * into the store is that only NEW is expected to have the amount field. So 
     * we need to preserve the amount and carry it over the the latest update.
     * @param update
     */
    private void updateOrder(OrderUpdate update) {
        OrderUpdate lastUpdate = updates.get(update.getOrderId());
        if (lastUpdate == null) {
            updates.put(update.getOrderId(), update);
        } else {
            lastUpdate.setUpdateId(update.getUpdateId());
            lastUpdate.setStatus(update.getStatus());
            updates.put(lastUpdate.getOrderId(), lastUpdate);
        }

    }

    /**
     * Get order statistics
     *
     * @return Map of stats eg: {
     *         "NEW": 4,
     *         "COOKING":2,
     *         "DELIVERING": 3,
     *         "DELIVERED":5,
     *         "CANCELLED": 1,
     *         "REFUNDED" : 1,
     *         "AMOUNT"   : 100
     *         }
     */
    @Override
    public Map<String, Number> getStats() {
        Map<String, Number> stats = new HashMap<String, Number>();
        HashMap<String, Integer> counter = new HashMap<String, Integer>();
        Integer amount = 0;
        Collection<String> notChargeableStatusList;
        notChargeableStatusList = Arrays.asList(OrderUpdate.STATUS_NEW, OrderUpdate.STATUS_CANCELED, OrderUpdate.STATUS_REFUNDED);
        for (String status : OrderUpdate.VALID_STATUS) {
            counter.put(status, 0);
        }
        for (OrderUpdate update : updates.values()) {
            String status = update.getStatus();
            Integer count = counter.get(status);
            counter.put(status, count + 1);
            if (!notChargeableStatusList.contains(status)) {
                amount += update.getAmount();
            }
        }
        for (String status : OrderUpdate.VALID_STATUS) {
            Integer count = counter.get(status);
            stats.put(status, count);
        }
        stats.put("AMOUNT", amount);
        return stats;
    }

    /**
     * print order statistics
     */
    @Override
    public void printStats() {
        Map<String, Number> stats = getStats();
        System.out.println("New: "        + stats.get(OrderUpdate.STATUS_NEW));
        System.out.println("Cooking: "    + stats.get(OrderUpdate.STATUS_COOKING));
        System.out.println("Delivering: " + stats.get(OrderUpdate.STATUS_DELIVERING));
        System.out.println("Delivered: "  + stats.get(OrderUpdate.STATUS_DELIVERED));
        System.out.println("Canceled: "   + stats.get(OrderUpdate.STATUS_CANCELED));
        System.out.println("Refunded: "   + stats.get(OrderUpdate.STATUS_REFUNDED));
        System.out.println("Total amount charged: $" + stats.get("AMOUNT"));
    }



    /**
     * clears the order store
     */
    @Override
    public void clear() {
        this.updates.clear();
    }
}
