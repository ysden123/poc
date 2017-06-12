/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[claimListParser]');
logger.setLevel('debug');

const Readable = require('stream').Readable;
const fs = require('fs');
const util = require('util');

/**
 * Parses a JSon input (text) with format: {"campaign1":["user1","user2"]}
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

/**
 * Parses a JSon input (file) with format: {"campaign1":["user1","user2"]}
 * @param input input JSon string
 * @param handler callback function; will be called with (campaign,user) for each user
 * @returns {Promise} resolve();reject(err)
 */
function parseFile(fileName, handler) {
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

        let fileStream = fs.createReadStream(fileName);

        fileStream.pipe(source.input)
            .on('error', (err) => {
                let msg = 'Parsing error: ' + err.message;
                logger.error(msg);
                reject(new Error(msg));
            })
    })
}

function parseFileAsStream(fileName, handler) {
    logger.info('Parse Started parsing');
    return new Promise((resolve, reject) => {
        let campaignStarted = false;
        let userStarted = false;
        let campaign = null;
        let user = null;

        let Parser = require('stream-json/Parser');
        // let parser = new Parser();
        let Streamer = require('stream-json/Streamer');
        // let streamer = new Streamer();
        // logger.debug(`parser: ${parser}, streamer: ${streamer}`);


        // let fileStream = fs.createReadStream(fileName);
        // let source = fileStream.pipe(parser).pipe(streamer);
        let pipeline = fs.createReadStream(fileName).pipe(new Parser()).pipe(new Streamer());

        // logger.debug('parser: ' + util.inspect(parser, {showHidden: true, depth: true}));
        // logger.debug('streamer: ' + util.inspect(streamer, {showHidden: true, depth: true}));
        // logger.debug('source: ' + util.inspect(source, {showHidden: true, depth: true}));
        logger.debug('pipeline: ' + util.inspect(pipeline, {showHidden: true, depth: true}));

        pipeline.on('data',(chunk)=>{
            logger.debug('chunk: ' + util.inspect(chunk, {showHidden: true, depth: true}));
        });
        pipeline.on('end', () => {
            resolve();
            logger.info('(pipeline) parse completed parsing');
        })

        // streamer.on('startKey', () => {
        //     campaignStarted = true
        // });
        // streamer.on('endKey', () => {
        //     campaignStarted = false
        // });
        //
        // streamer.on('startString', () => {
        //     userStarted = true
        // });
        // streamer.on('endString', () => {
        //     userStarted = false
        // });
        //
        // streamer.on('stringChunk', (value) => {
        //     if (campaignStarted) {
        //         campaign = value;
        //     } else if (userStarted) {
        //         user = value;
        //         if (handler) {
        //             handler(campaign, user)
        //         }
        //     } else {
        //         let msg = 'Invalid state';
        //         logger.error(msg);
        //         reject(new Error(msg));
        //     }
        // });
        //
        // parser.on('end', () => {
        //     resolve();
        //     logger.info('parse completed parsing');
        // });
        //
        // parser.on('error', (err) => {
        //     let msg = 'Parsing error: ' + err.message;
        //     logger.error(msg);
        //     reject(new Error(msg));
        // });
        //
        // parser.on('data', (data)=>{
        //     logger.debug('parser on data: ' + util.inspect(data, {showHidden: true, depth: true}));
        // })

        //////////////////////////////
        // streamer.on('end',()=>{
        //     resolve();
        //     logger.info('(streamer) parse completed parsing');
        // });
        // parser.on('end',()=>{
        //     resolve();
        //     logger.info('(parser) parse completed parsing');
        // })
        //////////////////////////////
    })
}

module.exports = {
    parse: parse,
    parseFile: parseFile,
    parseFileAsStream:parseFileAsStream
};
