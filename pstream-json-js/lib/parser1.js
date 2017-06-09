/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[parser1]');
logger.setLevel('debug');

const Readable = require('stream').Readable;


function parse(json, callback) {
    logger.debug('==>parse');
    let source = require('stream-json')();
    let keyCounter = 0;
    source.on('startObject', () => {
        logger.debug('on startObject');
    });
    source.on('endObject', () => {
        logger.debug('on endObject');
    });
    source.on('startKey', () => {
        ++keyCounter;
        logger.debug('on startKey');
    });
    source.on('endKey', () => {
        logger.debug('on endKey');
    });
    source.on('startString', () => {
        logger.debug('on startString');
    });
    source.on('stringChunk', (v) => {
        logger.debug(`on stringChunk, v: ${v}`);
    });
    source.on('endString', () => {
        logger.debug('on endString');
    });
    source.on('end', () => {
        logger.debug(`Found ${keyCounter} objects.`);
        if (callback)
            callback(keyCounter)
    });

    let s = new Readable;
    s.push(json);
    s.push(null);
    s.pipe(source.input);
}

module.exports = {
    parse: parse
};
