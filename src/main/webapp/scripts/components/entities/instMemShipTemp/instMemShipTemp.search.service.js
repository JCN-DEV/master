'use strict';

angular.module('stepApp')
    .factory('InstMemShipTempSearch', function ($resource) {
        return $resource('api/_search/instMemShipTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
