'use strict';

angular.module('stepApp')
    .factory('DeoSearch', function ($resource) {
        return $resource('api/_search/deos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
