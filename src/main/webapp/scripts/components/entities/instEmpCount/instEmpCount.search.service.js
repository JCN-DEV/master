'use strict';

angular.module('stepApp')
    .factory('InstEmpCountSearch', function ($resource) {
        return $resource('api/_search/instEmpCounts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
