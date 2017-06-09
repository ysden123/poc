/*
 * Copyright (c) 2017. Yuriy Stul 
 */

'use strict';
const parser1 = require('..').parser1;
const should = require('chai').should();

describe('parser1', () => {
    describe('#parse', () => {
        it('should parse simple json line', (done) => {
            let json = '{"a":["v1","v2"]}';
            parser1.parse(json, (objectCounter) => {
                objectCounter.should.equal(1);
                done()
            });
        });

        it('should parse multi-line json line', (done) => {
            let json = '{"a":["v1",\n';
            json = json + '"v2"]}';
            parser1.parse(json, (objectCounter) => {
                objectCounter.should.equal(1);
                done()
            });
        });

        it('should parse two objects', (done) => {
            let json = '{"obj1": ["v11","v12"],'
                + '"obj2": ["v21","v22","v23","v24"]}';
            parser1.parse(json, (objectCounter) => {
                objectCounter.should.equal(2);
                done()
            });
        })
    })
});