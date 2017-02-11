'use strict';

angular.module('stepApp')
    .factory('InstituteEmpCountSearch', function ($resource) {
        return $resource('api/_search/instituteEmpCounts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
