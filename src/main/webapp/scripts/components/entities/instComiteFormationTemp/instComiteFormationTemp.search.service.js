'use strict';

angular.module('stepApp')
    .factory('InstComiteFormationTempSearch', function ($resource) {
        return $resource('api/_search/instComiteFormationTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
