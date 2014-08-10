package org.ramki.order;

import java.util.Arrays;

/**
 * Model that represents Order Updates
 */
public class OrderUpdate {
    // These members are Objects instead of primitives as they may be null (for bad JSON strings)
    private Integer orderId;
    private Integer updateId;
    private String status;
    private Integer amount;

    final public static String STATUS_NEW = "NEW";
    final public static String STATUS_COOKING = "COOKING";
    final public static String STATUS_DELIVERING = "DELIVERING";
    final public static String STATUS_DELIVERED = "DELIVERED";
    final public static String STATUS_CANCELED = "CANCELED";
    final public static String STATUS_REFUNDED = "REFUNDED";

    public static final String[] VALID_STATUS = new String[]{STATUS_NEW, STATUS_COOKING, STATUS_DELIVERING, STATUS_DELIVERED, STATUS_CANCELED, STATUS_REFUNDED};

    public OrderUpdate() {
        this.updateId = null;
        this.orderId = null;
        this.amount = null;
        this.status = null;
    }

    public OrderUpdate(Integer orderId, Integer updateId, String status, Integer amount) {
        this.updateId = updateId;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "org.ramki.order.OrderUpdate{" +
                "orderId=" + orderId +
                ", updateId=" + updateId +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }

    public boolean validate() {
        if (orderId == null) return false;
        if (updateId == null) return false;
        if (status == null) return false;
        // check if status is in valid list
        if (Arrays.asList(VALID_STATUS).contains(status) == false) return false;
        // NEW Orders without amount is invalid
        if (status.equals(STATUS_NEW) && amount == null) return false;
        return true;

    }
    
    // Getters and Setters
    
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
