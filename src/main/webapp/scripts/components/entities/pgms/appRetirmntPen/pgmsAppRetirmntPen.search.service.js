'use strict';

angular.module('stepApp')
    .factory('PgmsAppRetirmntPenSearch', function ($resource) {
        return $resource('api/_search/pgmsAppRetirmntPens/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
