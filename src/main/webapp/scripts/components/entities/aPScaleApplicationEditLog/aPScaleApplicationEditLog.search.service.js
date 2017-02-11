'use strict';

angular.module('stepApp')
    .factory('APScaleApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/aPScaleApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
