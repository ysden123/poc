// Routing
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

app.get('/', (req, res) => {
    res.end('Welcome to my homepage');
});
app.get('/about', (req, res) => {
    res.end('Welcome to about page');
});
app.get('/weather', (req, res) => {
    res.end('The current weather is NICE.');
});

app.get('/hello/:who', (req, res) => {
    res.end('Hello, ' + req.params.who + '.');
});

app.use((req, res) => {
    res.statusCode = 404;
    res.end('404!');
});

app.listen(3000);