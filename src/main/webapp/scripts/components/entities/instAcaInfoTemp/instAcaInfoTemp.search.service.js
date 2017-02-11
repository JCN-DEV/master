'use strict';

angular.module('stepApp')
    .factory('InstAcaInfoTempSearch', function ($resource) {
        return $resource('api/_search/instAcaInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
