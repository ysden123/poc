/*
 * Copyright (c) 2017. Yuriy Stul
 */

'use strict';
const kafka = require('kafka-node'),
    Consumer = kafka.Consumer,
    client = new kafka.Client(),
    consumer = new Consumer(
        client,
        [
            {topic: 'topic1', partition: 0}
        ],
        {
            autoCommit: true
        }
    );

consumer.on('message', (message) => {
    console.log({consumerOnMessage: message});
    consumer.close()
});

console.log("==>consumer11");