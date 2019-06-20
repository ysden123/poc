/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const Kafka = require('no-kafka');
const consumer = new Kafka.GroupConsumer({
    groupId: "testGroup",
    logger: {
        logLevel: 1 // 0 - nothing, 1 - just errors, 2 - +warnings, 3 - +info, 4 - +debug, 5 - +trace
    }
});

const dataHandler = function (messageSet, topic, partition) {
    return new Promise((resolve, reject) => {
        Promise.all(
            messageSet.map(m => {
                new Promise((resolve, reject) => {
                    console.log(topic, partition, m.offset, m.message.value.toString());
                    // commit offset
                    return consumer.commitOffset({
                        topic: topic,
                        partition: partition,
                        offset: m.offset,
                        metadata: 'optional'
                    })
                        .then(() => resolve())
                        .catch(err => reject(err));
                })
            }))
            .then(() => resolve())
            .catch(err => reject(err));
    })

};

const strategies = [{
    subscriptions: ['myTopic'],
    handler: dataHandler
}];

consumer.init(strategies) // all done, now wait for messages in dataHandler
    .then(() => console.log('Started group consumer'))
    .catch(err => console.error('Failed starting group consumer'));