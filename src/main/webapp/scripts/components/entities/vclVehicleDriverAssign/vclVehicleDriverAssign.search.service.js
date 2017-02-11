'use strict';

angular.module('stepApp')
    .factory('VclVehicleDriverAssignSearch', function ($resource) {
        return $resource('api/_search/vclVehicleDriverAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
