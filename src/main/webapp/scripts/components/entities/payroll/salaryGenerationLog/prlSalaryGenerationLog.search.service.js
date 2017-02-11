'use strict';

angular.module('stepApp')
    .factory('PrlSalaryGenerationLogSearch', function ($resource) {
        return $resource('api/_search/prlSalaryGenerationLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
