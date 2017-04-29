// Defines list of the automation tests.
'use strict';
module.exports = function () {
    return {
        rootPath: 'test/data',
        tests: [
            'single_tests/stest1.js',
            'single_tests/stest2.js',
            'tests/*'
        ]
    };
}