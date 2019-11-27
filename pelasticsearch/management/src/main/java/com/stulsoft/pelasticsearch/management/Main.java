/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pelasticsearch.management;

import com.stulsoft.pelasticsearch.management.model.Workflow;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Yuriy Stul
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("==>main");

        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster")
                .put("client.transport.sniff", false)
                .build();

        Client client = null;
        try {
            logger.info("Creating client...");
            long start = System.currentTimeMillis();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            logger.info("Client is created in {} ms.", System.currentTimeMillis() - start);

//            getAllDocs(client);
//            getAllWorkflows(client);
//            getAllWorkflows2(client);
            getAllWorkflows3(client);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (client != null) {
                logger.info("Closing client ...");
                try {
                    client.close();
                } catch (Exception ex) {
                    logger.error("Failed close client: " + ex.getMessage(), ex);
                }
            }
        }
    }

/*
    private static void getAllDocs(Client client) {
        logger.info("==>getAllDocs");
        SearchResponse response = client.prepareSearch("conductor")
                .setQuery(QueryBuilders.termQuery("type", "workflow"))
                .get();
        logger.info("response: {}", response.toString());
    }
*/

    private static void getAllDocs(Client client) {
        logger.info("==>getAllDocs");
        SearchResponse response = client.prepareSearch("conductor")
                .get();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        searchHits.forEach(sh -> {
//            logger.info("hit: {}", sh.getSourceAsString());

            ObjectMapper mapper = new ObjectMapper();

            try {
                Workflow wf = mapper.readValue(sh.getSourceAsString(), Workflow.class);
                logger.info("workflow: {}", wf);
            } catch (Exception ex) {
//                logger.error("Failed parsing " + sh.getSourceAsString() + " : " + ex.getMessage(), ex);
            }
        });
//        logger.info("response: {}", response.toString());
    }

    private static void getAllWorkflows(Client client) {
        logger.info("==>getAllWorkflows");
        SearchResponse response = client.prepareSearch("conductor")
                .get();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        searchHits.forEach(sh -> {
            if (sh.getType().equals("workflow")) {

                ObjectMapper mapper = new ObjectMapper();

                try {
                    Workflow wf = mapper.readValue(sh.getSourceAsString(), Workflow.class);
                    logger.info("workflow: {}", wf);
                } catch (Exception ex) {
                    logger.error("Failed parsing " + sh.getSourceAsString() + " : " + ex.getMessage(), ex);
                }
            }
        });
//        logger.info("response: {}", response.toString());
    }

    private static void getAllWorkflows2(Client client) {
        logger.info("==>getAllWorkflows2");
        SearchResponse response = client.prepareSearch("conductor")
                .setTypes("workflow")
                .get();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        searchHits.forEach(sh -> {
            logger.info("id: {}", sh.getId());

                ObjectMapper mapper = new ObjectMapper();

                try {
                    Workflow wf = mapper.readValue(sh.getSourceAsString(), Workflow.class);
                    logger.info("workflowType: {}", wf.getWorkflowType());
//                    logger.info("workflow: {}", wf);
                } catch (Exception ex) {
                    logger.error("Failed parsing " + sh.getSourceAsString() + " : " + ex.getMessage(), ex);
                }
        });
//        logger.info("response: {}", response.toString());
    }

    private static void getAllWorkflows3(Client client) {
        logger.info("==>getAllWorkflows3");

        SearchResponse response = client.prepareSearch("conductor")
                .setTypes("workflow")
                .get();
        for(SearchHit sh: response.getHits().getHits()){
            logger.info("id: {}", sh.getId());

            ObjectMapper mapper = new ObjectMapper();

            try {
                Workflow wf = mapper.readValue(sh.getSourceAsString(), Workflow.class);
                logger.info("workflowType: {}", wf.getWorkflowType());
                    logger.info("workflow: {}", wf);
            } catch (Exception ex) {
                logger.error("Failed parsing " + sh.getSourceAsString() + " : " + ex.getMessage(), ex);
            }
        }
    }
}
