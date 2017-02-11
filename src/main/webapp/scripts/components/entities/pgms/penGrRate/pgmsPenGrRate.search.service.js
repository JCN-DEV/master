'use strict';

angular.module('stepApp')
    .factory('PgmsPenGrRateSearch', function ($resource) {
        return $resource('api/_search/pgmsPenGrRates/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
