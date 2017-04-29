// Midleware with 1 use
'use strict';
const express = require('express');

const app = express();

// Midleware
app.use((request, response) => {
    console.log(`In comes a request to: ${request.url}`);
    response.end('Hello, world!');
});

/*
 app.listen(3000); = http.createServer(app).listen(3000);
 It is just shorthand.
 */
app.listen(3000);
