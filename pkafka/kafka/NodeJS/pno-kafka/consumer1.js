/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const Kafka = require('no-kafka');
const consumer = new Kafka.SimpleConsumer();

consumer.init()
    .then(() => {
        let count = 0;
        // Subscribe partitons 0 and 1 in a topic:
        return consumer.subscribe('myTopic', [0, 1], (messageSet, topic, partition) => {
            messageSet.forEach(m => {
                console.log(topic, partition, m.offset, m.message.value.toString());
            });

            if (++count >= 2)
                consumer.end()
        });
    })
    .catch(err => {
        console.log({err: err});
    });
