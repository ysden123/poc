/*
 * Copyright (c) 2017. Yuriy Stul 
 */

'use strict';
const parser1 = require('..').parser1;

describe('parser1', () => {
    describe('#parse', () => {
        it('should parse simple json line', () => {
            let json = '{"a":["v1","v2"]}';
            parser1.parse(json);
        });
        it('should parse multi-line json line', () => {
            let json = '{"a":["v1",\n';
            json = json + '"v2"]}';
            parser1.parse(json);
        });
        it('should parse two objects', () => {
            let json = '{"obj1": ["v11","v12"],'
                + '"obj2": ["v21","v22","v23","v24"]';
            parser1.parse(json);
        })
    })
});