/*
 * Copyright (c) 2017. Yuriy Stul
 */
'use strict';
const parseString = require('xml2js').parseString;
const xml2js = require('xml2js');

const srcXml = "<root><entry><name>Name 1</name><value>The value 1</value></entry><entry><name>Name 2</name><value>The value 2</value></entry></root>";
let srcObj = parseString(srcXml, (err, result) => {
        console.log({result: result});
        return result
    }
);

let builder = new xml2js.Builder();
let dstXml = builder.buildObject(srcObj);
console.log({dstXml: dstXml});