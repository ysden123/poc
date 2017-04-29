'use strict';
var HazelcastClient = require('hazelcast-client').Client;
var person = {
    firstName: "Joe",
    lastName: "Doe",
    age: 42
};

let map;
let theHazelcastClient;
HazelcastClient
    .newHazelcastClient()
    .then(hazelcastClient => {
        theHazelcastClient = hazelcastClient;
        map = hazelcastClient.getMap("personMap");
        return map.put(1, person);
    })
    .then(val => {
        // prints previous value for key `1` 
        console.log('saved:', val);
        return map.get(1);
    })
    .then(val => {
        console.log('received:', val);
        return Promise.resolve();
    })
    .then(val => {
        // prints previous value for key `ERROR` 
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
        console.error('Failure:', err);
        if (theHazelcastClient != undefined) {
            theHazelcastClient.shutdown();
        }
    });