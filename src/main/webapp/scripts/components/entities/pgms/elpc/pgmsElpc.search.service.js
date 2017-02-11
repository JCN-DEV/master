'use strict';

angular.module('stepApp')
    .factory('PgmsElpcSearch', function ($resource) {
        return $resource('api/_search/pgmsElpcs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
