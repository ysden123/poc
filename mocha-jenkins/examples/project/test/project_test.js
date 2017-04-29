'use strict';
const should = require('chai').should();

describe('project', function () {
    describe('#foo', function () {
        it('should run', function () {
            require('..').foo();
        })
    })
})