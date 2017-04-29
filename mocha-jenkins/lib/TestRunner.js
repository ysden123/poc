// Test runner
'use strict';
const Mocha = require('mocha')
const fs = require('fs')
const path = require('path')
const manager = require('simple-node-logger').createLogManager();
const logger = manager.createLogger('[TestRunner]');

class TestRunner {
    constructor(options) {
        if ((typeof options) !== 'object')
            throw new Error('options must be object');
        if (!Array.isArray(options.tests))
            throw new Error('options.tests must be Array');
        if ((typeof options.rootPath) !== 'string')
            throw new Error('options.rootPath must be string');
        this.options = options;
    }

    run(callback) {
        const mocha = new Mocha();
        const pjson = require('../package.json'); 
        logger.info(`${pjson.name}@${pjson.version}`)
        // Prepare test list for running
        this.options.tests.forEach(fileSpec => {
            logger.info(`Adding ${fileSpec}`);
            if (fileSpec.endsWith('/*')) {
                getFileList(this.options.rootPath, fileSpec.substr(0, fileSpec.length - 2), mocha);
            } else {
                const testPath = path.join(this.options.rootPath, fileSpec);
                mocha.addFile(testPath);
                logger.info(`${testPath} was added.`);
            }
        })

        // Run tests.
        logger.info('Started executing tests');
        mocha.reporter('xunit-file').run(failures => {
            logger.info(`Completed executing tests, failures=${failures}`);
            if (callback)
                callback();
            process.on('exit', () => {
                logger.info(`Exit, failures=${failures}`);
                process.exit(failures);
            })
        })
    }
}

function getFileList(rootPath, folder, mocha) {
    const testDir = path.join(rootPath, folder);
    fs.readdirSync(testDir)
        .filter(file => {
            return file.substr(-3) === '.js';
        })
        .forEach(file => {
            const testPath = path.join(testDir, file);
            mocha.addFile(testPath);
            logger.info(`${testPath} was added.`);
        })
}

module.exports = TestRunner; 