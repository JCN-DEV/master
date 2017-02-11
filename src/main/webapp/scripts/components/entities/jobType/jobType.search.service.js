'use strict';

angular.module('stepApp')
    .factory('JobTypeSearch', function ($resource) {
        return $resource('api/_search/jobTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobTypeAllActive', function ($resource) {
        return $resource('api/jobTypes/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
