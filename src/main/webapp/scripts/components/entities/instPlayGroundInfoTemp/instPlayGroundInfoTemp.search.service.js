'use strict';

angular.module('stepApp')
    .factory('InstPlayGroundInfoTempSearch', function ($resource) {
        return $resource('api/_search/instPlayGroundInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
