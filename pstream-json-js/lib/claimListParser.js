/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[claimListParser]');
logger.setLevel('debug');

const Readable = require('stream').Readable;
function parse(input, handler) {
    logger.info('Parse Started parsing');
    return new Promise((resolve, reject) => {
        let campaignStarted = false;
        let userStarted = false;
        let campaign = null;
        let user = null;

        let source = require('stream-json')();

        source.on('startKey', () => {
            campaignStarted = true
        });
        source.on('endKey', () => {
            campaignStarted = false
        });

        source.on('startString', () => {
            userStarted = true
        });
        source.on('endString', () => {
            userStarted = false
        });

        source.on('stringChunk', (value) => {
            if (campaignStarted) {
                campaign = value;
            } else if (userStarted) {
                user = value;
                if (handler) {
                    handler(campaign, user)
                }
            } else {
                let msg = 'Invalid state';
                logger.error(msg);
                reject(new Error(msg));
            }
        });

        source.on('end',()=>{
           resolve();
            logger.info('parse completed parsing');
        });

        let stringStream = new Readable;
        stringStream.push(input);
        stringStream.push(null);
        stringStream.pipe(source.input);
    })
}


module.exports = {
    parse: parse
};
