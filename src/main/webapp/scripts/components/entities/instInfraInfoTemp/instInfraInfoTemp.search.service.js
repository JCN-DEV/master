'use strict';

angular.module('stepApp')
    .factory('InstInfraInfoTempSearch', function ($resource) {
        return $resource('api/_search/instInfraInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstInfraInfoTempAll', function ($resource) {
        return $resource('api/instInfraInfoTemp/AllInfo/:institueid', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
