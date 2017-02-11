'use strict';

angular.module('stepApp')
    .factory('PgmsPenRulesSearch', function ($resource) {
        return $resource('api/_search/pgmsPenRuless/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
