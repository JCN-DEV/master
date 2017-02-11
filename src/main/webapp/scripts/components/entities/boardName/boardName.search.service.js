'use strict';

angular.module('stepApp')
    .factory('BoardNameSearch', function ($resource) {
        return $resource('api/_search/boardNames/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
