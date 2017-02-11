'use strict';

angular.module('stepApp')
    .factory('InstGovernBodyTempSearch', function ($resource) {
        return $resource('api/_search/instGovernBodyTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
