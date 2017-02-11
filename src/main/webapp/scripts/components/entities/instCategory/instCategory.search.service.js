'use strict';

angular.module('stepApp')
    .factory('InstCategorySearch', function ($resource) {
        return $resource('api/_search/instCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstCategoryActive', function ($resource) {
        return $resource('api/instCategorys/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
