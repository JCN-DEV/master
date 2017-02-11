'use strict';

angular.module('stepApp')
    .factory('PrlEconomicalCodeInfoSearch', function ($resource) {
        return $resource('api/_search/prlEconomicalCodeInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
