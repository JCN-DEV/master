'use strict';

angular.module('stepApp')
    .factory('InstEmpEduQualiSearch', function ($resource) {
        return $resource('api/_search/instEmpEduQualis/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmpEduQualisCurrent', function ($resource) {
        return $resource('api/instEmpEduQualis/currentLogin', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
