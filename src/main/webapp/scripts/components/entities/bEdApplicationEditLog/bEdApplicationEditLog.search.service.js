'use strict';

angular.module('stepApp')
    .factory('BEdApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/bEdApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
