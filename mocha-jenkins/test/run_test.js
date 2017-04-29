// Unit tests for Run class
'use strict';

const should = require('chai').should();
const TestRunner = require('..').TestRunner;
const Config = require('./data/config');
const conf = new Config();

describe('TestRunner',function(){
    describe('#run',function(){
        it('should run unit tests',function(done){
            const tr = new TestRunner(conf);
            tr.run(done);
        });
    })
})