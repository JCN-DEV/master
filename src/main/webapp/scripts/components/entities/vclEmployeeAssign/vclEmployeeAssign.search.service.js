'use strict';

angular.module('stepApp')
    .factory('VclEmployeeAssignSearch', function ($resource) {
        return $resource('api/_search/vclEmployeeAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
