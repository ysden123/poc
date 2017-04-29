'use strict';
var HazelcastClient = require('hazelcast-client').Client;
var person = {
    firstName: "Joe",
    lastName: "Doe",
    age: 42
};

let map;
let theHazelcastClient;
let ttl = 1000;
HazelcastClient
    .newHazelcastClient()
    .then(hazelcastClient => {
        theHazelcastClient = hazelcastClient;
        map = hazelcastClient.getMap("personMapTtl");
        return map.put(1, person, ttl);
    })
    .then(val => {
        console.log('saved:', val);
        return map.get(1);
    })
    .then(val => {
        console.log('received:', val);
        return Promise.resolve();
    })
    .then(() => {
        console.log('Getting after 2 secs');
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                map.get(1)
                    .then((val) => resolve(val));
            }, 2000);
        })
    })
    .then(val => {
        console.log('received after 2 secs:', val);
        return Promise.resolve();
    })
    .then(val => {
        console.log('Getting with error key...');
        return map.get('ERROR');
    })
    .then(val => {
        console.log('received for ERROR key:', val);
        return Promise.resolve();
    })
    .then(() => {
        console.log('Done');
        theHazelcastClient.shutdown();
    })
    .catch(err => {
        console.error(err);
        if (theHazelcastClient != undefined) {
            theHazelcastClient.shutdown();
        }
    });