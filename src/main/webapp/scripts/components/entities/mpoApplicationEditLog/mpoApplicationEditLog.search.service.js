'use strict';

angular.module('stepApp')
    .factory('MpoApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/mpoApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
