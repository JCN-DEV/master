'use strict';

angular.module('stepApp')
    .factory('DearnessAssignSearch', function ($resource) {
        return $resource('api/_search/dearnessAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
