'use strict';

angular.module('stepApp')
    .factory('JpEmployeeSearch', function ($resource) {
        return $resource('api/_search/jpEmployees/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
