/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const path = require('path');
const fs = require('fs');
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[fileStream1]');
logger.setLevel('debug');
const makeSource = require("stream-json");
const source = makeSource();

const fileName = path.resolve(__dirname, '../test/test1.json');
let keyCounter = 0;
source.on("startKey", function(){ ++keyCounter; });
source.on("end", function(){
    logger.info("Found ", keyCounter, " keys.");
});

fs.createReadStream(fileName).pipe(source.input);

