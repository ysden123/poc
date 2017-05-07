/*
 * Copyright (c) 2017. Yuriy Stul 
 */

'use strict';
const parseString = require('xml2js').parseString;
const xml = "<root>Hello xml2js!</root>";
parseString(xml, function (err, result) {
    console.dir(result);
});

const xml2 = "<ROOT>Hello xml2js!</ROOT>";
parseString(xml2, (err, result) => {
    console.log(result);
});