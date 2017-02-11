'use strict';

angular.module('stepApp')
    .factory('InstInfraInfoSearch', function ($resource) {
        return $resource('api/_search/instInfraInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('AllInstInfraInfo', function ($resource) {
        return $resource('api/instInfraInfosAllInfo/:institueid', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
