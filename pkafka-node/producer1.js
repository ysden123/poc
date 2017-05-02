/*
 * Copyright (c) 2017. Yuriy Stul
 */

'use strict';
const kafka = require('kafka-node');
const Producer = kafka.Producer;
const client = new kafka.Client();
const producer = new Producer(client);
const
    payloads = [
        {topic: 'topic1', messages: 'hi', partition: 0}
    ];

producer.on('ready', () =>
    producer.send(payloads, function (err, data) {
        if (err)
            console.log({err: err});
        else {
            console.log({data: data});
            client.close()
        }
    })
);

producer.on('error', (err) => console.log({onError: err}));