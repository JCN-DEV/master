'use strict';

angular.module('stepApp')
    .factory('JpEmployeeReferenceSearch', function ($resource) {
        return $resource('api/_search/jpEmployeeReferences/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpEmployeeReferenceJpEmployee', function ($resource) {
        return $resource('api/references/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
