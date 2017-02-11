'use strict';

angular.module('stepApp')
    .factory('InstLevelSearch', function ($resource) {
        return $resource('api/_search/instLevels/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstLevelByName', function ($resource) {
        return $resource('api/instLevels/byName/:name', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstLevelActive', function ($resource) {
        return $resource('api/instLevels/active', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
