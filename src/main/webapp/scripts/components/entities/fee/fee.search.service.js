'use strict';

angular.module('stepApp')
    .factory('FeeSearch', function ($resource) {
        return $resource('api/_search/fees/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
