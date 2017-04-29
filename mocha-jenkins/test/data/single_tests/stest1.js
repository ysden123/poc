'use strict';

const should = require('chai').should();

describe('stest1',function(){
    describe('#sfoo1',function(){
        it('Should run successfully',function(){
            
        });
        it('Should run unsuccessfully',function(done){
            done(new Error('test'));
        });
    })
})