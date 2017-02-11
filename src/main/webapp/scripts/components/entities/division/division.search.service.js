'use strict';

angular.module('stepApp')
    .factory('DivisionSearch', function ($resource) {
        return $resource('api/_search/divisions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
