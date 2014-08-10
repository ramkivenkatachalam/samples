package org.ramki.order;

import java.util.Map;

/**
 * OrderStore lets you process order updates as they come in. It performs all the business rules validation
 * and provides order statistics.
 *
 */
public interface OrderStore {
    /**
     * Inserts a valid order into store after validation/biz rules check.
     * Following biz rules need to be enforced
     * 1. Update object is legal/well-formed
     * 2. Order state in NEW state
     * 3. Update ID is monotonically increasing
     * 4. Legal state transition

     *
     * @param update - next order update to process
     */
    void processUpdate(OrderUpdate update);

    /**
     * Get order statistics
     *
     * @return Map of stats eg: {
     *         "NEW": 4,
     *         "COOKING":2,
     *         "DELIVERING": 3,
     *         "DELIVERED":5,
     *         "CANCELED": 1,
     *         "REFUNDED": 1,
     *         "AMOUNT": 100
     *         }
     */
    Map<String, Number> getStats();

    /**
     * print order statistics
     */
    void printStats();

    /**
     * clears the order store
     */
    void clear();
}
