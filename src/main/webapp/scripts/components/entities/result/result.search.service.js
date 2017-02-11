'use strict';

angular.module('stepApp')
    .factory('ResultSearch', function ($resource) {
        return $resource('api/_search/results/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
