'use strict';
var HazelcastClient = require('hazelcast-client').Client;
var person = {
    firstName: "Joe",
    lastName: "Doe",
    age: 42
};

let map;
let theHazelcastClient;
let key = 'http://gjhgjh.com?gffhh=vnbvvnb&vnbvnbvnbvn|{a:"fgfhgfhg",b:"gjgjh}';
HazelcastClient
    .newHazelcastClient()
    .then(hazelcastClient => {
        theHazelcastClient = hazelcastClient;
        map = hazelcastClient.getMap("personMapBadKey");
        return map.put(key, person);
    })
    .then(val => {
        // prints previous value for key `1` 
        console.log('saved:', val);
        return map.get(key);
    })
    .then(val => {
        console.log('received:', val);
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