'use strict';

angular.module('stepApp')
    .factory('InstLandTempSearch', function ($resource) {
        return $resource('api/_search/instLandTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
