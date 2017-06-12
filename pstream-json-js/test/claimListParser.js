/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const claimListParser = require('..').claimListParser;
const should = require('chai').should();
const path = require('path');
const fs = require('fs');

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

function writeLongClaimList(fileName, amountOfCampaigns, amountOfUsers) {
    let writer = fs.createWriteStream(fileName);
    writer.write('{');
    for (let campaignN = 1; campaignN <= amountOfCampaigns; ++campaignN) {
        if (campaignN > 1) writer.write(',');
        writer.write(`"campaign ${campaignN}":[`);
        for (let userN = 1; userN <= amountOfUsers; ++userN) {
            if (userN > 1) writer.write(',');
            writer.write(`"user${userN}"`)
        }
        writer.write(']');
    }
    // writer.write('}');
    // writer.end();
    writer.end('}');
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

        it('should parse long input', function (done) {
            this.timeout(10000);
            let userCount = 0;

            function handler(campaign, user) {
                ++userCount;
            }

            console.log({memory_usage_1: process.memoryUsage()});
            let amountOfCampaigns = 300;
            let amountOfUsers = 5000;
            let input = generateLongClaimList(amountOfCampaigns, amountOfUsers);
            console.log(`input length is ${input.length}`);
            claimListParser.parse(input, handler)
                .then(() => {
                    console.log({memory_usage_2: process.memoryUsage()});
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done()
                })
                .catch(err => done(err))
        });

        it('should fail parse wrong input 1', function (done) {
            let input = 'test';
            claimListParser.parse(input)
                .then(() => {
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done('Non-catched error')
                })
                .catch(err => done());
        });

        it('should fail parse wrong input 2', function (done) {
            let input = '{test}';
            claimListParser.parse(input)
                .then(() => {
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done('Non-catched error')
                })
                .catch(err => done());
        });

        it('should fail parse wrong input 3', function (done) {
            let input = '{"test"}';
            claimListParser.parse(input)
                .then(() => {
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done('Non-catched error')
                })
                .catch(err => done());
        });

        it('should fail parse wrong input 4', function (done) {
            let input = '{"test":[}';
            claimListParser.parse(input)
                .then(() => {
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done('Non-catched error')
                })
                .catch(err => done());
        });

        it('should fail parse wrong input 5', function (done) {
            let input = '{"test":["a"}';
            claimListParser.parse(input)
                .then(() => {
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done('Non-catched error')
                })
                .catch(err => done());
        })
    });

    describe('#parseFile',()=>{
        it('should parse correct json', function (done) {
            let fileName = path.resolve(__dirname, 'test1.json');
            claimListParser.parseFile(fileName)
                .then(() => done())
                .catch(err => done(err))
        });

        it('should parse call handler', function (done) {
            let userCount = 0;

            function handler(campaign, user) {
                console.log(`campaign: ${campaign}, user: ${user}`);
                ++userCount;
            }

            let fileName = path.resolve(__dirname, 'test1.json');
            claimListParser.parseFile(fileName, handler)
                .then(() => {
                    userCount.should.equal(4);
                    done()
                })
                .catch(err => done(err))
        });

        it('should parse long input', function (done) {
            this.timeout(20000);
            let userCount = 0;

            function handler(campaign, user) {
                ++userCount;
            }

            console.log({memory_usage_1: process.memoryUsage()});
            let amountOfCampaigns = 30;
            let amountOfUsers = 500;
            let fileName = path.resolve(__dirname, 'temp.json');
            writeLongClaimList(fileName, amountOfCampaigns, amountOfUsers);
            claimListParser.parseFile(fileName, handler)
                .then(() => {
                    console.log({memory_usage_2: process.memoryUsage()});
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done()
                })
                .catch(err => done(err))
        });
    });

    describe('#parseFile',()=>{
        it('should parse correct json', function (done) {
            this.timeout(20000);
            let fileName = path.resolve(__dirname, 'test1.json');
            claimListParser.parseFileAsStream(fileName)
                .then(() => {
                done()
            })
                .catch(err => done(err))
        });

        it.only('should parse call handler', function (done) {
            this.timeout(20000);
            let userCount = 0;

            function handler(campaign, user) {
                console.log(`campaign: ${campaign}, user: ${user}`);
                ++userCount;
            }

            let fileName = path.resolve(__dirname, 'test1.json');
            claimListParser.parseFileAsStream(fileName, handler)
                .then(() => {
                    userCount.should.equal(4);
                    done()
                })
                .catch(err => done(err))
        });

        it('should parse long input', function (done) {
            this.timeout(20000);
            let userCount = 0;

            function handler(campaign, user) {
                ++userCount;
            }

            console.log({memory_usage_1: process.memoryUsage()});
            let amountOfCampaigns = 30;
            let amountOfUsers = 500;
            let fileName = path.resolve(__dirname, 'temp.json');
            writeLongClaimList(fileName, amountOfCampaigns, amountOfUsers);
            claimListParser.parseFileAsStream(fileName, handler)
                .then(() => {
                    console.log({memory_usage_2: process.memoryUsage()});
                    userCount.should.equal(amountOfCampaigns * amountOfUsers);
                    done()
                })
                .catch(err => done(err))
        });
    })
});
