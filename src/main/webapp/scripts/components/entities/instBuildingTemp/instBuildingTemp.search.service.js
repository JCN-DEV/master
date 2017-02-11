'use strict';

angular.module('stepApp')
    .factory('InstBuildingTempSearch', function ($resource) {
        return $resource('api/_search/instBuildingTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
