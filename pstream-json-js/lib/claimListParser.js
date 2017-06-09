/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[claimListParser]');
logger.setLevel('debug');

const Readable = require('stream').Readable;

/**
 * Parses a JSon input with format: {"campaign1":["user1","user2"]}
 * @param input input JSon string
 * @param handler callback function; will be called with (campaign,user) for each user
 * @returns {Promise} resolve();reject(err)
 */
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

        source.on('end', () => {
            resolve();
            logger.info('parse completed parsing');
        });

        source.on('error', (err) => {
            let msg = 'Parsing error: ' + err.message;
            logger.error(msg);
            reject(new Error(msg));
        });

        let stringStream = new Readable;
        stringStream.push(input);
        stringStream.push(null);

        stringStream.pipe(source.input)
            .on('error', (err) => {
                let msg = 'Parsing error: ' + err.message;
                logger.error(msg);
                reject(new Error(msg));
            })
    })
}


module.exports = {
    parse: parse
};
