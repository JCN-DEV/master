'use strict';

angular.module('stepApp')
    .factory('InstGovBodyAccessTempSearch', function ($resource) {
        return $resource('api/_search/instGovBodyAccessTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
