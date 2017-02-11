'use strict';

angular.module('stepApp')
    .factory('ReferenceSearch', function ($resource) {
        return $resource('api/_search/references/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('ReferenceEmployee', function ($resource) {
        return $resource('api/references/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
