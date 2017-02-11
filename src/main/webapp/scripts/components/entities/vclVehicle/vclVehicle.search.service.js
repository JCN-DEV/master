'use strict';

angular.module('stepApp')
    .factory('VclVehicleSearch', function ($resource) {
        return $resource('api/_search/vclVehicles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
