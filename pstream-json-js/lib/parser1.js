/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[parser1]');
logger.setLevel('debug');
const source = require('stream-json')();
const Readable = require('stream').Readable;

function parse(json) {
    logger.debug('==>parse');
    let objectCounter = 0;
    source.on('startObject', () => {
        logger.debug('on startObject');
        ++objectCounter
    });
    source.on('endObject', () => {
        logger.debug('on endObject');
    });
    source.on('startKey', () => {
        logger.debug('on startKey');
    });
    source.on('stringChunk', (v) => {
        logger.debug(`on stringChunk, v: ${v}`);
    });
    source.on('end', () => {
        logger.debug(`Found ${objectCounter} objects.`);
    });

    let s= new Readable;
    s.push(json);
    s.push(null);
    s.pipe(source.input);
}

module.exports = {
    parse: parse
};
