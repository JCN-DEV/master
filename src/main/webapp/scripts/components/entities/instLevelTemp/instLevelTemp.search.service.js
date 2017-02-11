'use strict';

angular.module('stepApp')
    .factory('InstLevelTempSearch', function ($resource) {
        return $resource('api/_search/instLevelTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
