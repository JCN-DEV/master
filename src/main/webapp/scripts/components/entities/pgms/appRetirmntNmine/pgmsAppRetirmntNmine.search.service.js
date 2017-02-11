'use strict';

angular.module('stepApp')
    .factory('PgmsAppRetirmntNmineSearch', function ($resource) {
        return $resource('api/_search/pgmsAppRetirmntNmines/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
