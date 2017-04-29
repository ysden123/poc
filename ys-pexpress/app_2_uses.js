// Midleware with 2 uses
'use strict';
const express = require('express');
const http = require('http');

const app = express();

// Midleware

// Logging
app.use((request, response, next) => {
    console.log(`In comes a request to: ${request.url}`);
    next();
});

app.use((request, response) => {
    console.log(`In comes a request to: ${request.url}`);
    response.writeHead(200, { 'Content-Type': 'text/plain' });
    response.end('Hello world!');
});

app.listen(3000);
