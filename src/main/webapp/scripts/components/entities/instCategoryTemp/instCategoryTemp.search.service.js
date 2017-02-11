'use strict';

angular.module('stepApp')
    .factory('InstCategoryTempSearch', function ($resource) {
        return $resource('api/_search/instCategoryTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
