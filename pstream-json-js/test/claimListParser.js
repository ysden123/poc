/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const claimListParser = require('..').claimListParser;
const should = require('chai').should();

function generateLongClaimList(amountOfCampaigns, amountOfUsers) {
    let json = '{';
    for (let campaignN = 1; campaignN <= amountOfCampaigns; ++campaignN) {
        if (campaignN > 1) json += ',';
        json += `"campaign ${campaignN}":[`;
        for (let userN = 1; userN <= amountOfUsers; ++userN) {
            if (userN > 1) json += ',';
            json += `"user${userN}"`
        }
        json += ']'
    }
    json += '}';
    return json;
}

describe('claimListParser', () => {
    describe('#parse', () => {
        it('should parse correct json', function (done) {
            let input = '{"camp1":["u11","u12","u13"],"camp2":["u21","u22"]}';
            claimListParser.parse(input)
                .then(() => done())
                .catch(err => done(err))
        });

        it('should parse call handler', function (done) {
            let userCount = 0;

            function handler(campaign, user) {
                console.log(`campaign: ${campaign}, user: ${user}`);
                ++userCount;
            }

            let input = '{"camp1":["u11","u12","u13"],"camp2":["u21","u22"]}';
            claimListParser.parse(input, handler)
                .then(() => {
                    userCount.should.equal(5);
                    done()
                })
                .catch(err => done(err))
        });

        it('should parse long input',function(done){
            this.timeout(10000);
            let userCount = 0;

            function handler(campaign, user) {
                ++userCount;
            }

            console.log({memory_usage_1: process.memoryUsage()});
            let amountOfCampaigns = 300;
            let amountOfUsers = 5000;
            let input = generateLongClaimList(amountOfCampaigns,amountOfUsers);
            console.log(`input length is ${input.length}`);
            claimListParser.parse(input, handler)
                .then(() => {
                    console.log({memory_usage_2: process.memoryUsage()});
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done()
                })
                .catch(err => done(err))
        })
    })
});
