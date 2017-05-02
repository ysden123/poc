/*
 * Copyright (c) 2017. Yuriy Stul
 */

'use strict';
const kafka = require('kafka-node'),
    Producer = kafka.Producer,
    client = new kafka.Client(),
    producer = new Producer(client),
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