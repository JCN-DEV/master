'use strict';

angular.module('stepApp')
    .factory('PrlLocalitySetInfoSearch', function ($resource) {
        return $resource('api/_search/prlLocalitySetInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
