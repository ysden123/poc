// Midleware that changes the request and response.
'use strict';

const express = require('express');
const http = require('http');

const app = express();

// Midleware

// Ignore /favicon.ico
app.use((req, res, next) => {
    if (req.url === '/favicon.ico') {
        res.end();
    } else {
        next();
    }
});

// Logging
app.use((req, res, next) => {
    console.log(`In comes a request to: ${req.url}`);
    next();
});

let sw = 0;

// If visiting at the first time,
// calls next() to continue on; 
// If not authorized, send 403 status code and responds.
// Otherwise sends the secret information.
app.use((req, res, next) => {
    console.log(`sw=${sw}`);
    if (sw === 0) {
        sw = 1;
        next();
    } else {
        sw = 0;
        res.statusCode = 403;
        res.end('Not authorized.');
    }
});

app.use((req, res) => {
    res.end('Secred info: the password is "swordfish"!');
});

app.listen(3000);
