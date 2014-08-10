package org.ramki.order;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;


/**
 * Main OrderProcessing class. Reads JSON objects(OrderUpdate) from STDIN, processes them OrderStore and prints
 * order statistics to STDOUT
 */
public class OrderProcessor {
    private static Logger logger = Logger.getLogger(OrderProcessor.class.getCanonicalName());

    public static void main(String args[]) throws IOException {
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(new StreamHandler(System.err, new SimpleFormatter()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.processOrderStream(reader);
    }

    /**
     * processes a stream of JSON OrderUpdate strings
     * @param reader - input stream
     * @throws IOException if encountered while reading the stream.
     */
    public void processOrderStream(BufferedReader reader) throws IOException {
        OrderStore orderStore = HashOrderStore.getInstance();
        String line;
        while ((line = reader.readLine()) != null) {
            OrderUpdate update = parseUpdate(line);
            orderStore.processUpdate(update);
        }
        orderStore.printStats();
    }

    /**
     * Parse JSON string and return an OrderUpdate object
     * @param line - JSON string like {"orderId": 102, "status": "NEW", "updateId": 291, "amount": 17}
     * @return OrderUpdate object; empty OrderUpdate object which is invalid on parse exception
     */
    public OrderUpdate parseUpdate(String line) {
        logger.info("Got line: " + line);
        Gson gson = new Gson();
        OrderUpdate update = null;
        try {
            update = gson.fromJson(line, OrderUpdate.class);
            logger.info("Got :" + update);
        } catch (JsonSyntaxException e) {
            logger.warning("JsonSyntaxException while parsing " + line);
        }
        if (update != null) return update;
        else return new OrderUpdate();
    }



}
