/*
 * Copyright (c) 2017. Yuriy Stul
 */

'use strict';
const kafka = require('kafka-node');
const Consumer = kafka.Consumer;
const client = new kafka.Client();
const consumer = new Consumer(
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