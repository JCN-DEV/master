'use strict';

angular.module('stepApp')
    .factory('TimeScaleApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/timeScaleApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('TimescaseApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/timescaleApplicationStatusLogs/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
