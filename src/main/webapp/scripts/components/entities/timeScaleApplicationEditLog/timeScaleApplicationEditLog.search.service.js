'use strict';

angular.module('stepApp')
    .factory('TimeScaleApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/timeScaleApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
