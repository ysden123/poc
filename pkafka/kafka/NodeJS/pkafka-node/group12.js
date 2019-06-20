/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const ConsumerGroup = require('kafka-node').ConsumerGroup;
const consumerOptions = {
    // host: '127.0.0.1:2181',
    groupId: 'ExampleTestGroup',
    sessionTimeout: 15000,
    protocol: ['roundrobin'],
    fromOffset: 'earliest' // equivalent of auto.offset.reset valid values are 'none', 'latest', 'earliest'
};

const topics = ['topic1'];

// const consumerGroup1 = new ConsumerGroup(Object.assign({id: 'consumer11'}, consumerOptions), topics);
// consumerGroup1.on('error', onError);
// consumerGroup1.on('message', onMessage);

const consumerGroup2 = new ConsumerGroup(Object.assign({id: 'consumer12'}, consumerOptions), topics);
consumerGroup2.on('error', onError);
consumerGroup2.on('message', onMessage);

function onError(error) {
    console.error(error);
    console.error(error.stack);
}

function onMessage(message) {
    console.log('%s read msg Topic="%s" Partition=%s Offset=%d',
        this.client.clientId, message.topic, message.partition, message.offset);
}