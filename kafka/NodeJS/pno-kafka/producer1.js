/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const Kafka = require('no-kafka');
const producer = new Kafka.Producer();

producer.init().then(() => {
    return producer.send({
        // topic: 'kafka-test-topic',
        topic: 'myTopic',
        partition: 0,
        message: {
            value: 'Hello!'
        }
    });
})
    .then(result => {
        /*
         [ { topic: 'kafka-test-topic', partition: 0, offset: 353 } ]
         */
        console.log({result: result});
        if (result[0].error) {
            console.log({resultError: result[0].error});
            process.exit(1)
        }
        producer.end();
    })
    .catch(err => {
        console.log({err: err});
        producer.end();
    });
