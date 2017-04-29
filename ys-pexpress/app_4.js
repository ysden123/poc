// Using midleware to download static files
'use strict';

const express = require('express');
const http = require('http');
const path = require('path');

const publicPath = path.resolve(__dirname, 'public');

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

// Handling files from public folder.
app.use(express.static(publicPath));

app.use((req, res) => {
    res.writeHead(200, { "Content-Type": "text/plain" });
    res.end("Looks like you didn't find a static file.");
});

app.listen(3000);